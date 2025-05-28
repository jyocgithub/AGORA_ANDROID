package com.example.mediaplayer02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btMusica)).setOnClickListener(view -> {
            startActivity(new Intent(this, ReproAudioActivity.class));
        });
        ((Button)findViewById(R.id.btVideoRaw)).setOnClickListener(view -> {
            startActivity(new Intent(this, ReproVideoActivity.class).putExtra("origen","raw"));
        });
        ((Button)findViewById(R.id.btVideoAsset)).setOnClickListener(view -> {
            startActivity(new Intent(this, ReproVideoActivity.class).putExtra("origen","asset"));
        });
        ((Button)findViewById(R.id.btVideoStream)).setOnClickListener(view -> {
            startActivity(new Intent(this, ReproVideoActivity.class).putExtra("origen","stream"));
        });
    }

}
