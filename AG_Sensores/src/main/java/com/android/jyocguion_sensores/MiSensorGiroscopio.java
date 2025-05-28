package com.android.jyocguion_sensores;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MiSensorGiroscopio {

    //  NECESITA 
    //<uses-feature
    //    android:name="android.hardware.sensor.proximity"
    //    android:required="true" />
    //<uses-feature
    //    android:name="android.hardware.sensor.gyroscope"
    //    android:required="true" />

    MainActivity mainactivity;
    SensorManager sensorManager;
    SensorEventListener gyroscopeSensorListener;
    Sensor gyroscopeSensor;

    public MiSensorGiroscopio(MainActivity mainactivity) {
        this.mainactivity = mainactivity;
        crearSensor();
    }


    public void crearSensor() {
        sensorManager = (SensorManager) mainactivity.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null) {
            Toast.makeText(mainactivity, "No hay GIROSCOPIO", Toast.LENGTH_LONG).show();
            return;
        }

        gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                // la info viene en radianes, lo pasamos a grados
                float nx = (float)(Math.toDegrees(sensorEvent.values[0] ));
                float ny = (float)(Math.toDegrees(sensorEvent.values[1] ));
                float nz = (float)(Math.toDegrees(sensorEvent.values[2] ));
                String sx = new DecimalFormat("##0.00").format(nx );
                String sy = new DecimalFormat("##0.00").format(ny );
                String sz = new DecimalFormat("##0.00").format(nz );
                
                
                String datos = "X: " + sx + " | Y: " + sy + " | Z : " + sz;
                mainactivity.tvGiroscopio.setText(datos);

                if (sensorEvent.values[2] > 0.5f) { //un giro sentido contrario agujas reloj
                    mainactivity.tvGiroscopio.setBackgroundColor(Color.MAGENTA);
                } else if (sensorEvent.values[2] < -0.5f) { // un giro sentido  agujas reloj
                    //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    mainactivity.tvGiroscopio.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }


    public void iniciarSensor() {
        // registramos el listener en nustro sensor gisroscopio
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararSensor() {
        sensorManager.unregisterListener(gyroscopeSensorListener);
    }


}
