package com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask;

// NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
//  <uses-permission android:name="android.permission.READ_CONTACTS" />
// lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission

import android.graphics.Bitmap;

import com.jyoc.jyocpruebacogerfotogaleria.Contacto;

import java.util.List;

public interface IGestionContactos {
    // -- CUIDADO, ESTOS METODOS SERAN LLAMADOS DESDE UN HILO EXTERNO
    // -- NO PUEDE ESCRIBIRSE EN LA UI DENTRO DE ELLOS DIRECTAMENTE
    // -- Si se desea hacer alguna accion en UI; se puede con un runOnUiThread:
    // -------    
    //runOnUiThread(new Runnable() {
    //    public void run() {
    //        ivPrueba.setImageBitmap(foto);
    //    }
    //});
    void trasleertodosloscontactos( List<Contacto> listaContactos);
    void trasleerUnContacto( Contacto c);
    void trasleertodoslosTelefonos( List<String> listaTelefonos);
    void trasleerFotoDeUnContacto( Bitmap foto);
    void trasleerCumpleañosDeUnContacto( String cumpleanos);

}
