package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectImageFab, selectBrowseFab;
    Button capture, submit;
    ImageView mImageView;
    ImageButton selectHelp;
    static final String HOME_URL = "http://18.191.74.137";
    static final String FILE_UPLOAD_URL = "http://18.191.74.137/input";
    String mCurrentPhotoPath = "";

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
        mImageView = findViewById(R.id.imageView);

        capture = findViewById(R.id.captureButton);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCurrentPhotoPath.equals("")) {
                    UploadFileToServer uploadFileToServer = new UploadFileToServer();
                    uploadFileToServer.execute();
                    changeToResultActivity(view);
                }
            }
        });

        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Camera_HomeSelect);
        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_Camera_ImageSelect);
        selectBrowseFab = (FloatingActionButton) findViewById(R.id.fab_Camera_BrowseSubmissions);
        selectHelp = (ImageButton) findViewById(R.id.HelpIcon);

        selectHomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToMainActivity(view);
            }
        });

        selectImageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToImageSelectActivity(view);
            }
        });

        selectBrowseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToBrowseSubmissionsActivity(view);
            }
        });

        selectHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToHelp(view);
            }
        });

    }

    public void changeToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeToImageSelectActivity(View view) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        startActivity(intent);
    }

    public void changeToBrowseSubmissionsActivity(View view) {
        Intent intent = new Intent(this, BrowseSubmissionsActivity.class);
        startActivity(intent);
    }

    public void changeToResultActivity(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void changeToHelp(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                galleryAddPic();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
           int thumbSize = 64;
           Bitmap thumbBitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mCurrentPhotoPath),
                   thumbSize, thumbSize);
           mImageView.setImageBitmap(thumbBitmap);
       }
   }

    private class UploadFileToServer extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String responseString = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(FILE_UPLOAD_URL);
            try {
                MultipartEntity entity = new MultipartEntity();
                File file = new File(mCurrentPhotoPath);
                entity.addPart("pic", new FileBody(file));
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode + " -> " + response.getStatusLine().getReasonPhrase();
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                ResultActivity.resultTextView.setText("Response" + result);
            } else {
                ResultActivity.resultTextView.setText("Response was null");
            }
        }
    }
}
