package com.example.johnelmo.seefoodapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.github.clans.fab.FloatingActionButton;

public class BrowseSubmissionsActivity extends AppCompatActivity {

    FloatingActionButton selectHomeFab, selectImageFab, selectCameraFab;
    ImageButton selectHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_submissions);
        selectHomeFab = (FloatingActionButton) findViewById(R.id.fab_Browse_HomeSelect);
        selectImageFab = (FloatingActionButton) findViewById(R.id.fab_Browse_ImageSelect);
        selectCameraFab = (FloatingActionButton) findViewById(R.id.fab_Browse_CameraSelect);
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

}