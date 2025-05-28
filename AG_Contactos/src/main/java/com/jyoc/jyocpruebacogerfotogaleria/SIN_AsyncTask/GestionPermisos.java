package com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.Map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GestionPermisos  {


    // +---------------------------------------------+
    // |                                             |
    // |                                             |
    // |         PEDIR UN PERMISO                    |
    // |                                             |
    // |                                             |
    // +---------------------------------------------+
    public static void pedirUnPermiso(IGestionPermisos actividadOrigen, String permisoSolicitado ) {
        AppCompatActivity context = (AppCompatActivity) actividadOrigen;

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
        //String permisoSolicitado = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), permisoSolicitado) == PackageManager.PERMISSION_GRANTED) {
            actividadOrigen.accionesConPermisos();
        } else {
            ActivityResultLauncher<String> miARL_pedirUnPermiso =
                    context.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                                @Override
                                public void onActivityResult(Boolean isGranted) {
                                    if (isGranted) {
                                        Toast.makeText(context.getApplicationContext(), "PERMISO CONCEDIDO", Toast.LENGTH_LONG).show();
                                        actividadOrigen.accionesConPermisos();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "PERMISO DENEGADO !!!!!!!!!!!", Toast.LENGTH_LONG).show();
                                        actividadOrigen.accionesSinPermisos();
                                    }
                                }
                            }
                    );
            // lanzar la peticion (en un listener, si se quiere)
            miARL_pedirUnPermiso.launch(permisoSolicitado);
        }
    }

    // +---------------------------------------------+
    // |                                             |
    // |                                             |
    // |       PEDIR VARIOS PERMISOS                 |
    // |                                             |
    // |                                             |
    // +---------------------------------------------+

    /**
     * pedirVariosPermisos: método estático para pedir varios permisos
     * @param actividadOrigen Objeto de una clase que implemente IGestionPermisos
     * @param ARR_PERMISOS array de string con los permisos, como este:
     *   String[] ARR_PERMISOS = {
     *           Manifest.permission.WRITE_EXTERNAL_STORAGE,
     *           Manifest.permission.WRITE_CONTACTS,
     *           Manifest.permission.WRITE_EXTERNAL_STORAGE,
     *           Manifest.permission.READ_EXTERNAL_STORAGE
     *   };
     */
    public static void pedirVariosPermisos(IGestionPermisos actividadOrigen,final String[] ARR_PERMISOS) {
        AppCompatActivity context = (AppCompatActivity) actividadOrigen;
        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

        ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
                context.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> isGranted) {
                        if (isGranted.containsValue(false)) {
                            actividadOrigen.accionesSinPermisos();
                        } else {
                            // Nos han concedido los permisos..... seguimos adelante
                            actividadOrigen.accionesConPermisos();
                        }
                    }
                });
        if (!tieneYaEstosPermisos(ARR_PERMISOS, context)) {
            miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
        } else {
            actividadOrigen.accionesConPermisos();
        }
    }

    private static  boolean tieneYaEstosPermisos(String[] permisos, Context context) {
        if (permisos != null) {
            for (String cadaPermiso : permisos) {
                if (ActivityCompat.checkSelfPermission(context, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}