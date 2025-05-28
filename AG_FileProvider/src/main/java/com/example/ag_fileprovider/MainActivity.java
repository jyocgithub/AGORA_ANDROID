package com.example.ag_fileprovider;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tvcontenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirUnPermiso();
    }

    public void accionesConPermiso() {
        tvcontenido = findViewById(R.id.tvContenido);
        
        ((Button) findViewById(R.id.btComenzar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             
                //String texto = "Texto de mensaje de prueba, para almacenarlo usando fileprovider";
                //String nombreficheroconfecha = "TEXTO" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".TXT";
                //
                //
                //// Fileprovider en el directorio DCIM del área de almacenamiento EXTERNO PUBLICO 
                //// ---------------------------------------------------------------------------------------
                //File directorioEnDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                ////File directorioEnDCIM = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "videosmios");
                ////if(!directorioEnDCIM.exists()){
                ////    directorioEnDCIM.mkdirs();
                ////}
                //
                //File ficherointermedio = new File(directorioEnDCIM, nombreficheroconfecha);
                //Uri uriprovider1 = FileProvider.getUriForFile(
                //        getApplicationContext(),
                //        BuildConfig.APPLICATION_ID + ".fileprovider",    //  BuildConfig.APPLICATION_ID identifica el paquete del proyecto
                //        ficherointermedio);
                //
                //File ficherosalida1 = new File(uriprovider1.getPath());
                //
                //
                //grantUriPermission( BuildConfig.APPLICATION_ID, uriprovider1, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //
                //guardarString_enFile(ficherosalida1, texto);
                //
                //Toast.makeText(MainActivity.this, "FICHERO CREADO", Toast.LENGTH_SHORT).show();






                usarAlmacenamiento_Interno_Raiz();
                usarAlmacenamiento_Interno_micarpeta();


                usarAlmacenamiento_ExternoApp_Raiz();
                usarAlmacenamiento_ExternoApp_micarpeta();


                usarAlmacenamiento_PublicoDispositivo_raiz();
                //usarAlmacenamiento_PublicoDispositivo_carpetaPropia();
                
                
                
            }
        });
    }

    public void usarAlmacenamiento_Interno_Raiz(){
        File f1 = GUT_Files.getFileAreaPrivadaFiles(this, "cosas1.txt");
        GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
        String contenido = GUT_Files.leerStringDeFichero(f1);
        tvcontenido.setText(contenido);
    }
    public void usarAlmacenamiento_Interno_micarpeta(){
        File f1 = GUT_Files.getFileAreaPrivadaMiCarpeta(this, "micarpeta", "cosas2.txt");
        GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
        String contenido = GUT_Files.leerStringDeFichero(f1);
        tvcontenido.setText(contenido);
    }
    public void usarAlmacenamiento_ExternoApp_Raiz(){
        File f1 = GUT_Files.getFileAreaPublicaAppFiles(this, "cosas3.txt");
        GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
        String contenido = GUT_Files.leerStringDeFichero(f1);
        tvcontenido.setText(contenido);
    }
    public void usarAlmacenamiento_ExternoApp_micarpeta(){
        File f1 = GUT_Files.getFileAreaPublicaAppCarpetaPropia(this, "micarpeta", "cosas4.txt");
        GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
        String contenido = GUT_Files.leerStringDeFichero(f1);
        tvcontenido.setText(contenido);
    }
    
    public void usarAlmacenamiento_PublicoDispositivo_raiz(){
        File f1 = GUT_Files.getFileAreaPublicaDispositivo(this, Environment.DIRECTORY_MUSIC, "cosas5.txt");
        GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
        String contenido = GUT_Files.leerStringDeFichero(f1);
        tvcontenido.setText(contenido);
    }
    //public void usarAlmacenamiento_PublicoDispositivo_carpetaPropia(){
    //    File f1 = GUT_Files.getFileAreaPublicaDispositivoCarpetaPropia(this, Environment.DIRECTORY_MUSIC, "micarpeta", "cosas6.txt");
    //    GUT_Files.escribirStringEnFichero(f1, "Contenido del fichero privado en raiz", false); 
    //    String contenido = GUT_Files.leerStringDeFichero(f1);
    //    tvcontenido.setText(contenido);
    //}
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void pedirUnPermiso() {

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
        String permisoSolicitado = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permisoSolicitado) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "No se necesita pedir ningun permiso", Toast.LENGTH_LONG).show();
            accionesConPermiso();
        } else {
            ActivityResultLauncher<String> miARL_pedirUnPermiso =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                                @Override
                                public void onActivityResult(Boolean isGranted) {
                                    if (isGranted) {
                                        Toast.makeText(getApplicationContext(), "PERMISO CONCEDIDO", Toast.LENGTH_LONG).show();
                                        accionesConPermiso();
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "PERMISO DENEGADO !!!!!!!!!!!", Toast.LENGTH_LONG).show();
                                        accionesConPermiso();
                                        //accionesSinPermiso();
                                    }
                                }
                            }
                    );
            // lanzar la peticion (en un listener, si se quiere)
            miARL_pedirUnPermiso.launch(permisoSolicitado);
        }

    }

    // _________________________________________ PERMISOS
    //public void pedirVariosPermisos() {
    //    final String[] ARR_PERMISOS = {
    //            Manifest.permission.WRITE_CONTACTS,
    //            Manifest.permission.WRITE_EXTERNAL_STORAGE
    //    };
    //    ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
    //            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
    //                @Override
    //                public void onActivityResult(Map<String, Boolean> isGranted) {
    //
    //                    Log.d("PedirPermisos", "-- respuesta del activity result MULTIPLE:" + isGranted.toString());
    //                    if (isGranted.containsValue(false)) {
    //                        Log.d("PedirPermisos", "NO SE han aceptado los permisos");
    //                        accionesSinPermisos();
    //                    } else {
    //                        // Nos han concedido los permisos..... seguimos adelante
    //                        Log.d("PedirPermisos", "SI SE han aceptado los permisos");
    //                        accionesConPermisos();
    //                    }
    //                }
    //            });
    //
    //    // lanzar la peticion (en un listener, si se quiere)
    //    if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
    //        Log.d("PedirPermisos", "Pedimos permisos");
    //        miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
    //    } else {
    //        Log.d("PedirPermisos", "Ya tenemos todos los permisos...");
    //        accionesConPermisos();
    //    }
    //}
    //
    //private boolean tieneYaEstosPermisos(String[] permisos) {
    //    if (permisos != null) {
    //        for (String cadaPermiso : permisos) {
    //            if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
    //                Log.d("PedirPermisos", "Este permiso no lo tenemos: " + cadaPermiso);
    //                return false;
    //            }
    //            Log.d("PedirPermisos", "Este permiso ya lo tenemos: " + cadaPermiso);
    //        }
    //        return true;
    //    }
    //    return false;
    //}

    public void accionesSinPermiso() {
    }

}