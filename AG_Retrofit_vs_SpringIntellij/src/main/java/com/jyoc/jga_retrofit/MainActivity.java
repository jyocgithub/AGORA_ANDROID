package com.jyoc.jga_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/* 
   ESTE EJEMPLO CONSUME COMO CLIENTE LOS SERVICIOS SPRING QUE SE PUBLICAN
   EN LA APP JAVA 
   SpringMavenJava8_Intellij
   DICHA APP SE CONECTA A LA BASE DE DATOS
   JYOC_SPRING_BOOT_CON_RETROFIT   
*/
public class MainActivity extends AppCompatActivity implements IRespuestasAsincronas{
    ListView lvAutores;
    ArrayList<String> listaNombreDeAutores;
    ArrayAdapter arrayAdapter;
    Button btCargar, btAnadir, btBorrar, btConsultar;
    RetrofitAutor retrofitAutor;
    EditText etNombre, etGeneracion, etMail, etIdBuscar, etIdBorrar, etRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAutores = findViewById(R.id.lvAutores);
        btCargar = findViewById(R.id.btCargar);
        btAnadir = findViewById(R.id.btAnadir);
        btBorrar = findViewById(R.id.btBorrar);
        btConsultar = findViewById(R.id.btConsultar);
        etNombre = findViewById(R.id.etNombre);
        etGeneracion = findViewById(R.id.etGeneracion);
        etMail = findViewById(R.id.etMail);
        etIdBuscar = findViewById(R.id.etIdBuscar);
        etIdBorrar = findViewById(R.id.etIDborrar);
        etRespuesta = findViewById(R.id.etRespuesta);


        listaNombreDeAutores = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaNombreDeAutores);
        lvAutores.setAdapter(arrayAdapter);

        retrofitAutor = new RetrofitAutor(this);
        //btCargar.setForeground(new ColorDrawable(Color.BLACK));
        btCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrofitAutor.getListaAutores();
            }
        });

        btAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etGeneracion.getText().toString().isEmpty() &&
                        !etMail.getText().toString().isEmpty() &&
                        !etNombre.getText().toString().isEmpty()) {

                    Autor autor = new Autor(0,
                            etNombre.getText().toString(),
                            etMail.getText().toString(),
                            Integer.parseInt(etGeneracion.getText().toString()));

                    retrofitAutor.saveAutor(autor);
                }
            }
        });

        btConsultar.setOnClickListener(v -> {
            if (!etIdBuscar.getText().toString().isEmpty()) {
                int p = Integer.parseInt(etIdBuscar.getText().toString());
                retrofitAutor.getAutor(p);
            }
        });


        btBorrar.setOnClickListener(v -> {
            if (!etIdBorrar.getText().toString().isEmpty()) {
                int p = Integer.parseInt(etIdBorrar.getText().toString());
                retrofitAutor.deleteAutor(p);
            }
        });

    }

    //public void trasGetListaAutores(List<Autor> listaAutores) {
    //    for (Autor autor : listaAutores) {
    //        listaNombreDeAutores.add(autor.getNombre());
    //        Log.d("JYOC***", autor.getNombre());
    //    }
    //
    //    arrayAdapter.notifyDataSetChanged();
    //    Toast.makeText(this, "AUTORES RECIBIDOS !", Toast.LENGTH_LONG).show();
    //
    //
    //}
    //
    //public void trasGetAutor(Autor autor) {
    //    etRespuesta.setText(autor.getNombre());
    //    Toast.makeText(this, "Autor consultado correctamente", Toast.LENGTH_LONG).show();
    //    
    //}

    @Override
    public void trasGetElemento(Object elemento) {
        Autor autor = (Autor) elemento; 
        etRespuesta.setText(autor.getNombre());
        Toast.makeText(this, "Autor consultado correctamente", Toast.LENGTH_LONG).show();

    }

    @Override
    public void trasGetTodos(List elementos) {
        List<Autor> autores = elementos;
        listaNombreDeAutores.clear();
        for (Autor autor : autores) {
            listaNombreDeAutores.add(autor.getIdautor()+"-"+autor.getNombre());
            Log.d("JYOC***", autor.getNombre());
        }

        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, "AUTORES RECIBIDOS !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void trasSaveElemento(Object elemento) {

    }

    @Override
    public void trasDeleteElemento(int elemento) {

    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}










