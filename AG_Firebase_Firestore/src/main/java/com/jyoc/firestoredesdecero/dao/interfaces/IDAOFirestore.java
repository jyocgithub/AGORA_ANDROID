package com.jyoc.firestoredesdecero.dao.interfaces;

import android.graphics.Bitmap;

import java.util.List;

public interface IDAOFirestore<T> {

    // Todos los atributos de una interfaz son por definicion, public y static  
    int ACCION_LEER_TODO = 560;
    int ACCION_LEER_POR_IGUALQUE = 561;
    int ACCION_LEER_POR_MAYORQUE = 562;
    int ACCION_LEER_POR_MENORQUE = 563;
    int ACCION_LEER_POR_IN = 564;
    int ACCION_BORRAR = 565;
    int ACCION_BORRAR_TODO = 566;

    void trasLeerFirebase(int tipoAccion, List<T> pColeccionLeida);

    void trasBorrarFirebase(int tipoAccion, int pNumeroElementosBorrados);

    void trasGuardarFirebase(T pNuevoElemento);

}
