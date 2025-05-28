package com.example.spinnerpropio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int nivel = 0;
    Button btSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSpinner = findViewById(R.id.btSpinner);
        
        // crear spinner y adaptador propio y asignarlo;
        IconosSpinner.iniciarArrayIconos();
        Spinner mSpinner = findViewById(R.id.spSuperior);
        MiAdaptadorSpinner customAdapter = new MiAdaptadorSpinner(this, R.layout.img_spinner, IconosSpinner.getArrayIconosSpinner());
        mSpinner.setAdapter(customAdapter);
        
        btSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearDialogoConSpinnerYSinLayoutDeDialogo();
                //crearDialogoConSpinnerYConLayoutDeDialogo();
            }
        });
        
        
        String nombre = getNombreDeUnView(btSpinner) ;
        Log.d("++++++++++++++++++++++++++++++*JYOC" , nombre);
    }


    public void crearDialogoConSpinnerYSinLayoutDeDialogo() {
        // se puede hacer creando directamente el spinner y añadiendolo al AlertDialog.
        // Evitamos asi tener un layout propio del Dialog

        IconosSpinner.iniciarArrayIconos();
        MiAdaptadorSpinner customAdapter = new MiAdaptadorSpinner(this, R.layout.img_spinner, IconosSpinner.getArrayIconosSpinner());

        Spinner miSpinner = new Spinner(this);
        miSpinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        miSpinner.setAdapter(customAdapter);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);

        mBuilder.setTitle("Elige un personaje");
        
        mBuilder.setPositiveButton("¡ Quiero este !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (miSpinner.getSelectedItemPosition()) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Elegido icono 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Elegido icono 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Elegido icono 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Elegido icono 4", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(miSpinner);
        mBuilder.create().show();
    }


    public void crearDialogoConSpinnerYConLayoutDeDialogo() {

        View layoutDialogo = getLayoutInflater().inflate(R.layout.dialogo_spinner, null);
        
        Spinner miSpinner =layoutDialogo.findViewById(R.id.spinner);

        MiAdaptadorSpinner customAdapter = new MiAdaptadorSpinner(this, R.layout.img_spinner, IconosSpinner.getArrayIconosSpinner());
        miSpinner.setAdapter(customAdapter);
        
        TextView tvPeticion=layoutDialogo.findViewById(R.id.tvPeticion);
        tvPeticion.setText("Elige un personaje");
        
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setPositiveButton("¡ Quiero este !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (miSpinner.getSelectedItemPosition()) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Elegido icono 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Elegido icono 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Elegido icono 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Elegido icono 4", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(layoutDialogo);
        mBuilder.create().show();
    }

    public String getNombreDeUnView(View v){
        String nombreentero = getResources().getResourceName(v.getId());
        String nombresimple = nombreentero.substring(nombreentero.lastIndexOf("/") + 1);
        return nombresimple;
    }
}
