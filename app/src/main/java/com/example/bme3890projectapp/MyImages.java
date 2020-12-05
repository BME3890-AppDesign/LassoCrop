package com.example.bme3890projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class MyImages extends AppCompatActivity {

    private BottomNavigationView navView;
    private TextView firstDate, secondDate, firstRed, secondRed;
    private String firstPath, secondPath;
    private ImageView firstImage, secondImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        navView = (BottomNavigationView) findViewById(R.id.bnv_navbar);
        navView.setOnNavigationItemSelectedListener(bottomNavMethod);
        firstDate = (TextView) findViewById(R.id.tv_firstDate);
        //secondDate = (TextView) findViewById(R.id.tv_secondDate);
        firstRed = (TextView) findViewById(R.id.tv_firstRedValue);
        //secondRed = (TextView) findViewById(R.id.tv_secondRedValue);
        firstImage = (ImageView) findViewById(R.id.iv_firstImage);

        SharedPreferences imageInfo = getSharedPreferences("images",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor loginEditor = imageInfo.edit();

        Map<String,?> keys = imageInfo.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            //for (int i = 0; i < keys.size(); i++) {
               // if (keys.size() == 1) {
                    firstDate.setText(entry.getKey());
                    Bitmap imageBitmap = BitmapFactory.decodeFile(entry.getValue().toString());
                    firstImage.setImageBitmap(imageBitmap);
                    firstRed.setText("Average red value: 12");
              //  }
                /* else {
                    if (i == 0) {
                        firstDate.setText(entry.getKey());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(entry.getValue().toString());
                        firstImage.setImageBitmap(imageBitmap);
                        firstRed.setText("Average red value: ");
                    }
                    else {
                        secondDate.setText(entry.getKey());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(entry.getValue().toString());
                        secondImage.setImageBitmap(imageBitmap);
                        secondRed.setText("Average red value: ");
                    }

                }*/

          //  }
        }
    }

    public void toHome(MenuItem item) {
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }

    public void toCamera(View view) {
        Intent i = new Intent(this, Camera.class);
        startActivity(i);
    }

    public void toThird(MenuItem item) {
        Intent i = new Intent(this, Email.class);
        startActivity(i);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mi_home:
                    toHome(null);
                    break;
                case R.id.mi_email:
                    toThird(null);
                    break;
                case R.id.mi_photo:
                    toCamera(null);
                    break;
            }
            return true;
        }
    };
}