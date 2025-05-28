package com.android.jyocguion_sensores;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MiActividadObtenerDireccion{
//public class MiActividadObtenerDireccion extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener {
//
//    /* Constantes usadas para:
//
//     * - manejar errores * - enviar y recibir información * al IntentService (por eso las constantes se nombran acabando en IS) */ 
//    public static final int RESULTADO_EXITO_IS = 0; 
//    public static final int RESULTADO_FALLO_IS = 1; 
//    public static final String PACKAGE_NAME = "com.mmc.obteniendodireccion"; 
//    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER"; 
//    public static final String DIRECCION_OBTENIDA_IS = PACKAGE_NAME + ".DIRECCION_OBTENIDA"; 
//    public static final String ULTIMA_UBICACION_IS = PACKAGE_NAME + ".ULTIMA_UBICACION";
//
//    private GoogleApiClient googleApiClient; 
//    private Location ultimaUbicacion;
//    private TextView direccionTextView; private Button averiguarDireccionButton; // Receiver registrado en esta actividad // Su misión es obtener la respuesta de AveriguarDireccionIntentService private ResultadoDireccionReceiver receiver;
//
//
//
//
//
//
//    @Override public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState); setContentView(R.layout.activity_obteniendo_direccion);
//
//        receiver = new ResultadoDireccionReceiver(new Handler());
//
//        direccionTextView = (TextView) findViewById(R.id.direccionTextView); averiguarDireccionButton = (Button) findViewById(R.id.direccionButton); averiguarDireccionButton.setEnabled(false); crearGoogleApiClient();
//
//    }
//
//    // Se crea un objeto de la clase GoogleApiClient
//
//    private void crearGoogleApiClient() {
//
//        googleApiClient = new GoogleApiClient.Builder(this) .addConnectionCallbacks(this) .addOnConnectionFailedListener(this) .addApi(LocationServices.API) .build();
//
//    }
//
//    /*
//
//     * Método asociado el evento onClick del botón direccionButton del UI (User Interface) * Objetivo: comprobar si googleApiClient está conectado y si existe la última ubicación, * en caso afirmativo llama al método para iniciar el IntentService */
//
//    public void averiguarDireccionButton(View view){
//
//        if (googleApiClient.isConnected() && ultimaUbicacion != null) { iniciarIntentService(); }
//
//    }
//
//    @Override
//
//    public void onConnected(Bundle connectionHint) {
//
//        ultimaUbicacion = LocationServices.FusedLocationApi.getLastLocation(googleApiClient); if (ultimaUbicacion != null) {
//
//            // Se comprueba si hay un Geocoder disponible if (!Geocoder.isPresent()) {
//
//            Toast.makeText(this, "No hay un Geocoder disponible", Toast.LENGTH_LONG).show();
//
//            return; } averiguarDireccionButton.setEnabled(true);
//
//    } else Toast.makeText(this, "NO existe la última ubicación", Toast.LENGTH_LONG).show();
//
//}
//
//    @Override public void onConnectionFailed(ConnectionResult result) { }
//
//    @Override public void onConnectionSuspended(int cause) { googleApiClient.connect(); }
//    /*
//
//     * Método: iniciarIntentService() * Objetivo: crear el intent que se pasará al IntentService añadiendo dos datos extras: * (1) el receiver para recibir la respuesta, (2) la última ubicación */
//
//    private void iniciarIntentService() {
//
//        // Intent explícito que se pasa al IntentService Intent intent = new Intent(this, AveriguarDireccionIntentService.class); // Se añade como dato extra el receiver intent.putExtra(RECEIVER, receiver); // También se añade como dato extra la última ubicación intent.putExtra(ULTIMA_UBICACION_IS, ultimaUbicacion); startService(intent);
//
//    }
//
//// Para mostrar un Toast (usado desde el receiver) protected void mostrarToast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
//
///*
//
// * RECEIVER * Clase ResultadoDireccionReceiver extends ResultReceiver * Objetivo: recibir resultados desde el IntentService (AveriguarDireccionIntentService) */
//
//class ResultadoDireccionReceiver extends ResultReceiver {
//
//    public ResultadoDireccionReceiver(Handler handler) { super(handler); } // Recibe los resultados enviados desde AveriguarDireccionIntentService @Override
//
//    protected void onReceiveResult(int resultCode, Bundle resultData) {
//
//        // Se extrae la información enviada desde el Intent Service (puede ser // la dirección o un mensaje de error) y se muestra en el TextView String direccionObtenida = resultData.getString(DIRECCION_OBTENIDA_IS); direccionTextView.setText(direccionObtenida);
//
//        // Se muestra un mensaje por pantalla si se ha encontrado la dirección if (resultCode == RESULTADO_EXITO_IS) { mostrarToast("Dirección encontrada"); }
//
//    }
//
//}
//
//    @Override
//
//    protected void onStart() {
//
//        super.onStart(); googleApiClient.connect();
//
//    }
//
//    @Override
//
//    protected void onStop() {
//
//        super.onStop(); if (googleApiClient.isConnected()) { googleApiClient.disconnect(); }
//
//    }
}
