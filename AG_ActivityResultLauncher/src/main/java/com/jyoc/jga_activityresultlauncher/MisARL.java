package com.jyoc.jga_activityresultlauncher;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


public class MisARL extends AppCompatActivity {

    EditText etEnviar;
    ImageView ivFoto;
    MisARL contexto;
    Uri uriDeFicheroResultante;

    //EXIGE AL MENOS      implementation 'androidx.appcompat:appcompat:1.3.0'

    // EL USO DE  ActivityResultLauncher, DE LA NUEVA Activity Results API PUEDE PARECER COMPLEJO,
    // PUES OBLIGA A DEFINIR UN CONTRATO CADA VEZ QUE SE HACE UNA LLAMADA, PERO GOOGLE PREDEFINIO MUCHOS CONTRATOS PARA QUE SEA MAS FACIL, 
    // Existen los siguientes Contratos:
    //        GetContent()               -- GENERICO PARA OBTENER EL CONTENIDO QUE SE SOLICITE EN EL LAUNCH
    //        StartActivityForResult()
    //        RequestMultiplePermissions()
    //        RequestPermission()
    //        TakePicturePreview()
    //        TakePicture()
    //        TakeVideo()
    //        PickContact()
    //        CreateDocument()
    //        OpenDocumentTree()
    //        OpenMultipleDocuments()
    //        OpenDocument()
    //        GetMultipleContents()

    // FORMATOS DE DATOS EN PETICION/RECOGIDA


    class ARHacerFoto {
        AppCompatActivity activity;
        ActivityResultLauncher<Uri> miARL_hacerFoto;

        ARHacerFoto( AppCompatActivity activity) {
            this.activity = activity;
            
            miARL_hacerFoto = registerForActivityResult(

                    new ActivityResultContracts.TakePicture(),  // TakePicture
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean result) {

                            // Hacer foto (EXIGE QUE EXISTA EL FILEPROVIDER; AQUI NO SE HA HECHO, AL IGUAL QUE PROBABLEMENTE PEDIR PERMISOS D CAMARA)
                            File file = new File(getFilesDir(), "fotoHecha");
                            uriDeFicheroResultante = FileProvider.getUriForFile(contexto, getApplicationContext().getPackageName() + ".provider", file);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(MisARL.this.getContentResolver(), uriDeFicheroResultante);
                  
                                  activity.recibirFotoDeCamara(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


        }

        public void goHacerFoto() {
            // lanzar la peticion (en un listener, si se quiere)
            miARL_hacerFoto.launch(uriDeFicheroResultante);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto = this;

        etEnviar = findViewById(R.id.etEnviar);
        ivFoto = findViewById(R.id.ivFoto);


        // lanzamos los métodos de recogida de las peticiones de ActivityResultLauncher

        paraCuandoLanceActividadEsperandoRespuesta();  // para recoger la respuesta de una segunda actividad
        paraCuandoLancePedirFotoDeGaleria();           // para recoger la foto que se haya solicitado en la galeria
        paraCuandoLanceHacerFoto();                    // para recoger la foto que se haya realizado con la camara
        paraCuandoPidaUnPermiso();                     // para recoger la respuesta del usuario tras pedirle un permiso
        paraCuandoPidaVariosPermisos();                // para recoger la respuesta del usuario tras pedirle varios permisos

        ((Button) findViewById(R.id.btSeguir)).setOnClickListener(n -> {
            lanzarActividadEsperandoRespuesta();
        });

        ((Button) findViewById(R.id.btBuscarFoto)).setOnClickListener((n) -> {
            lanzarPedirFotoDeGaleria();
        });

        ((Button) findViewById(R.id.btHAcerFoto)).setOnClickListener((n) -> {
            lanzarHacerFoto();
        });


        ((Button) findViewById(R.id.btPermiso)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPedirUnPermiso();
            }
        });


        ((Button) findViewById(R.id.btPermisos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPedirVariosPermisos();
            }
        });
    }

    // ============================================================================
    // ============================================================================
    // EJEMPLO DE ABRIR UNA SEGUNDA ACTIVIDA ESPERANDO QUE DEVUELVA ALGO
    // ============================================================================
    // ============================================================================
    // --------------  MEJOR EL EJEMPLO CON DOS METODOS SEPARADOS  ------------- 

    ActivityResultLauncher<Intent> objetoARL_abrirOtraActividad;

    public void paraCuandoLanceActividadEsperandoRespuesta() {
        // 1.- PRIMERO SE CREA UN OBJETO DE LA CLASE ActivityResultLauncher
        // ESE OBJETO ES UN SERVICIO DONDE SE RECIBIRAN LOS DATOS CUANDO SE VUELVA DE OTRA ACTIVIDAD A ESTA

        // DEBE SER REGISTRADO ANTES DE COMPLETAR EL ONCREATE (DENTRO DE ESTE, POR EJEMPLO), 
        // O EN METODO LLAMADO DESDE EL ONCREATE 

        // PERO NO SE PUEDE CREAR DENTRO DE UN LISTENER,
        // Cuando se hace click en un boton y se llama al onclicklistener, la aplicaion esta en estado RESUMED,
        // y en tal estado no se pueden procesar altas de ActivityResultLauncher

        //   LA SEGUNDA ACTIVIDAD DEVUELVE A ESTA PRIMERA UN VALOR CON 
        //      setResult(RESULT_OK,intentHaciaMainActivity);
        objetoARL_abrirOtraActividad = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),  // StartActivityForResult
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Ya no es necesario request codes
                            Intent data = result.getData();
                            String textoRecibido = data.getStringExtra("TEXTODEVUELTO");
                            etEnviar.setText(textoRecibido);

                        }
                    }
                });
    }

    public void lanzarActividadEsperandoRespuesta() {
        // 2.- Y SEGUNDO, SE LANZA LA PETICION. 
        //  ESTO PUEDE ESTAR FUERA DE ONCREATE O INCLUSO EN LISTENER DENTRO DEL ONCREATE 
        Intent intentHaciaSecondActivity = new Intent(MisARL.this, SecondActivity.class);
        String textoAEnviar = etEnviar.getText().toString();
        Avioneta avioneta = new Avioneta("CESNA112", "YYD23S", 800);
        intentHaciaSecondActivity.putExtra("TEXTOENVIADO", textoAEnviar);
        intentHaciaSecondActivity.putExtra("OBJETOAVIONETA", avioneta);
        // LAUNCH ES EL EQUIVALENTE AL ANTIGUO STARTACTIVITY O STARTACTIVITY FOR RESULT
        objetoARL_abrirOtraActividad.launch(intentHaciaSecondActivity);
    }

    // ============================================================================     
    // ============================================================================     
    // EJEMPLO DE ABRIR UNA PETICION DE UN IMAGEN DE LA GALERIA
    // ============================================================================
    // ============================================================================
    ActivityResultLauncher<String> miARL_buscarImagenes;

    public void paraCuandoLancePedirFotoDeGaleria() {
        miARL_buscarImagenes = registerForActivityResult(
                new ActivityResultContracts.GetContent(),  // GetContent
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(MisARL.this.getContentResolver(), uri);
                            ivFoto.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void lanzarPedirFotoDeGaleria() {
        // lanzar la peticion (en un listener, se puede, si se quiere)
        miARL_buscarImagenes.launch("image/*");

    }

    // ============================================================================
    // ============================================================================
    // EJEMPLO DE ABRIR CAMARA Y HACER FOTO
    // ============================================================================
    // ============================================================================

    ActivityResultLauncher<Uri> miARL_hacerFoto;

    public void paraCuandoLanceHacerFoto() {
        miARL_hacerFoto = registerForActivityResult(

                new ActivityResultContracts.TakePicture(),  // TakePicture
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {

                        // Hacer foto (EXIGE QUE EXISTA EL FILEPROVIDER; AQUI NO SE HA HECHO, AL IGUAL QUE PROBABLEMENTE PEDIR PERMISOS D CAMARA)
                        File file = new File(getFilesDir(), "fotoHecha");
                        uriDeFicheroResultante = FileProvider.getUriForFile(contexto, getApplicationContext().getPackageName() + ".provider", file);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(MisARL.this.getContentResolver(), uriDeFicheroResultante);
                            ivFoto.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void lanzarHacerFoto() {
        // lanzar la peticion (en un listener, si se quiere)
        miARL_hacerFoto.launch(uriDeFicheroResultante);
    }


    // ============================================================================
    // ============================================================================
    // ============================================================================
    // ============================================================================
    // EJEMPLO DE PEDIR UN PERMISO
    // ============================================================================
    // ============================================================================
    // ============================================================================
    // ============================================================================
    ActivityResultLauncher<String> miARL_pedirUnPermiso;
    String permisoSolicitado = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public void paraCuandoPidaUnPermiso() {

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permisoSolicitado) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "No se necesita pedir ningun permiso", Toast.LENGTH_LONG).show();
            accionesConPermiso();
        } else {
            miARL_pedirUnPermiso = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean isGranted) {
                            if (isGranted) {
                                Toast.makeText(getApplicationContext(), "PERMISO CONCEDIDO", Toast.LENGTH_LONG).show();
                                accionesConPermiso();
                            } else {
                                Toast.makeText(getApplicationContext(), "PERMISO DENEGADO !!!!!!!!!!!", Toast.LENGTH_LONG).show();
                                accionesSinPermiso();
                            }
                        }
                    }
            );
        }
    }

    public void lanzarPedirUnPermiso() {
        // lanzar la peticion (en un listener, si se quiere)
        miARL_pedirUnPermiso.launch(permisoSolicitado);

    }

    public void accionesConPermiso() {
    }

    public void accionesSinPermiso() {
    }

    // ============================================================================
    // ============================================================================
    // ============================================================================
    // ============================================================================
    // ============================================================================
    // EJEMPLO DE PEDIR VARIOS PERMISOS
    // ============================================================================
    // ============================================================================
    // ============================================================================
    // ============================================================================
    ActivityResultLauncher<String[]> miARL_pedirVariosPermisos;

    final String[] ARR_PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public void paraCuandoPidaVariosPermisos() {
        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

        miARL_pedirVariosPermisos = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> isGranted) {

                Log.d("PedirPermisos", "-- respuesta del activity result MULTIPLE:" + isGranted.toString());
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
    }

    public void lanzarPedirVariosPermisos() {

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

    public void accionesConPermisos() {
    }

    public void accionesSinPermisos() {
    }


}


//// AndroidManifest.xml, en <application>
//<provider
//    android:name="androidx.core.content.FileProvider"
//    android:authorities="${applicationId}.provider"
//    android:exported="false"
//    android:grantUriPermissions="true">
//    <meta-data
//        android:name="androidx.core.content.FileProvider_paths"
//        android:resource="@xml/providers_path" />
//</provider>
//
//
//// provider_paths.xml en res/xml
//<?xml version="1.0" encoding="utf-8"?>
//<paths xmlns:android="http://schemas.android.com/apk/res/android">
//    <!-- Ruta para compartir archivos dentro del directorio de archivos internos -->
//    <files-path name="foto_temp" path="." />
//</paths>
//
//
//// Crear un archivo temporal en el oncreate()
//File filesDir = context.getFilesDir();
//File photoFile = new File(filesDir, "fotoTemporal.jpg");
//// Usar FileProvider para obtener una URI de contenido
//uriDeFicheroResultante = FileProvider.getUriForFile(
//        context,
//        context.getApplicationContext().getPackageName() + ".provider",
//        photoFile);  // URI de tipo content://
//// Lanza el ActivityResultLauncher con la URI del archivo
//activityResultLauncher_hacerfoto.launch(uriDeFicheroResultante);