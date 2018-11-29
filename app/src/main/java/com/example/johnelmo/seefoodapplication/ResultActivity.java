package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

public class ResultActivity extends AppCompatActivity {
    FloatingActionButton selectHomeFab, selectImageFab, selectCameraFab,selectBrowseFab;
    Button selectHelp;
    static TextView viewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        viewResult = findViewById(R.id.resultTextView);
        displayScore();

        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Result_HomeSelect);
        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_Result_ImageSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Result_CameraSelect);
        selectBrowseFab = (FloatingActionButton) findViewById(R.id.fab_Result_BrowseSubmissions);
        selectHelp = (Button) findViewById(R.id.HelpIcon);
        
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

    public static void displayScore() {
        viewResult.setText(CameraActivity.getScore1() + ", " + CameraActivity.getScore2());
    }
}
