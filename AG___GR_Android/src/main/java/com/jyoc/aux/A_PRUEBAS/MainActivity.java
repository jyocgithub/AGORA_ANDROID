package com.example.ag___pruebas;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((Button) findViewById(R.id.btSeguir)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText etUsuario = findViewById(R.id.etUsuario);
                //TextView tvUsuario= findViewById(R.id.tvUsuario);
                //tvUsuario.setError(null);
                //String usuario = etUsuario.getText().toString();
                //if(usuario.isEmpty()){
                //    tvUsuario.setError("No puede quedar vacio");
                //    tvUsuario.requestFocus();
                //}

                Cosa objetoCosa = new Cosa();
                objetoCosa.a = "Pepe";
                ArrayList<Integer> d = new ArrayList<Integer>(Arrays.asList(3, 5, 4, 4));
                objetoCosa.lista = d;
                Intent i = new Intent(MainActivity.this, SegundaActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.putExtra("OBJETO_DE_COSA", objetoCosa);
                startActivity(i);
            }
        });


        
        
        
        
        
    }

}


class Cosa implements Serializable {

    String a;
    String[] b = {"h", "k", "j"};
    ;
    ArrayList<Integer> lista;

}

class Algo {

    String a;
    int n;

}