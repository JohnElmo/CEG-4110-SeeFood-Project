package com.example.johnelmo.seefoodapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;


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

public class ImageSelectActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectCameraFab, selectBrowseFab;
    Button selectHelp, browseGallery, submitImage;
    ImageView img,robot;
    TextView submitResponse;
    RatingBar ratingBar;

    static final int SELECT_PICTURE = 1;
    static final String HOME_URL = "http://18.191.74.137";
    static final String FILE_UPLOAD_URL = "http://18.191.74.137/input";
    String mCurrentPhotoPath = "";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        getSupportActionBar().hide();

        verifyStoragePermissions(this);


        img = (ImageView)findViewById(R.id.GalleryPreview);
        robot = (ImageView)findViewById(R.id.imageRobot);
        browseGallery = (Button) findViewById(R.id.Gallerybtn);
        submitResponse = (TextView) findViewById(R.id.selected_image_submitResponse);
        submitImage = (Button) findViewById(R.id.submitButton);
        ratingBar = findViewById(R.id.ratingBar);

        browseGallery.setBackgroundResource(R.drawable.chicken_raw);
        robot.setBackgroundResource(R.drawable.cooking_robot_just_pan);

        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Image_HomeSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Image_CameraSelect);
        selectBrowseFab = (FloatingActionButton) findViewById(R.id.fab_Image_BrowseSubmissions);
        selectHelp = (Button) findViewById(R.id.HelpIcon);

        selectHomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToMainActivity(view);
            }
        });

        selectCameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToCameraActivity(view);
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

        browseGallery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        submitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCurrentPhotoPath.equals("")) {
                    UploadFileToServer uploadFileToServer = new UploadFileToServer();
                    uploadFileToServer.execute();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                mCurrentPhotoPath = getPath(selectedImageUri);
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void changeToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeToCameraActivity(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void changeToBrowseSubmissionsActivity(View view) {
        Intent intent = new Intent(this, BrowseSubmissionsActivity.class);
        startActivity(intent);
    }

    public void changeToHelp(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    private class UploadFileToServer extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            submitResponse.setText("Submit in progress...");
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
            // if result returned is correct then it will be in the form: "float1, float2"
            if (result.matches("[-+]?[0-9]*\\.?[0-9]+[,][ ][-+]?[0-9]*\\.?[0-9]+")) {
                String arr[] = result.split(", ", 2);
                float score1 = Float.valueOf(arr[0]);
                float score2 = Float.valueOf(arr[1]);
                String classType;
                if (score1 > score2) {
                    classType = "Food";
                } else {
                    classType = "No Food";
                }
                //double confidence = abs(score1 - score2) / 4;
                float confidence = score1 - score2;
                double rating;
                if (confidence >= 3.0) { // greater than 3
                    rating = 6.0; // Certainly It Is Food
                    browseGallery.setBackgroundResource(R.drawable.chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_food);
                } else if (confidence >= 2.0) { // between 3 and 2
                    rating = 5.0; // Very Likely Food
                    browseGallery.setBackgroundResource(R.drawable.chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_food);
                } else if (confidence >= 0.5) { // between 2 and 0.5
                    rating = 4.0; // Likely Food
                    browseGallery.setBackgroundResource(R.drawable.chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_food);
                } else if (confidence >= -0.5) { // between 0.5 and -0.5
                    rating = 3.0; // Unlikely Food
                    browseGallery.setBackgroundResource(R.drawable.burnt_chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_not_food);
                } else if (confidence >= -2.0) { // between -0.5 and -2
                    rating = 2.0; // Very Unlikely Food
                    browseGallery.setBackgroundResource(R.drawable.burnt_chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_not_food);
                } else { // less than -2
                    rating = 1.0; // Definitely Not Food
                    browseGallery.setBackgroundResource(R.drawable.burnt_chicken);
                    robot.setBackgroundResource(R.drawable.cooking_robot_not_food);
                }
                submitResponse.setText("I see " + classType);
                ratingBar.setRating((float) rating);
                // if result returned is not null, then print out the error message
            } else if (result != null) {
                submitResponse.setText(result);
            } else {
                submitResponse.setText("Result Error: null");
            }
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}