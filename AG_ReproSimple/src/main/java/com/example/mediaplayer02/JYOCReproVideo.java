package com.example.mediaplayer02;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RawRes;

public class JYOCReproVideo {

    Context context;
    String tipo, sRecurso;
    int iRecurso;
    Uri uri;
    VideoView videoView;
    MediaController mediaController;

    public JYOCReproVideo(Context context, VideoView videoView, String tipo, String sRecurso) {
        this.context = context;
        this.tipo = tipo;
        this.sRecurso = sRecurso;
        this.videoView = videoView;
        lanzar();
    }

    public JYOCReproVideo(Context context, VideoView videoView, String tipo, @RawRes int iRecurso) {
        this.context = context;
        this.tipo = tipo;
        this.iRecurso = iRecurso;
        this.videoView = videoView;
        lanzar();
    }

    public void lanzar() {

        if (tipo.equals("raw")) {
            uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + iRecurso);
        }
        if (tipo.equals("stream")) {
            uri = Uri.parse(sRecurso);
        }


        // Creamos el objeto MediaController
        mediaController = new MediaController(context);
        // Establecemos el ancho del MediaController
        mediaController.setAnchorView(videoView);
        // Al contenedor VideoView le añadimos los controles
        videoView.setMediaController(mediaController);
        mediaController.requestFocus();

        // Cargamos el contenido multimedia (el vídeo) en el VideoView
        videoView.setVideoURI(uri);

        // Registramos el callback que será invocado cuando el vídeo esté cargado y
        // preparado para la reproducción
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(context, "Estamos preparados", Toast.LENGTH_SHORT).show();
                mediaController.show(0);
                videoView.start();
            }
        });
    }

    public MediaController getMediaController() {
        return mediaController;
    }

}
