package com.example.af_;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/*
    Permisos que se deben incluir en el manifest:
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 */

public class VideoSinCamara extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    private Button btnRecorder, btnStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pedirVariosPermisos();

    }

    private void accionesConPermisos() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_video_sin_camara);

        recorder = new MediaRecorder();

        SurfaceView cameraView = findViewById(R.id.surfaceView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);

        btnRecorder = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        btnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabar();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pararDeGrabar();
            }
        });


    }


    private void initRecorder() {

        if (recorder == null) {
            recorder = new MediaRecorder();
        } else {
            try {
                if (recording) {
                    recorder.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        //recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // notice here
        
        
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // notice here
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // notice here
        //recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        

        //recorder.setAudioSamplingRate(44100);
        //recorder.setAudioEncodingBitRate(128000);
        //recorder.setAudioChannels(1);
        

        //CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        //recorder.setProfile(cpHigh);

        // ----- FICHERO
        // API 24 en adelante exige el uso de FileProvider
        // esta restriccion se puede evitar con las dos lineas siguientes 
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "");
        // fecha
        SimpleDateFormat miFormato = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String fechaEnString = miFormato.format(Calendar.getInstance().getTime());
        String nombrefichero = "mificheroImagen" + fechaEnString + ".MP4";
        //    File file = new File(dir, nombrefichero);
        File diryfichero = new File(dir, nombrefichero);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            recorder.setOutputFile(diryfichero);
        }
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
   
        prepareRecorder();
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public void grabar() {
        if (!recording) {
            //prepareRecorder();
            recording = true;
            recorder.start();
        }
    }

    public void pararDeGrabar() {
        if (recording) {
            recorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        initRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }


    @Override
    public void onClick(View view) {
        //        if (recording) {
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //                recorder.pause();
        //            }
        //            recording = false;
        //
        //        }else{
        //            recorder.start();
        //        }
        if (recording) {
            recorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
            recorder.start();
        }
    }


    public void pedirVariosPermisos() {
        ActivityResultLauncher<String[]> miARL_pedirVariosPermisos;
        final String[] ARR_PERMISOS = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
            miARL_pedirVariosPermisos = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> isGranted) {
                    Log.d("OSCAR->PedirPermisos", "-- respuesta del activity result MULTIPLE:" + isGranted.toString());
                    if (isGranted.containsValue(false)) {
                        // no nos han concedido los permisos..... abandonamos
                        Log.d("OSCAR->PedirPermisos", "NO se han aceptado los permisos");
                        finish(); // salir de la app
                    } else {
                        // SI nos han concedido los permisos..... seguimos adelante
                        Log.d("OSCAR->PedirPermisos", "SI se han aceptado los permisos");
                        //                 accionesConPermisos(); // método donde se ejecuta la aplicación
                    }
                }
            });

            Log.d("OSCAR->PedirPermisos", "Pedimos permisos");
            miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
            //multiplePermissionActivityResultLauncher.launch(ARR_PERMISOS);
        } else {
            Log.d("OSCAR->PedirPermisos", "Ya tenemos todos los permisos...");
            accionesConPermisos();  // método donde se ejecuta la aplicación
        }

    }


    private boolean tieneYaEstosPermisos(String[] permisos) {
        if (permisos != null) {
            for (String cadaPermiso : permisos) {
                if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("OSCAR->PedirPermisos", "Este permiso no lo tenemos: " + cadaPermiso);
                    return false;
                }
                Log.d("OSCAR->PedirPermisos", "Este permiso ya lo tenemos: " + cadaPermiso);
            }
            return true;
        }
        return false;
    }


}