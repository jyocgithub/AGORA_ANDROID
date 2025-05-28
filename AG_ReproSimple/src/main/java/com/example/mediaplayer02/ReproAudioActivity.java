package com.example.mediaplayer02;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class ReproAudioActivity extends AppCompatActivity {
    JYOCReproMusica miRepro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repro_audio);

         String   uriRecurso = "android.resource://" + getPackageName() + "/" + R.raw.benny;
        miRepro = new JYOCReproMusica(this, findViewById(R.id.lyoutfondomusica), uriRecurso);
        miRepro.inicializar();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        miRepro.mediaPlayer.stop();
        miRepro.mediaPlayer.release();
    }

    @Override
    // El método onTouchEvent nos permite controlar qué hacer cuando el usuario toca la pantalla
    public boolean onTouchEvent(MotionEvent event) {
        // En este caso, cuando el usuario toque la pantalla,
        // mostramos los controles de reproducción
        miRepro.mediaController.show(0);
        return false;
    }



}
