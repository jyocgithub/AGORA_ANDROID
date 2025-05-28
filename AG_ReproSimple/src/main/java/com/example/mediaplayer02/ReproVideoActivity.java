package com.example.mediaplayer02;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ReproVideoActivity extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    Uri uri = null;
    JYOCReproVideo reproVideo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repro_video);

        // Obtenemos la referencia al widget VideoView
        videoView = findViewById(R.id.vvVideo);

        String tipo = getIntent().getStringExtra("origen");

        if (tipo.equals("raw")) {
            reproVideo = new JYOCReproVideo(this, videoView, "raw", R.raw.tierra);
        }
        if (tipo.equals("stream")) {
            String videoUrl = "https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
            reproVideo = new JYOCReproVideo(this, videoView, "stream", videoUrl);
        }

    }

    // Muestre el MediaController cuando el usuario pulse en la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        reproVideo.getMediaController().show();
        return false;
    }

    //
    //@SuppressLint("MissingInflatedId")
    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_repro_video);
    //
    //    // Obtenemos la referencia al widget VideoView
    //    videoView = findViewById(R.id.vvVideo);
    //
    //
    //    String tipo = getIntent().getStringExtra("origen");
    //    if (tipo.equals("raw")) {
    //        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tierra);
    //        //uri = GUT_Recursos.getURIRecursoFromNombreRecurso(this,"raw", "tierra");
    //    }
    //    if (tipo.equals("stream")) {
    //        videoUrl = "https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    //        uri = Uri.parse(videoUrl);
    //    }
    //    //if (tipo.equals("asset")) {
    //    //    uri = GUT_Recursos.getURIFromAssetName(this, "asset:///" + "tierra2.mp4");
    //    //}
    //
    //
    //    // Creamos el objeto MediaController
    //    mediaController = new MediaController(this);
    //    // Establecemos el ancho del MediaController
    //    mediaController.setAnchorView(videoView);
    //    // Al contenedor VideoView le añadimos los controles
    //    videoView.setMediaController(mediaController);
    //    mediaController.requestFocus();
    //
    //    // Cargamos el contenido multimedia (el vídeo) en el VideoView
    //    videoView.setVideoURI(uri);
    //
    //    // Registramos el callback que será invocado cuando el vídeo esté cargado y
    //    // preparado para la reproducción
    //    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
    //        @Override
    //        public void onPrepared(MediaPlayer mp) {
    //            Toast.makeText(ReproVideoActivity.this, "Estamos preparados", Toast.LENGTH_SHORT).show();
    //            mediaController.show(0);
    //            videoView.start();
    //        }
    //    });
    //
    //
    //
    //


}

//
//class JYOCReproVideoasdfasd {
//
//    Context context;
//    String tipo, sRecurso;
//    int iRecurso;
//    Uri uri;
//    VideoView videoView;
//    MediaController mediaController;
//
//    public JYOCReproVideo(Context context, VideoView videoView, String tipo, String sRecurso) {
//        this.context = context;
//        this.tipo = tipo;
//        this.sRecurso = sRecurso;
//        this.videoView = videoView;
//        lanzar();
//    }
//
//    public JYOCReproVideo(Context context, VideoView videoView, String tipo, @RawRes int iRecurso) {
//        this.context = context;
//        this.tipo = tipo;
//        this.iRecurso = iRecurso;
//        this.videoView = videoView;
//        lanzar();
//    }
//
//    public void lanzar() {
//
//        if (tipo.equals("raw")) {
//            uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + iRecurso);
//        }
//        if (tipo.equals("stream")) {
//            uri = Uri.parse(sRecurso);
//        }
//
//
//        // Creamos el objeto MediaController
//        mediaController = new MediaController(context);
//        // Establecemos el ancho del MediaController
//        mediaController.setAnchorView(videoView);
//        // Al contenedor VideoView le añadimos los controles
//        videoView.setMediaController(mediaController);
//        mediaController.requestFocus();
//
//        // Cargamos el contenido multimedia (el vídeo) en el VideoView
//        videoView.setVideoURI(uri);
//
//        // Registramos el callback que será invocado cuando el vídeo esté cargado y
//        // preparado para la reproducción
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                Toast.makeText(context, "Estamos preparados", Toast.LENGTH_SHORT).show();
//                mediaController.show(0);
//                videoView.start();
//            }
//        });
//    }
//
//    public MediaController getMediaController() {
//        return mediaController;
//    }
//
//}
