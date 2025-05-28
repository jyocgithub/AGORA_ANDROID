package com.android.jyocguion_sensores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Bundle savedInstanceState;
    private static final int PETICION_DE_PERMISOS_DESDE_ONCREATE = 12321;
    private String[] arrayDePermisosSolicitados = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public TextView tvLocalizacionActual1, tvLocalizacionActual2, tvLocalizacionActual3, tvProximidad, tvGiroscopio;
    public TextView tvAcelerometro, tvLuz, tvDistancia;
    public EditText etLocalizacionLatitud, etLocalizacionLongitud;

    private MiSensorAcelerometro sensorAcelerometro;
    private MiSensorProximidad sensorProximidad;
    private MiSensorLocalizacion sensorLocalizacion;
    private MiSensorGiroscopio sensorGiroscopio;
    private MiSensorLuz sensorLuz;
    private SensorManager sensorManager;
    Location localizacionActual, locationDestino;


    protected void onCreate_conAccionesSoloConPermiso(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        tvLocalizacionActual1 = findViewById(R.id.tvLocalizacionActual);
        tvLocalizacionActual2 = findViewById(R.id.tvLocalizacionActual1);
        tvLocalizacionActual3 = findViewById(R.id.tvLocalizacionDireccion);
        tvAcelerometro = findViewById(R.id.tvAcelerometro);
        tvProximidad = findViewById(R.id.tvProximidad);
        tvGiroscopio = findViewById(R.id.tvGiroscopio);
        tvLuz = findViewById(R.id.tvLuz);
        etLocalizacionLatitud = findViewById(R.id.etLocalizacionLatitud);
        etLocalizacionLongitud = findViewById(R.id.etLocalizacionLongitud);

        // comprobar qué sensores existen
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listadoSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String mensaje = "\n";
        for (Sensor s : listadoSensores) {
            mensaje += "\n" + s.getName();
        }
        Toast.makeText(this, "SENSORES ESCONTRADOS:" + mensaje, Toast.LENGTH_LONG).show();
        Log.w("===== JYOC", mensaje);


        // lanzar localizacion
        sensorLocalizacion = new MiSensorLocalizacion(this);
        sensorLocalizacion.iniciarSensor();

        // lanzar sensor de proximidad 
        sensorProximidad = new MiSensorProximidad(this);
        sensorProximidad.iniciarSensor();

        // lanzar sensor de giroscipio 
        sensorGiroscopio = new MiSensorGiroscopio(this);
        sensorGiroscopio.iniciarSensor();

        // lanzar acelerometro
        sensorAcelerometro = new MiSensorAcelerometro(this);
        sensorAcelerometro.iniciarSensor();
        
        // lanzar sensor luz
        sensorLuz = new MiSensorLuz(this);
        sensorLuz.iniciarSensor();

    }

    protected void onPause() {
        super.onPause();
        sensorGiroscopio.pararSensor();
        sensorProximidad.pararSensor();
        sensorAcelerometro.pararSensor();
        sensorLocalizacion.pararSensor();


    }

    protected void onResume() {
        super.onResume();
        sensorGiroscopio.iniciarSensor();
        sensorProximidad.iniciarSensor();
        sensorAcelerometro.iniciarSensor();
        sensorLocalizacion.iniciarSensor();

    }

    public void medirDistancia(View v) {

        String longi = etLocalizacionLatitud.getText().toString();
        String latit = etLocalizacionLongitud.getText().toString();
        double latitudDestino = Double.parseDouble(latit);
        double longitudDestino = Double.parseDouble(longi);
        locationDestino = new Location("");
        locationDestino.setLatitude(latitudDestino);
        locationDestino.setLongitude(longitudDestino);

        HiloMedirDistancia hmd = new HiloMedirDistancia();
        hmd.execute("");


    }


    private class HiloMedirDistancia extends AsyncTask<String, Integer, Boolean> {

        boolean seguir = true;

        @Override
        protected void onPreExecute() {
            tvDistancia = findViewById(R.id.tvDistancia);
            localizacionActual = sensorLocalizacion.getLocalizacionActual();
        }


        @Override
        protected Boolean doInBackground(String... params) {

            while (seguir) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(0);
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (localizacionActual != null) {

                double metros = localizacionActual.distanceTo(locationDestino);
                String mm = new DecimalFormat("##0.00").format(metros);
                tvDistancia.setText(mm + " metros");
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }

        @Override
        protected void onCancelled() {
        }
    }


    // **********************+++++++++++++++++++++++********************************
    // **                     BLOQUE PETICION PERMISOS                            **
    // ********++++++++++++++++++++++++*********************************************
    //
    // Procedimiendo de uso (en la primera actividad de la aplicacion):
    // 1.- Renombrar el método onCreate(savedInstanceState);  como
    //        onCreate_conAccionesSoloConPermiso(savedInstanceState);
    // 2.- Quitar las lineas
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    // 3.- Copiar este bloque entero en esa misma primera actividad de la aplicacion y descomentarlo
    // 4.- Modificar el arrayDePermisosSolicitados para incluir solo los que se necesita pedir
    // 5.- Añadir esos mismos permisos en el Android.Manifest con lineas como estas:
    //        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    //
    //
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ INICIO DEL BLOQUE PARA COPIAR @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@
    // @@@@@@@@ NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST: <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    // @@@@@@@@


    /**
     * onCreate
     * Sustituye al onCreate original de la actividad
     * Cuidado que si esta no es MainActivity hay que modificar el setContentView
     *
     * @param savedInstanceState  Bundle con el estado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmarQueExistenTodosEstosPermisos(this, savedInstanceState, arrayDePermisosSolicitados);
    }//  FIN nuevo onCreate

    /**
     * confirmarQueExistenTodosEstosPermisos
     * <p>
     * Solicita una serie de permisos si no se han concedido previamente por el usuario
     *
     * @param actividad            actividad donde se piden los permisos
     * @param savedInstanceState   bundle con el estado de la actividad
     * @param arrayPermisos        array con los permisos solicitados
     */
    public void confirmarQueExistenTodosEstosPermisos(Activity actividad, Bundle savedInstanceState, String... arrayPermisos) {
        boolean todosLosPemisosOk = true;
        for (String cadapermiso : arrayPermisos) {
            int permiso = ContextCompat.checkSelfPermission(actividad, cadapermiso);
            if (!(ContextCompat.checkSelfPermission(actividad, cadapermiso) == PackageManager.PERMISSION_GRANTED)) {
                todosLosPemisosOk = false;
            }
        }
        if (todosLosPemisosOk) {
            //-- RECORDAR HACER AQUI LO QUE SE DESEE CUANDO HAY PERMISOS   ---------------
            //-- LO NORMAL ES LLAMAR A UN METODO QUE COMPLETE EL ONCREATE  ---------------
            onCreate_conAccionesSoloConPermiso(savedInstanceState);
        } else {
            ActivityCompat.requestPermissions(actividad, arrayPermisos, PETICION_DE_PERMISOS_DESDE_ONCREATE);
        }
    }//  FIN confirmarQueExistenTodosEstosPermisos

    /**
     * onRequestPermissionsResult
     * Complemento al metodo confirmarQueExistenTodosEstosPermisos, que solicita permisos
     * <p>
     * Recordar modificar si se desea las acciones que se deseen realizar si se conceden
     * o si no se conceden los permisos
     * La correcta concesion de permisos, actualmente,  llama al antiguo OnCreate que
     * se renombro como     onCreate_conAccionesSoloConPermiso
     *
     * @param requestCode  codigo de quien solicito los permisos
     * @param permissions  array de permisos que se han pedido al usuario
     * @param grantResults array de respuestas del usuario a los permisos pedidos
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PETICION_DE_PERMISOS_DESDE_ONCREATE) {
            if (grantResults.length > 0) {
                boolean todosLosPemisosOk = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                        todosLosPemisosOk = false;
                    }
                }
                if (todosLosPemisosOk) {
                    //-- RECORDAR HACER AQUI LO QUE SE DESEE CUANDO HAY PERMISOS   ---------------
                    //-- LO NORMAL ES LLAMAR A UN METODO QUE COMPLETE EL ONCREATE  ---------------
                    onCreate_conAccionesSoloConPermiso(savedInstanceState);

                } else {
                    //-- RECORDAR HACER AQUI LO QUE SE DESEE SI NO HAY PERMISOS ---------------
                    finish(); // algun permiso no se otorgó, terminamos la actividad, no se deja seguir
                }
            } else {
                //-- RECORDAR HACER AQUI LO QUE SE DESEE SI NO HAY PERMISOS ---------------
                finish(); // se canceló al solicitar permisos, terminamos la actividad, no se deja seguir
            }
        }
    }//  FIN onRequestPermissionsResult
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIN DEL BLOQUE PARA COPIAR @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@






}

