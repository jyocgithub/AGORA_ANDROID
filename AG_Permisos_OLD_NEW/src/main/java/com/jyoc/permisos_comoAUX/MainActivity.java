package com.jyoc.permisos_comoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.jyoc.permisos_comoAUX.AUX_Permisos;
import com.jyoc.permisos_comoAUX.IAUX_Permisos;
import com.jyoc.permisos_comoactivity_OLD.R;

public class MainActivity extends AppCompatActivity implements IAUX_Permisos {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

        //final String[] arrayDePermisosSolicitados = {
        //        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //        Manifest.permission.READ_CONTACTS,
        //        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //        Manifest.permission.CAMERA
        //};
        //AUX_Permisos.pedirVariosPermisos(this, arrayDePermisosSolicitados);

        String permisoSolicitado = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        AUX_Permisos.pedirUnPermiso(this, permisoSolicitado);
    }

    @Override
    public void accionesConPermisos() {
        Toast.makeText(this, "PERMISOS CONCEDIDOS, ADELANTE !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void accionesSinPermisos() {
        Toast.makeText(this, "PERMISOS NO CONCEDIDOS.. NO SEGUIMOS MAS", Toast.LENGTH_SHORT).show();

    }
}