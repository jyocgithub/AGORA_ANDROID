package com.jyoc.permisos_comoAUX;

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

public class AUX_Permisos {


    // +---------------------------------------------+
    // |                                             |
    // |                                             |
    // |         PEDIR UN PERMISO                    |
    // |                                             |
    // |                                             |
    // +---------------------------------------------+
    public static void pedirUnPermiso(IAUX_Permisos actividadOrigen, String permisoSolicitado) {
        AppCompatActivity context = (AppCompatActivity) actividadOrigen;

        // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
        //  <uses-permission android:name="android.permission.READ_CONTACTS" />
        // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

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


    
    
    public static void pedirVariosPermisos(IAUX_Permisos actividadOrigen, String[] arrayDePermisosSolicitados) {
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
        if (!tieneYaEstosPermisos(arrayDePermisosSolicitados, context)) {
            miARL_pedirVariosPermisos.launch(arrayDePermisosSolicitados);
        } else {
            actividadOrigen.accionesConPermisos();
        }
    }

    private static boolean tieneYaEstosPermisos(String[] permisos, Context context) {
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