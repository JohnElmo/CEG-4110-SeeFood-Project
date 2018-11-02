package com.example.johnelmo.seefoodapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
                Toast.makeText(MainActivity.this, "You selected ImageFab", Toast.LENGTH_SHORT).show();
            }
        });

        selectCameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You selected CameraFab", Toast.LENGTH_SHORT).show();
            }
        });

        selectBrowseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You selected BrowseFab", Toast.LENGTH_SHORT).show();
            }
        });
    }
}