package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

public class ImageSelectActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectCameraFab, selectBrowseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Image_HomeSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Image_CameraSelect);
        selectBrowseFab = (FloatingActionButton) findViewById(R.id.fab_Image_BrowseSubmissions);

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
}