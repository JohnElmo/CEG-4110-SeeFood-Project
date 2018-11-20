package com.example.johnelmo.seefoodapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.resultTextView);
        String response = CameraActivity.getResponse();
//        JSONObject object = null;
//        String score = "";
//        String result = "";
//        try {
//            object = (JSONObject) new JSONTokener(response).nextValue();
//            score = object.getString("score");
//            result = object.getString("result");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        // resultTextView.setText("Score: " + score +" , result: " + result);
        resultTextView.setText("Response: " + response);
    }
}
