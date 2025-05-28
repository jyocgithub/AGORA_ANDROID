package com.example.mediaplayer02;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;

import java.io.IOException;

public class JYOCReproMusica  implements MediaController.MediaPlayerControl{
    String uriRecurso;
    public MediaController mediaController;
    public MediaPlayer mediaPlayer;
    public Handler handler;
    public Context context;
    public View cLayoutMadre;
    //public ConstraintLayout cLayoutMadre;

    protected JYOCReproMusica(Context context, View cLayoutMadre, String uriRecurso) {
        //protected MiRepro(Context context, ConstraintLayout cLayoutMadre,  String uriRecurso) {
        this.uriRecurso = uriRecurso;
        this.context = context;
        this.cLayoutMadre = cLayoutMadre;
    }

    public void inicializar() {
        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(context);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(cLayoutMadre);
        handler = new Handler();

        try {
            mediaPlayer.setDataSource(context, Uri.parse(uriRecurso));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.post(new Runnable() {
                    public void run() {
                        mediaController.setEnabled(true);
                        // Se muestra el control en la pantalla. Tras 20 segundos de inactividad, el control se ocultará
                        mediaController.show(0);
                        //mediaPlayer.start();
                    }
                });
            }
        });
    }


    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
        //mediaController.show(0);
    }

}
