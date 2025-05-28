package com.android.jyocguion_sensores;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MiSensorAcelerometro implements SensorEventListener {
    MainActivity activity;
    SensorManager sensorManager;
    Sensor sensorAcelerometro;

    public MiSensorAcelerometro(MainActivity mainActivity) {
        this.activity = mainActivity;
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        crearSensor();
    }

    public void crearSensor() {
        // HEMOS FIJADO LA PANRTALLA COMO PORTRAIT PARA QUE AL AGITAR EL MOVIL Y MOVERLO NO CAMBIE Y SEA MAS FACIL VER LOS RESULTADOS
        // ESTO SE HACE EN EL MANIFEST, EN LA ACRIVIDAD, PONIENDOLE  android:screenOrientation="portrait"
        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void iniciarSensor() {
        // registramos el listener en nustro sensor acelerometro
        sensorManager.registerListener(this, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararSensor() {
        sensorManager.unregisterListener(this);
    }


    /*
     * Mediante este metodo se puede monitorizar los valores de un sensor
     */
    long ultimaHora;
    //private long lastUpdate = 0;
    float actualx, actualy, actualz;
    String sx, sy, sz;
    private float last_x, last_y, last_z;
    long diffTime;
    private static final int LIMITE_SACUDIDA = 600;

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // como el sensor es muy sensible, vemos que solo se evalue cada 100 milisegundos
            long ahora = System.currentTimeMillis();
            if ((ahora - ultimaHora) > 100) {
                diffTime = (ahora - ultimaHora);
                ultimaHora = ahora;
                // y ahora ya si, mostramos la info 
                actualx = event.values[0];
                actualy = event.values[1];
                actualz = event.values[2];
                sx = new DecimalFormat("##0.000").format(event.values[0]);
                sy = new DecimalFormat("##0.000").format(event.values[1]);
                sz = new DecimalFormat("##0.000").format(event.values[2]);
                String valores = "X: " + sx + " | Y: " + sy + " | Z : " + sz;
                activity.tvAcelerometro.setText(valores);
            }


            // Si queremos ver si hubo una "sacudida" em el telefono, podemos usar esto:
            float speed = Math.abs(actualx + actualy + actualz - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > LIMITE_SACUDIDA) {
                Toast.makeText(activity, "DETECTADA SACUDIDA !!!!!!", Toast.LENGTH_LONG).show();

            }

            last_x = actualx;
            last_y = actualy;
            last_z = actualz;

        }

    }

    //El metodo onAccuracyChanged() es invocado cuando un sensor cambia su precision
    //Parametros:
    // - Sensor sensor: referencia al objeto de tipo Sensor que ha cambiado de precisi坦n
    // - int accuracy: la nueva precisi坦n del sensor

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}