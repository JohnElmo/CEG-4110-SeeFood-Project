package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton selectImageFab, selectCameraFab, selectBrowseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_ImageSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_CameraSelect);
        selectBrowseFab = (FloatingActionButton) findViewById(R.id.fab_BrowseSubmissions);

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
}