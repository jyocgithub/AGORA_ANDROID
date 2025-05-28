package com.example.jyocgmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements IGUT_Permisos{

    ImageView imagen;
    Button btnGMapsApp;
    Button btnMapasPropio;
    EditText etDireccionApp, etDireccionActividad;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

        final String[] ARR_PERMISOS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        GUT_Permisos.pedirVariosPermisos(this, ARR_PERMISOS);

        //String permisoSolicitado = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        //GUT_Permisos.pedirUnPermiso(this, permisoSolicitado);
        
    }
    
    @Override
    public void accionesConPermisos() {

        // intenta que el layput SUBA cuando aparezca el teclado para ver donde estamos escribiendo
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        imagen = findViewById(R.id.imageView);
        btnGMapsApp = findViewById(R.id.btnGMapsApp);
        btnMapasPropio = findViewById(R.id.btnMapasPropio);
        etDireccionApp = findViewById(R.id.etDireccionApp);
        etDireccionActividad = findViewById(R.id.etDireccionActividad);

        btnGMapsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dir = etDireccionApp.getText().toString();
                llamarAMapsAppConDireccion(dir);
            }
        });

        btnMapasPropio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dir = etDireccionActividad.getText().toString();
                llamarAMapsActivityPropia(dir);
            }
        });
    }


    public void llamarAMapsActivityPropia(String direccion) {
        String dir = ((EditText)findViewById(R.id.etDireccionActividad)).getText().toString();
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("DIRECCION", dir);
        startActivity(intent);
    }

    public void llamarAMapsAppConDireccion(String direccion) {
        // buscamos la URI con la localizacion de la direccion que tiene el contacto, CP en este caso
        Uri gmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(direccion));
        // creamos un intent que llame a google maps con la URI que acabamos de crear
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        // vemos si existe en el movil un gestor de mapas
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Ejecutar el intent...
            startActivity(mapIntent);
        }
    }
    
    public void llamarAMapsAppConLatLong(double lat, double longi) {
        // buscamos la URI con la localizacion de la direccion que tiene el contacto
        Uri gmIntentUri = Uri.parse("geo:"+lat+","+longi);
        // creamos un intent que llame a google maps con la URI que acabamos de crear
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        // vemos si existe en el movil un gestor de mapas
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Ejecutar el intent...
            startActivity(mapIntent);
        }
    }

   

    @Override
    public void accionesSinPermisos() {
        Toast.makeText(this, "No se han concedido los permisos necesarios", Toast.LENGTH_SHORT).show();
    }
}