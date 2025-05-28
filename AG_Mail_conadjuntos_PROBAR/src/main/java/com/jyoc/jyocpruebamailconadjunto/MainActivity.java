package com.jyoc.jyocpruebamailconadjunto;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Activity mainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmarQueExistenTodosEstosPermisos(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    void onCreate_conAccionesSoloConPermiso() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainactivity = this;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bitmap bitmap = JYOCUtilsv4.tomarScreenShot(mainactivity);
                //File file = JYOCUtilsv4.salvarBitmapEnEnDirectorioExternoApp(Environment.DIRECTORY_PICTURES,bitmap,"ScreenShots2","pantallazo.png");
                //if(file!=null){
                //    Toast.makeText(getApplicationContext(), "Pantallazo creado", Toast.LENGTH_SHORT).show();
                //}
                //JYOCUtilsv4.Compartir(file,mainactivity);
                JYOCUtilsv4.enviarMailConFichero(mainactivity, "poipoi", "jkjk h", new String[]{});
                //JYOCUtilsv4.salvarStringEnDirectorioInternoApp(getApplicationContext(), "PEPEPEPE", "prubastxt.txt") ;
            }
        });
    }

    public void confirmarQueExistenTodosEstosPermisos(Activity actividad, String... arrayPermisos) {
        boolean todosLosPemisosOk = true;
        for (String cadapermiso : arrayPermisos) {
            int permiso = ContextCompat.checkSelfPermission(actividad, cadapermiso);
            if (!(ContextCompat.checkSelfPermission(actividad, cadapermiso) == PackageManager.PERMISSION_GRANTED)) {
                todosLosPemisosOk = false;
            }
        }
        if (todosLosPemisosOk) {
            //-- RECORDAR HACER AQUI LO QUE SE DESEE CUANDO HAY PERMISOS   ---------------
            //-- LO NORMAL ES LLAMAR A UN METODO QUE COMPLETE EL ONCREATE  ---------------
             onCreate_conAccionesSoloConPermiso();
        } else {
            ActivityCompat.requestPermissions(this, arrayPermisos, PETICION_DE_PERMISOS);
        }
    }//
    /**
     * onRequestPermissionsResult
     * Complemento al metodo anterior (confirmarQueExistenTodosEstosPermisos) que solicita permisos
     * ESTE METODO HA DE COPAIRSE EN LA ACTIVIDAD QUE INCLUYE EL METODO ANTERIOR; NO VALE INVOCARLO
     * <p>
     * Recordar completar las acciones que se deseen realizar si se conceden o si no se conceden
     * los permisos
     * La concesion normalmente llama al mismo metodo que completa el OnCreate vista en el
     * metodo anterior (confirmarQueExistenTodosEstosPermisos)
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static final int PETICION_DE_PERMISOS = 12321;

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PETICION_DE_PERMISOS) {
            if (grantResults.length > 0) {
                boolean todosLosPemisosOk = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                        todosLosPemisosOk = false;
                    }
                }
                if (todosLosPemisosOk) {
                    //-- RECORDAR HACER AQUI LO QUE SE DESEE CUANDO HAY PERMISOS   ---------------
                    //-- LO NORMAL ES LLAMAR A UN METODO QUE COMPLETE EL ONCREATE  ---------------
                    onCreate_conAccionesSoloConPermiso();

                } else {
                    //-- RECORDAR HACER AQUI LO QUE SE DESEE SI NO HAY PERMISOS ---------------
                    finish(); // algun permiso no se otorgó, terminamos la actividad, no se deja seguir
                }
            } else {
                //-- RECORDAR HACER AQUI LO QUE SE DESEE SI NO HAY PERMISOS ---------------
                finish(); // se canceló al solicitar permisos, terminamos la actividad, no se deja seguir
            }
        }
    }// ---------------------------------------------------- FIN onRequestPermissionsResult

}
