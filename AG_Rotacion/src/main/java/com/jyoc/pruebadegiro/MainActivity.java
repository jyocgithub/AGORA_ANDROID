package com.jyoc.pruebadegiro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        Button bt1 = findViewById(R.id.btrellenar);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv1.setText("Este texto desaparecera cuando se gire el movil");
                tv2.setText("Este se recuperara por programa");
                tv3.setText("Este tambien se recuperara por programa");
            }
        });

        tv4.setText("Este texto nunca desaparece, pues se pinta en el oncreate");

        // OPCION 1
        // recuperamos texto del giro opcion 1: con el Bundle recibido en el onCreate
        if (savedInstanceState != null) {
            // Recuperamos los valores almacenados previamente en el Bundle
            String textoRecuperado = savedInstanceState.getString("TEXTO2");

            // recolocamos los valores donde deben estar
            tv2.setText(textoRecuperado);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // guardar en el bundle de estado de instancia los valores que he de recuperar al girar 
        savedInstanceState.putString("TEXTO2", tv2.getText().toString());
        savedInstanceState.putString("TEXTO3", tv3.getText().toString());

        // guardar el estado de instancia, el Bundle super.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }


    // OPCION 2
    // recuperamos texto del giro opcion 2: con el metodo onRestoreInstanceState
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState); // Llamar a la superclase antes de nada 

        // No hace falta mirar si el Bundle no esta vacio, 
        // este método se lanza automáticamente si el bundle no esta vacio, tras el onStart()

        // Recuperamos los valores almacenados previamente en el Bundle
        String textoRecuperado = savedInstanceState.getString("TEXTO3");
        // recolocamos los valores donde deben estar
        tv3.setText(textoRecuperado);
    }


}