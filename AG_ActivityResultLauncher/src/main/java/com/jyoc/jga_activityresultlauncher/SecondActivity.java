package com.jyoc.jga_activityresultlauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    EditText etVolver;
    Button btVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etVolver = findViewById(R.id.etVolver);
        btVolver = findViewById(R.id.btVolver);
        
        Intent intentDesdeMainActivity = getIntent();
        String textoRecibido= intentDesdeMainActivity.getStringExtra("TEXTOENVIADO");
        Avioneta avioneta = intentDesdeMainActivity.getParcelableExtra("OBJETOAVIONETA");
        etVolver.setText(textoRecibido + avioneta.toString());
        
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDesdeMainActivity = getIntent();
                String textoAEnviar = etVolver.getText().toString();
                intentDesdeMainActivity.putExtra("TEXTODEVUELTO",textoAEnviar);
                setResult(RESULT_OK,intentDesdeMainActivity);
                finish();
            }
        });
    }


}