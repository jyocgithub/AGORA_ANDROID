package com.example.ag_livedataviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class SegundaActivity extends AppCompatActivity {
    private LoginViewModel model;
    private TextInputLayout tilPassword;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        // findviews...
        etPassword = findViewById(R.id.etPassword2);
        tilPassword = findViewById(R.id.tilPassword2);


        // NO CREAR un nuevo viewmodel, sino coger el que creamos en el main activity
        // NOOOOOO -> model = new ViewModelProvider(this).get(LoginViewModel.class);
        model = MainActivity.getThis().getModel();

        MutableLiveData<String> mod = model.getPassword();
        String eso = mod.getValue();
        // o ambas lineas  en una sola linea
        //String eso = model.getPassword().getValue();

        etPassword.setText(eso);

        // tener en cuenta que, si modificamos desde aqui el objeto observado en el livedata,
        // se modifica en todos los sitios automaticamente
        (findViewById(R.id.btVolver)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // esto CAMBIA EL EDITTEXT..... DE LA PRIMERA ACTIVIDAD !!!!!  
                model.getPassword().setValue("MIRA COMO HEMOS CAMBIADO...."); 
                
                finish();
            }
        });
    }
}