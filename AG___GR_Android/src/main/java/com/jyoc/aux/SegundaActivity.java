package com.jyoc.aux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jyoc.pojos_para_guiasrapidas.Gato;
import com.jyoc.pojos_para_guiasrapidas.Perro;

public class SegundaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);


        Intent i = getIntent();
        
        Perro p = (Perro) i.getSerializableExtra("can");
        String gatillo  =i.getStringExtra("cat");
        Gato g = Gato.recuperarDeJson(gatillo);
        
        Log.d("JYOC***", g.getNombre());
        Log.d("JYOC***", g.getEdad()+"");
        Log.d("JYOC***", g.getAlergias().get(0));
        Log.d("JYOC***", g.getAlergias().get(1));
        
    }
}