package com.herprogramacion.toolbarapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recycler;
    private LinearLayoutManager lManager;
    private CollapsingToolbarLayout collapser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Añadir la Toolbar
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // crear adaptador
        SimpleAdapter adaptador = new SimpleAdapter(this, Girls.randomList(30));

        // Obtener el Recycler
        recycler =  findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // asignar el nuevo adaptador
        recycler.setAdapter(adaptador);
        
    }

    // crear el menu de la app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // crear listeners del menu de la app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                showSnackBar("Comenzar a buscar...");
                return true;
            case R.id.action_settings:
                showSnackBar("Se abren los ajustes");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Muestra una snackbar
    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG).show();
    }
}
