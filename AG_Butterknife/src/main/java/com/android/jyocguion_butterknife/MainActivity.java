package com.android.jyocguion_butterknife;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Metemos esto en el gradel 
    //    implementation 'com.jakewharton:butterknife:10.0.0'
    //    annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'

    //ASeguramos que en el gradle aparece, en el capitulo 
    // androd{
    //      ...
    //      compileOptions {
    //              sourceCompatibility JavaVersion.VERSION_1_8
    //              targetCompatibility JavaVersion.VERSION_1_8
    //      }
    // 
    // }

    //Marcamos cada view con una anotacion;
    //@BindView(R.id.btPrueba)
    //Button btPrueba;

    @BindView(R.id.tvMensaje)
    TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idenrificamkos que actividad usa butterknife;
        ButterKnife.bind(this);


        Toast.makeText(this, "Hello from Butterknife OnClick annotation", Toast.LENGTH_LONG).show();
        // EL CLICK SE HACE CON ANOTCION , ver mas abajo
        //btPrueba.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        tvMensaje.setText("CAMBIADO !!!");
        //    }
        //});
    }

    // EL CLICK SE HACE CON ANOTACION, AQUI
    @OnClick(R.id.btPrueba)
    public void submit() {
        Toast.makeText(this, "Hello from Butterknife OnClick annotation", Toast.LENGTH_LONG).show();
        tvMensaje.setText("CAMBIADO !!!");
    }
}