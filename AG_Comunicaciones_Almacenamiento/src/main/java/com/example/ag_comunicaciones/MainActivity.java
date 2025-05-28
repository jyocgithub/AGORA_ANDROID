package com.example.ag_comunicaciones;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ag_comunicaciones.alarmas.AlarmActivity;
import com.example.ag_comunicaciones.compartir.AUX_Compartir;
import com.example.ag_comunicaciones.ficheros.AUX_File;
import com.example.ag_comunicaciones.ficheros.AUX_GetFile;
import com.example.ag_comunicaciones.ficheros.AUX_ReadFile;
import com.example.ag_comunicaciones.ficheros.AUX_WriteFile;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btHacerAlgo;
    public ImageView ivImagen;
    TextView tvcentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btHacerAlgo = findViewById(R.id.btHacerAlgo);
        ivImagen = findViewById(R.id.ivImagen);
        tvcentro = findViewById(R.id.tvCentro);
        pedirVariosPermisos();
    }

    public void accionesConPermisos() {
        pruebasAuxCompartir();
        pruebasAuxAlmacenamiento();
        //pruebasAuxAlarmas();
                
    }

    public void pruebasAuxAlmacenamiento() {
        String mensaje = "TEXTO PARA ALMACENAR\nESTO ES UN FICHERO DE TEXTO";

        //  . . . . . . . . . . . . . . . . . . . . . . . 
        // .               PRUEBAS DE                    .
        // .  ACCESO UNIDAD INTERNA CARPETA FILES        .
        // .      NO NECESITA FILE PROVIDER              .
        //  . . . . . . . . . . . . . . . . . . . . . . . 
        
        // ---- leer y escribir String
        File f = AUX_GetFile.getFile_UnidadInterna_CarpetaFiles(this,"privateAppRoot.txt");
        AUX_WriteFile.writeStringInFileObject(f, mensaje);
        String res = AUX_ReadFile.leerStringDeFile(f);
        tvcentro.setText(res);
        Log.d("***JYOC", res);
        
        
        // ---- leer y escribir Bitmap
        
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(getResources(), R.drawable.cara_1);
        File fb = AUX_GetFile.getFile_UnidadInterna_CarpetaFiles(this,"privateAppRoot.bmp");
        AUX_File.guardarBitMapEnFile(this,fb, imagenEnBmp);
        Bitmap bmp= AUX_ReadFile.leerBitMapDeFile(this,fb);
        ivImagen.setImageBitmap(bmp);



        // envio bitmap correo
        //Uri uriDelDFichero = Uri.fromFile(new File(fb.getAbsolutePath()));
        //Intent shareIntent = new Intent();
        //shareIntent.setAction(Intent.ACTION_SEND);
        //// Example: content://com.google.android.apps.photos.contentprovider/...
        //shareIntent.putExtra(Intent.EXTRA_STREAM, uriDelDFichero);
        //shareIntent.setType("image/jpeg");
        //startActivity(Intent.createChooser(shareIntent, null));

        // .................................................... 
        // .                                                   .
        // .               PRUEBAS DE                          .
        // .  ACCESO UNIDAD INTERNA CARPETA PROPIA (no FILES)  .
        // .      NO NECESITA FILE PROVIDER                    .
        // .                                                   .
        //  ................................................... 
        File f2 = AUX_GetFile.getFile_UnidadInterna_CarpetaPropia(this,"carpetamiscosas","privateAppMicarpeta.txt");
        AUX_File.guardarStringEnFile(f2, mensaje);
        String res2 = AUX_ReadFile.leerStringDeFile(f2);
        Log.d("***JYOC", res2);

        //  ................................................. 
        // .                                                 .
        // .               PRUEBAS DE                    .
        // .  ACCESO UNIDAD EXTERNA ZONA APP CARPETA FILES   .
        // .      NO NECESITA FILE PROVIDER                  .
        // .                                                 .
        //  ................................................. 
        //File f3 = AUX_Almacenamiento.getFile_UnidadExternaPrivada_CarpetaRaiz(this,"publicAppRoot.txt");
        //AUX_File.guardarStringEnFile(f3, mensaje);
        //String res3 = AUX_File.leerStringDeFile(f3);
        //Log.d("***JYOC", res3);

        //  ................................................................. 
        // .                                                                 .
        // .               PRUEBAS DE                    .
        // .  ACCESO UNIDAD EXTERNA ZONA APP CARPETA PROPIA DENTRO DE FILES  .
        // .                NO NECESITA FILE PROVIDER                        .
        // .                                                                 .
        //  ................................................................. 
        //File f4 = AUX_Almacenamiento.getFile_UnidadExternaPrivada_CarpetaPropia(this,"miCarpetaPublicApp","externaAppMicarpeta.txt");
        //AUX_File.guardarStringEnFile(f4, mensaje);
        //String res4 = AUX_File.leerStringDeFile(f4);
        //Log.d("***JYOC", res4);

        //  ................................................................. 
        // .                                                                 .
        // .               PRUEBAS DE                                        .
        // .  ACCESO UNIDAD EXTERNA ZONA PUBLICA CON FICHEROS PROPIOS        .
        // .     FUCIONA HASTA API 28  Y  SIN FILE PROVIDER                  .
        // .     NO FUCIONA DESDE API 28                                     .
        //  ................................................................. 
        //File f5 = AUX_Almacenamiento.getFile_UnidadExternaPublica(Environment.DIRECTORY_DOWNLOADS,null,"fichPropioEnDownload.txt");
        //AUX_File.guardarStringEnFile(f5, mensaje);
        //String res5 = AUX_File.leerStringDeFile(f5);
        //Log.d("***JYOC", res5);


        //  ................................................................. 
        // .                                                                 .
        // .               PRUEBAS DE                                        .
        // .  ACCESO UNIDAD EXTERNA ZONA PUBLICA CON FICHEROS AJENOS         .
        // .       NO FUNCIONA DESDE API 28                                  .
        //  ................................................................. 
        //File f6 = AUX_Almacenamiento.getFile_UnidadExternaPublica(Environment.DIRECTORY_PICTURES,null,"fichMILAGRO.txt");
        ////String fileauthorities = BuildConfig.APPLICATION_ID   ".fileprovider";
        //String fileauthorities = "com.example.ag_comunicaciones.fileprovider";
        //Uri contenUri = FileProvider.getUriForFile(getApplicationContext(), fileauthorities, f6);
        ////grantUriPermission(BuildConfig.APPLICATION_ID, contenUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //grantUriPermission("com.example.ag_comunicaciones", contenUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //
        //AUX_File.guardarStringEnFile(f6, mensaje);
        //String res6 = AUX_File.leerStringDeContentUriFile(this, contenUri);
        //if(res6!=null) Log.d("JYOC***1", res6);


        //  ........................................................................................... 
        // .                                                                                           .
        // .               PRUEBAS DE                                                                  .
        // .  ABRIR UN FICHERO, TEXTO O BITMAP, EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION    .
        // .                                                                                           .
        //  ........................................................................................... 
        //AUX_Almacenamiento.abrirFichero_UnidadExternaPublica(this,"image/*");


        //  ........................................................................................... 
        // .                                                                                           .
        // .               PRUEBAS DE                                                                  .
        // .  CREAR UN FICHERO DE TEXTO EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION            .
        // .                                                                                           .
        //  ........................................................................................... 
        //AUX_Almacenamiento.crearFicheroTexto_UnidadExternaPublica(this,"nomnbreejemplo.txt", "TEXTO PARA UN NUEVO FICHERO\nULTIMA LINEA");


        //  ........................................................................................... 
        // .                                                                                           .
        // .               PRUEBAS DE                                                                  .
        // .  CREAR UN FICHERO DE BITMAP EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION           .
        // .                                                                                           .
        //  ........................................................................................... 
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cara_1);
        //AUX_Almacenamiento.crearFicheroBitmap_UnidadExternaPublica(this,"imagenejemplo.png", bitmap);


    }

    public void pruebasAuxCompartir() {

        //buscarArchivoEnDispositivo_preparar(this, "image/*");

        btHacerAlgo.setOnClickListener(view -> {
            // ........... buscar archivo en el movil
            //buscarArchivoEnDispositivo_lanzar(this, "image/*");

            // .........  llamada telefonica
            //AUX_Compartir.hacerLlamadaTelefonica(MainActivity.this,"445566554");

            //  ......... mandar sms con app 
            //AUX_Compartir.enviarSMSConAPP(MainActivity.this,"mensaje", "445566554");

            //  ......... mandar sms sin app 
            //AUX_Compartir.enviarSMSSinAPP(MainActivity.this,"mensaje", "445566554");

            //  ......... enviar mail
            //AUX_Compartir.enviarMail(MainActivity.this,"EL ASUNTO","EL MENSAJE", "javayotrascosas@gmail.com");


            // =============================================
            // ........... enviar mail con anexo
            // =============================================

            //               FUNCIONAN CON API 28 EN EL MOVIL Y MIN 28 Y SDK 32
            //               EN EL PATH_XML SOLO NECESITA MARCARSE EL DIR . 
            File file = new File(   getFilesDir() , "privateAppRoot.bmp");
            //File file = new File(   getFilesDir() "/misfiles"  , "fich4.txt");
            //File file = new File(   getFilesDir() "/misfiles/bin"  , "fich16.txt");
            //File file = new File(getExternalFilesDir("."), "fich7.txt");
            //File file = new File(   getExternalFilesDir("./miscosas")   , "fich8.txt");
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)   , "fich11.txt");  // EN PICTURES/RAIZ
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) "/bin"   , "fich14.txt");  // EN PICTURES/BIN
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)   , "fich10.txt");

            AUX_Compartir.enviarMailConFichero(MainActivity.this,"EL ASUNTO","EL MENSAJE", new String[]{"javayotrascosas@gmail.com"}, file);

            //               NO FUNCIONAN CON API 30 EN EL MOVIL Y MIN 28 Y SDK 32
            //               SON LAS ZONAS PUBLICAS DEL MOVIL
            //               PERO NO SON NECESARIAS POR QUE PARA ANEXAR UN DOC DE HAY 
            //               SE PUEDE HACER DESDE LA APP DE CORREO 
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)   , "fich11.txt");  // EN PICTURES/RAIZ
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) "/bin"   , "fich14.txt");  // EN PICTURES/BIN
            //File file = new File(   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)   , "fich10.txt");


            // ...................  compartir un ARCHIVO con otras apps
            // AUX_Compartir.compartirArchivoConOOtrasApps(this, file);

            // ...................  compartir un TEXTO con otras apps
            // AUX_Compartir.compartirTextoConOtrasAPPs(this, "texto que comparto") ;


        });


        //  ........................................................................................... 
        // .                                                                                           .
        // .               PRUEBAS DE                                                                  .
        // .  BUSCAR UN FICHERO EN EL DISPOSITIVO 
        // .                                                                                           .
        //  ........................................................................................... 
        //ActivityResultLauncher<String> ARL_buscarArchivo;
        //public void buscarArchivoEnDispositivo_preparar(AppCompatActivity activity, String tipofichero){
        //    ARL_buscarArchivo = activity.registerForActivityResult(
        //            new ActivityResultContracts.GetContent(),
        //            new ActivityResultCallback<Uri>() {
        //                @Override
        //                public void onActivityResult(Uri uri) {
        //                    buscarArchivoEnDispositivo_resultados( activity, uri, tipofichero);
        //                }
        //            });
        //}
        //public void buscarArchivoEnDispositivo_lanzar(AppCompatActivity activity, String tipofichero){
        //    ARL_buscarArchivo.launch(tipofichero);
        //}
        //public void buscarArchivoEnDispositivo_resultados(AppCompatActivity activity,Uri uri, String tipofichero){
        //    try {
        //
        //        Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        //        ImageView ivImagen = findViewById(R.id.ivImagen);
        //        ivImagen.setImageBitmap(bitmap);
        //   
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //}

    }

    public void pruebasAuxAlarmas() {


        //  . . . . . . . . . . . . . . . . . . . . . . . 
        // .               PRUEBAS DE                    .
        // .            PONER UNA ALARMA                 .
        //  . . . . . . . . . . . . . . . . . . . . . . . 

        //AUX_Alarmas.setAlarmaAbriendoApp(this,"mensaje de al alarma",17,01);
        
        startActivity(new Intent(this, AlarmActivity.class));
        
        //AUX_File.guardarStringEnFile(f, mensaje);
        //Log.d("JYOC***1", res); 
        //String res = AUX_File.leerStringDeFile(f);
        //File f = AUX_Almacenamiento.getFile_UnidadInterna_CarpetaFiles(this,"privateAppRoot.txt");
        
        
        
    }

    public void pedirVariosPermisos() {
        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses.permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
        final String[] ARR_PERMISOS = {
                Manifest.permission.CALL_PHONE,
                //Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //Manifest.permission.SET_ALARM,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS
        };
        ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> isGranted) {

                        Log.d("PedirPermisos", ".. respuesta del activity result MULTIPLE:" + isGranted.toString());
                        if (isGranted.containsValue(false)) {
                            Log.d("PedirPermisos", "NO SE han aceptado los permisos");
                            accionesSinPermisos();
                        } else {
                            // Nos han concedido los permisos..... seguimos adelante
                            Log.d("PedirPermisos", "SI SE han aceptado los permisos");
                            accionesConPermisos();
                        }
                    }
                });

        // lanzar la peticion (en un listener, si se quiere)
        if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
            Log.d("PedirPermisos", "Pedimos permisos");
            miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
        } else {
            Log.d("PedirPermisos", "Ya tenemos todos los permisos...");
            accionesConPermisos();
        }
    }

    private boolean tieneYaEstosPermisos(String[] permisos) {
        if (permisos != null) {
            for (String cadaPermiso : permisos) {
                if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PedirPermisos", "Este permiso no lo tenemos: " + cadaPermiso);
                    return false;
                }
                Log.d("PedirPermisos", "Este permiso ya lo tenemos: " + cadaPermiso);
            }
            return true;
        }
        return false;
    }

    public void accionesSinPermisos() {
    }

}