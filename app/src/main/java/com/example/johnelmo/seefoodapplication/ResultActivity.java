package com.example.johnelmo.seefoodapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView result = findViewById(R.id.resultTextView);
        JSONObject jsonObjectResult = CameraActivity.getJsonObjectResult();
        if (jsonObjectResult != null) {
            result.setText(":)");
        } else {
            result.setText(":(");
        }
    }
}
