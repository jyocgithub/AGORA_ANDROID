package com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask;

// NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
//  <uses-permission android:name="android.permission.READ_CONTACTS" />
// lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

public interface IGestionPermisos {

    void accionesConPermisos();
    
    void accionesSinPermisos();

}
