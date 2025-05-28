package com.example.ag_videoconfileprovider;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    VideoView vvVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirVariosPermisos();
    }

    public void accionesConPermisos() {
        vvVideo = findViewById(R.id.vvVideo);

        // Añadimos controles al videoview
        MediaController mediaController = new MediaController(this);
        vvVideo.setMediaController(mediaController);
        mediaController.setAnchorView(vvVideo);


        vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer m) {
                Log.d("*** videoView", "Listo para empezar");
                //Si queremos auto-start, arrancamos el video sin mas
                vvVideo.start();
            }
        });

        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("*** videoView", "terminado");
            }
        });

        ((Button) findViewById(R.id.btComenzar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabarVideo();
            }
        });
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void grabarVideo() {


        // creamos el intent para grabar video
        Intent intentParaHacerVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // Calidad del video, 1, la mas alta
        intentParaHacerVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        
        // --------------- SIN FILE PROVIDER 
        // Ponemos el nombre del fichero donde se guarda el video, y carpeta
        // API 24 en adelante exige el uso de FileProvider
        // esta restriccion se puede evitar con las dos lineas siguientes 
        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());

        //Uri uriDelDFichero = Uri.fromFile(new File(ficheroVideo.getAbsolutePath()));
        //intentParaHacerVideo.putExtra(MediaStore.EXTRA_OUTPUT, uriDelDFichero);
        
        
        // --------------- CON FILE PROVIDER
        //File directorioEnDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File directorioEnDCIM = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "videosmios");
        if(!directorioEnDCIM.exists()){
            directorioEnDCIM.mkdirs();
        }
        String textofecha = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nombreficheroconfecha = "VIDEO" + textofecha + ".MP4";
        File ficheroVideo = new File(directorioEnDCIM,nombreficheroconfecha);
        
        Uri uriprovider = FileProvider.getUriForFile(
                getApplicationContext(),  
                BuildConfig.APPLICATION_ID+".fileprovider",    //  BuildConfig.APPLICATION_ID identifica el paquete del proyecto
                ficheroVideo);
        
        intentParaHacerVideo.putExtra(MediaStore.EXTRA_OUTPUT, uriprovider);

        if (intentParaHacerVideo.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentParaHacerVideo, REQUEST_VIDEO_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            vvVideo.setVideoURI(videoUri);
        }
    }


    // _________________________________________ PERMISOS
    public void pedirVariosPermisos() {
        final String[] ARR_PERMISOS = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
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