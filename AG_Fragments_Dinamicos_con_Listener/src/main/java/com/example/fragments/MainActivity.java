package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PrimerFragment.OnComunicarConActivityListener {

    PrimerFragment primerFragment;
    SegundoFragment segundoFragment;
    Button b1, b2, btEnviarMain;
    EditText etTextoDelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        btEnviarMain = findViewById(R.id.btEnviarMain);

        etTextoDelMain = findViewById(R.id.etTextoDelMain);

        primerFragment = new PrimerFragment();
        segundoFragment = new SegundoFragment();

        ////Nos devuelve la instancia del único manager
        //FragmentManager manager = getSupportFragmentManager();

        // ponemos el primer fragmento en la ventana
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, primerFragment)
                .commit();

        //boton Atra
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ponemos el primer fragmento en la ventana
                getSupportFragmentManager()
                        .beginTransaction()
                        // no es necesario pero interesante
                        // Al llamar a addToBackStack(), la transacción se guarda en una pila para que 
                        // el usuario pueda revertir la transacción y recuperar el fragmento anterior 
                        // resionando el botón Atrás.
                        // Si agrega varios cambios a la transacción (como otro add() o remove()) y llama a addToBackStack(), todos los cambios aplicados antes de llamar a commit() se agregan a la pila posterior como una sola transacción y el botón Atrás los invertirá todos juntos.
                        .addToBackStack(null)
                        .replace(R.id.frameLayout, primerFragment)
                        .commit();

            }
        });

        //boton Adelante
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, segundoFragment)
                        .commit();
            }
        });

        btEnviarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primerFragment.recibirDeMain();
            }
        });

    }

    @Override
    public void mensajeAlMainActivity(String texto) {
        Toast.makeText(this, "Estoy en el activity, texto que he recibido: " + texto, Toast.LENGTH_SHORT).show();
        etTextoDelMain.setText(texto);
    }

    @Override
    public String mensajeDelMainActivity()    {
        return etTextoDelMain.getText().toString();
    }
}