package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
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

public class BrowseSubmissionsActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectImageFab, selectCameraFab;
    Button selectHelp;
    ImageView food1, food2, food3, food4, notFood1, notFood2, notFood3, notFood4;
    static final String FILE_DOWNLOAD_URL = "http://18.191.74.137/output";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_submissions);
        getSupportActionBar().hide();
        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Browse_HomeSelect);
        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_Browse_ImageSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Browse_CameraSelect);
        selectHelp = (Button) findViewById(R.id.HelpIcon);
        food1 = (ImageView) findViewById(R.id.food1);
        food2 = (ImageView) findViewById(R.id.food2);
        food3 = (ImageView) findViewById(R.id.food3);
        food4 = (ImageView) findViewById(R.id.food4);
        notFood1 = (ImageView) findViewById(R.id.notFood1);
        notFood2 = (ImageView) findViewById(R.id.notFood2);
        notFood3 = (ImageView) findViewById(R.id.notFood3);
        notFood4 = (ImageView) findViewById(R.id.notFood4);

        DownloadFileFromServer downloadFileFromServer = new DownloadFileFromServer();
        downloadFileFromServer.execute();

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

        selectCameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToCameraActivity(view);
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

    public void changeToCameraActivity(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    public void changeToHelp(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    private class DownloadFileFromServer extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String responseString = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(FILE_DOWNLOAD_URL);
            try {
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
        }
    }

}