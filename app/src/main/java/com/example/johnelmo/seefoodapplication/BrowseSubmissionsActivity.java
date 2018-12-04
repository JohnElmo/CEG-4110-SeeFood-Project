package com.example.johnelmo.seefoodapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class BrowseSubmissionsActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectImageFab, selectCameraFab;
    Button selectHelp;

    static final String FILE_DOWNLOAD_URL = "http://18.191.74.137/output";
    TextView responseText;
    String json_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_submissions);
        getSupportActionBar().hide();
        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Browse_HomeSelect);
        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_Browse_ImageSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Browse_CameraSelect);
        selectHelp = (Button) findViewById(R.id.HelpIcon);

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
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(BrowseSubmissionsActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
    });
    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        public Integer[] mThumbIds = {

                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food,
                R.drawable.cooking_robot,R.drawable.cooking_robot_food
        };
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
                json_string = EntityUtils.toString(r_entity);

                try {
                    JSONArray jsonArray = new JSONArray(json_string);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
            responseText.setText(json_string);
        }
    }

}