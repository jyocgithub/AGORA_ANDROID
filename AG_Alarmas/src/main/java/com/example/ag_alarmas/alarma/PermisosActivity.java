package com.example.ag_alarmas.alarma;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PermisosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_permisos);

        pedirUnPermiso();
    }

    // +---------------------------------------------+
    // |                                             |
    // |                                             |
    // |         PEDIR UN PERMISO                    |
    // |                                             |
    // |                                             |
    // +---------------------------------------------+
    public void pedirUnPermiso() {

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
        String permisoSolicitado = android.Manifest.permission.POST_NOTIFICATIONS;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permisoSolicitado) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "No se necesita pedir ningun permiso", Toast.LENGTH_LONG).show();
            accionesConPermiso();
        } else {
            ActivityResultLauncher<String> miARL_pedirUnPermiso =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                                @Override
                                public void onActivityResult(Boolean isGranted) {
                                    if (isGranted) {
                                        Toast.makeText(getApplicationContext(), "PERMISO CONCEDIDO", Toast.LENGTH_LONG).show();
                                        accionesConPermiso();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "PERMISO DENEGADO !!!!!!!!!!!", Toast.LENGTH_LONG).show();
                                        accionesSinPermiso();
                                    }
                                }
                            }
                    );
            // lanzar la peticion (en un listener, si se quiere)
            miARL_pedirUnPermiso.launch(permisoSolicitado);
        }
    }

    public void accionesConPermiso() {

        startActivity(new Intent(PermisosActivity.this, MainActivity.class));
    }

    public void accionesSinPermiso() {
        finish();
    }

    
    //
    //// +---------------------------------------------+
    //// |                                             |
    //// |                                             |
    //// |       PEDIR VARIOS PERMISOS                 |
    //// |                                             |
    //// |                                             |
    //// +---------------------------------------------+
    //public void pedirVariosPermisos() {
    //    // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
    //    //  <uses-permission android:name="android.permission.READ_CONTACTS" />
    //    // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
    //    final String[] ARR_PERMISOS = {
    //            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //            android.Manifest.permission.WRITE_CONTACTS,
    //            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //            Manifest.permission.READ_EXTERNAL_STORAGE
    //    };
    //
    //    ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
    //            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
    //                @Override
    //                public void onActivityResult(Map<String, Boolean> isGranted) {
    //
    //                    Log.d("PedirPermisos", "-- respuesta del activity result MULTIPLE:" + isGranted.toString());
    //                    if (isGranted.containsValue(false)) {
    //                        Log.d("PedirPermisos", "NO SE han aceptado los permisos");
    //                        accionesSinPermisos();
    //                    } else {
    //                        // Nos han concedido los permisos..... seguimos adelante
    //                        Log.d("PedirPermisos", "SI SE han aceptado los permisos");
    //                        accionesConPermisos();
    //                    }
    //                }
    //            });
    //
    //    // lanzar la peticion (en un listener, si se quiere)
    //    if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
    //        Log.d("PedirPermisos", "Pedimos permisos");
    //        miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
    //    } else {
    //        Log.d("PedirPermisos", "Ya tenemos todos los permisos...");
    //        accionesConPermisos();
    //    }
    //}
    //
    //private boolean tieneYaEstosPermisos(String[] permisos) {
    //    if (permisos != null) {
    //        for (String cadaPermiso : permisos) {
    //            if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
    //                Log.d("PedirPermisos", "Este permiso no lo tenemos: " + cadaPermiso);
    //                return false;
    //            }
    //            Log.d("PedirPermisos", "Este permiso ya lo tenemos: " + cadaPermiso);
    //        }
    //        return true;
    //    }
    //    return false;
    //}
    //public void accionesConPermisos(){
    //}
    //public void accionesSinPermisos(){
    //}

}