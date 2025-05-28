package com.jyoc.ag_fragmentsdialogfragment.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jyoc.ag_fragmentsdialogfragment.R;

public class MainActivity extends AppCompatActivity implements IDialogo {
    TextView tvMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMensaje = findViewById(R.id.tvMensaje);
        tvMensaje.setText("0");


        ((Button) findViewById(R.id.btMensaje)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MiDialogFragment midialogof = new MiDialogFragment();
                midialogof.show(getSupportFragmentManager(), 	"midialogofragment");


            }
        });
    }


    @Override
    public void onRespuestaDialogo(String s) {
        Snackbar.make(tvMensaje, "Acumulando valor en el textview", Snackbar.LENGTH_LONG).show();
    }
}

interface IDialogo {
    public void onRespuestaDialogo(String s);
}