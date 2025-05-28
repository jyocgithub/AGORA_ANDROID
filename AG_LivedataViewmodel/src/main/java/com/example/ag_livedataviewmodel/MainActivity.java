package com.example.ag_livedataviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    // tenemos una referencia estatica de nuestro objeto para usarla fuera de el
    private static MainActivity mi_this;
    
    private LoginViewModel model;
    private TextInputLayout tilPassword;
    private EditText etPassword;
    private TextView tvEspejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // asociamos la referencia estatica de este objeto
        // tener en cuenta que no se puede usar desde fuera esta referencia... si no se pasa antes por aqui
        mi_this = this;
        
        // findviews...
        etPassword = findViewById(R.id.etPassword);
        tilPassword= findViewById(R.id.tilPassword);
        tvEspejo= findViewById(R.id.tvEspejo);
                

        // Crear view model
        model = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observar pasando el par owner-observer
        model.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String password) {
                tvEspejo.setText(password);
                tilPassword.setError( password.length() < 6 ? "Son mínimo 6 caracteres" : null);
            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // usamos setValue() si estamos en el hilo principal
                // si no estamos en el hilo principal, sino en uno secundario,
                // debemos usar postValue() en vez de setValue()
                model.getPassword().setValue(editable.toString());
                Log.d("****JYOC", "la password del modelo-live es ahora "+model.getPassword().getValue().toString());
            }
        });
        

        (findViewById(R.id.btPaso)).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, SegundaActivity.class));
        });
        
    }

    // permitimos obtener una referencia estatica de este objeto desde fuera de el
    public static MainActivity getThis(){
        return mi_this;
    }

    public LoginViewModel getModel() {
        return model;
    }
}