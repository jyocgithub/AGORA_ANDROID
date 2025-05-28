package com.android.jyocguion_sensores;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class MiSensorLuz {

    //  NECESITA 
    //<uses-feature
    //    android:name="android.hardware.sensor.proximity"
    //    android:required="true" />
    //<uses-feature
    //    android:name="android.hardware.sensor.gyroscope"
    //    android:required="true" />

    MainActivity mainactivity;
    SensorManager sensorManager;
    SensorEventListener lightSensorListener;
    Sensor lightSensor;

    public MiSensorLuz(MainActivity mainactivity) {
        this.mainactivity = mainactivity;
        crearSensor();
    }


    public void crearSensor() {
        // EN UN HILO INDEPENDIENTE.....
        new Thread(() -> {

            sensorManager = (SensorManager) mainactivity.getSystemService(Context.SENSOR_SERVICE);
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor == null) {
                Toast.makeText(mainactivity, "No hay sensor de luz", Toast.LENGTH_LONG).show();
                return;
            }
            // creamos un listener para el sensor 
            lightSensorListener = new SensorEventListener() {

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                        mainactivity.tvLuz.setText("Factor de luz: " + event.values[0]);
                    }
                }

            };
            iniciarSensor();
       
        }).start();



    }


    public void iniciarSensor() {
        // registramos el listener en nustro sensor de proximidad, que lea el sensor cada 2 segundos
        sensorManager.registerListener(
                lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararSensor() {
        sensorManager.unregisterListener(lightSensorListener);
    }



}


