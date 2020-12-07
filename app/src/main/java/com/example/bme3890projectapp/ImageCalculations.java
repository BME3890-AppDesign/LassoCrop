package com.example.bme3890projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageCalculations extends AppCompatActivity /*implements View.OnTouchListener*/ {

    private ImageView fullImage;
    private GraphView rgbChart;
    private int bitmapWidth, bitmapHeight, scaledHeight, scaledWidth;
    private BottomNavigationView navView;
    private double artSize;

    private TextView touchX1, touchY1, touchX2, touchY2, tv_artifactSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_calculations);


        fullImage = (ImageView) findViewById(R.id.iv_fullImage);
        rgbChart = (GraphView) findViewById(R.id.gv_graph);
        tv_artifactSize = (TextView) findViewById(R.id.tv_artifactSize);
        navView = (BottomNavigationView) findViewById(R.id.bnv_navbar);
        navView.setOnNavigationItemSelectedListener(bottomNavMethod);

        SharedPreferences imageInfo = getSharedPreferences("images",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor loginEditor = imageInfo.edit();

        Intent imageCalculations = getIntent();
        String currentPhotoPath = imageCalculations.getStringExtra(TakePhoto.NAME_EXTRA);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loginEditor.putString(date, currentPhotoPath);
        loginEditor.apply();

        Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
        fullImage.setImageBitmap(imageBitmap);
        bitmapWidth = imageBitmap.getWidth();
        bitmapHeight = imageBitmap.getHeight();
        scaledHeight = imageBitmap.getScaledHeight(294);
        scaledWidth = imageBitmap.getScaledWidth(294);


        artSize = (bitmapWidth/32.13)/1.335; //32.13 = ratio of pixels to mm, 10 -> converts to cm
        tv_artifactSize.setText("Artifact diameter: " + artSize + "mm");


        //touchX1 = (TextView) findViewById(R.id.tv_touchPixelX1);
        //touchY1 = (TextView) findViewById(R.id.tv_touchPixelY1);
        //touchX2 = (TextView) findViewById(R.id.tv_touchPixelX2);
        //touchY2 = (TextView) findViewById(R.id.tv_touchPixelY2);


        //fullImage.setOnTouchListener(this);



    }


    public void makeGraph(View view) {

        Intent imageCalculations = getIntent();
        String currentPhotoPath = imageCalculations.getStringExtra(TakePhoto.NAME_EXTRA);
        Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);


      //  int length = (bitmapHeight*bitmapWidth)/6;

        //graphing 3 random pixels
        DataPoint[] red = new DataPoint[3];
        int redValue = Color.red(imageBitmap.getPixel(0,0));
        red[0] = new DataPoint(0, redValue);
        redValue = Color.red(imageBitmap.getPixel(bitmapWidth/2,bitmapHeight/2));
        red[1] = new DataPoint(1,redValue);
        redValue = Color.red(imageBitmap.getPixel(bitmapWidth-1,bitmapHeight-1));
        red[2] = new DataPoint(2,redValue);


       /* for (int k = 0; k<= length; k++){
            for (int i = 0; i <= bitmapHeight/6; i++) {
                for (int j = 0; j <= bitmapWidth/6; j++) {
                    redValue = Color.red(imageBitmap.getPixel(j,i));
                    red[k] = new DataPoint(k, redValue);

                }
            }
        } */

        LineGraphSeries<DataPoint> redSeries = new LineGraphSeries<>(red);

        rgbChart.addSeries(redSeries);
        rgbChart.setTitle("Red Values of the Pixels");
        rgbChart.getGridLabelRenderer().setVerticalAxisTitle("Red Value");
        rgbChart.getGridLabelRenderer().setHorizontalAxisTitle("Pixel Number");

    }
/*
    @Override    protected void onResume() {
        super.onResume();
        setContentView(new CropView(ImageCalculations.this));
    }
    */

/*
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("MainActivity","touching...");
        if (view.getId() == R.id.iv_fullImage){

            Log.d("MainActivity","TOUCHING IMAGE!!!");
            android.graphics.Point touchPlace = new Point();
            touchPlace.set(Float.floatToIntBits(motionEvent.getX()),
                    Float.floatToIntBits(motionEvent.getY()));

            touchX1.setText(Float.toString(motionEvent.getX()));
            touchY1.setText(Float.toString(motionEvent.getY()));


            return false;
        }
        else
            return true;
    }
    */

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