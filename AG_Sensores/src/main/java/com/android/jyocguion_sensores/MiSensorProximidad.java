package com.android.jyocguion_sensores;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class MiSensorProximidad {

    //  NECESITA 
    //<uses-feature
    //    android:name="android.hardware.sensor.proximity"
    //    android:required="true" />
    //<uses-feature
    //    android:name="android.hardware.sensor.gyroscope"
    //    android:required="true" />

    MainActivity mainactivity;
    SensorManager sensorManager;
    SensorEventListener proximitySensorListener;
    Sensor proximitySensor;
    public MiSensorProximidad(MainActivity mainactivity) {
        this.mainactivity = mainactivity;
        crearSensor();
    }


    public void crearSensor() {
         sensorManager = (SensorManager) mainactivity.getSystemService(Context.SENSOR_SERVICE);
         proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor == null) {
            Log.e("====JYOC", "No hay sensor de proximidad");
            Toast.makeText(mainactivity, "No hay sensor de proximidad", Toast.LENGTH_LONG).show();
            return;
        }
        // creamos un listener para el sensor 
         proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    mainactivity.tvProximidad.setText("ALGO DELANTE DEL SENSOR DE PROXIMIDAD");
                    mainactivity.tvProximidad.setBackgroundColor(Color.RED);
                     //Detected something nearby
                    //Toast.makeText(mainactivity, "Detectado algo cerca !!!!! ", Toast.LENGTH_LONG).show();
                } else {
                    mainactivity.tvProximidad.setText("NADA DELANTE DEL SENSOR DE PROXIMIDAD");
                    mainactivity.tvProximidad.setBackgroundColor(Color.GREEN);
                     //Nothing is nearby  NBO HAY NADA DELANTE DEL SENSOR DE PROXIMIDAD
                    //Toast.makeText(mainactivity, "Detectado cambio de proximidad ", Toast.LENGTH_LONG).show();
                }
              
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        iniciarSensor();

    }


    public void iniciarSensor() {
        // registramos el listener en nustro sensor de proximidad, que lea el sensor cada 2 segundos
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2000);
    }

    public void pararSensor() {
        sensorManager.unregisterListener(proximitySensorListener);
    }


}
