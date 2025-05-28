package com.example.ag___pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class SegundaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        
        Cosa objetoCosa1 = (Cosa) getIntent().getSerializableExtra("OBJETO_DE_COSA");
        Cosa objetoCosa2 = (Cosa) getIntent().getExtras().get("OBJETO_DE_COSA");
        Log.d("JAJAJA", objetoCosa2.a);
        Log.d("JAJAJA", objetoCosa2.b+"");
        Log.d("JAJAJA", objetoCosa2.lista.toString());



        ((Button) findViewById(R.id.btSeguir2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SegundaActivity.this, TerceraActivity.class);

                startActivity(i);
                finish();

            }
        });


    }
}