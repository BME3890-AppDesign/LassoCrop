package com.example.bme3890projectapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Camera extends AppCompatActivity implements SensorEventListener {

    private BottomNavigationView navView;
    private SensorManager sensorManager;
    private Sensor mLight;
    TextView sensorTextView;
    private GraphView fluxChart;
    public DataPoint[] fluxPoint = new DataPoint[1];
    public int k = 0;
    public float lux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        navView = (BottomNavigationView) findViewById(R.id.bnv_navbar);
        navView.setOnNavigationItemSelectedListener(bottomNavMethod);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorTextView = (TextView) findViewById(R.id.tv_sensor);

        fluxChart = (GraphView) findViewById(R.id.gv_graph);
        fluxChart.setTitle("Flux Values");
        fluxChart.getGridLabelRenderer().setVerticalAxisTitle("Flux (lx)");
        fluxChart.getGridLabelRenderer().setHorizontalAxisTitle("Time Point");
    }

    public void displayFlux(View view) {
        sensorTextView.setText("Current Flux: " + Float.toString(lux));
        fluxPoint[k] = new DataPoint(k, lux);
        fluxPoint = addX(fluxPoint.length, fluxPoint, fluxPoint[k]);
        k++;
        LineGraphSeries<DataPoint> fluxSeries = new LineGraphSeries<>(fluxPoint);
        fluxChart.addSeries(fluxSeries);
        fluxSeries.setColor(Color.MAGENTA);

        if (lux <= 10.0) {
            Toast.makeText(getApplicationContext(), "Not enough light. Try again!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Adequate light level achieved.", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(Camera.this,TakePhoto.class);
                    startActivity(i);
                }
            }, 2000);
        }


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        lux = event.values[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
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

    public void toHome(MenuItem item) {
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }

    public void toThird(MenuItem item) {

        Intent i = new Intent(this, Email.class);
        startActivity(i);
    }

    public void toCamera(View view) {
        Intent i = new Intent(this, Camera.class);
        startActivity(i);
    }
    public static DataPoint[] addX(int n, DataPoint arr[], DataPoint x)
    {
        int i;

        // create a new array of size n+1
        DataPoint newarr[] = new DataPoint[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

}