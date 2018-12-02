import os
import numpy as np
import tensorflow as tf
from PIL import Image
from flask import Flask, request, json

app = Flask(__name__)

###### Initialization code - we only need to run this once and keep in memory.
sess = tf.Session()
saver = tf.train.import_meta_graph('/var/www/FlaskApps/SeefoodApp/saved_model/model_epoch5.ckpt.meta')
saver.restore(sess, tf.train.latest_checkpoint('/var/www/FlaskApps/SeefoodApp/saved_model/'))
graph = tf.get_default_graph()
x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
keep_prob = graph.get_tensor_by_name('Placeholder:0')
class_scores = graph.get_tensor_by_name("fc8/fc8:0")
######

@app.route('/')
def home():
    return "This is a flask application"

@app.route('/input', methods=['GET', 'POST'])
def input():
    if request.method == 'POST':
        file = request.files['pic']
		file_path_name = '/var/www/FlaskApps/SeefoodApp/images/' + str(file.filename)
        file.save(file_path_name)
        os.chmod(file_path_name, 0777)
    # Work in RGBA space (A=alpha) since png's come in as RGBA, jpeg come in as RGB
    # so convert everything to RGBA and then to RGB.
        image = Image.open(file_path_name).convert('RGB')
        image = image.resize((227, 227), Image.BILINEAR)
        img_tensor = [np.asarray(image, dtype=np.float32)]
    # Run the image in the model.
        scores = sess.run(class_scores, {x_input: img_tensor, keep_prob: 1.})
	if np.argmax(scores) == 1:
            #add notfood to end of file name
            os.rename(file_path_name, file_path_name + '_notfood')
        else:
            #add food to end of file name
            os.rename(file_path_name, file_path_name + '_food')
        return str(scores.item(0)) + ", " + str(scores.item(1))	
    else:
        return "Not POST method"
