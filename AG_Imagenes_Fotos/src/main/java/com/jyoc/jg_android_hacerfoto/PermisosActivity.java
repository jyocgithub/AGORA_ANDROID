package com.jyoc.jg_android_hacerfoto;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Map;

public class PermisosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);
        pedirVariosPermisos();
    }//  FIN  onCreate


    public void accionesConPermisos(){
        
        startActivity(new Intent(this, MainFotoActivity.class));
    }

    public void accionesSinPermisos(){
        Toast.makeText(this, "no tiene permisos", Toast.LENGTH_SHORT).show();
    }

    // +---------------------------------------------+
    // |                                             |
    // |                                             |
    // |       PEDIR VARIOS PERMISOS                 |
    // |                                             |
    // |                                             |
    // +---------------------------------------------+
    public void pedirVariosPermisos() {
        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
        final String[] ARR_PERMISOS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> isGranted) {
                        if (isGranted.containsValue(false)) {
                            accionesSinPermisos();
                        } else {
                            // Nos han concedido los permisos..... seguimos adelante
                            accionesConPermisos();
                        }
                    }
                });

        // lanzar la peticion (en un listener, si se quiere)
        if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
            miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
        } else {
            accionesConPermisos();
        }
    }

    private boolean tieneYaEstosPermisos(String[] permisos) {
        if (permisos != null) {
            for (String cadaPermiso : permisos) {
                if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}