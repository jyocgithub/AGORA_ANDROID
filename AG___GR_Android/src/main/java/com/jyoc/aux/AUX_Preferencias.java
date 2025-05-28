package com.jyoc.aux;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

public class AUX_Preferencias {

    // ********************************************************************************
    // ********************************************************************************
    // *                           CASO   1                                           *
    // *      USANDO SHARED PREFERENCES CON UNA COLECCION PROPIA DE PREFERENCIAS      *
    // *      SE PUEDEN CREAR MUCHOS "PAQUETES" DE SHARED PREFERENCES PROPIOS         *
    // ********************************************************************************
    // ********************************************************************************

    // +------------------------------------------------+
    // |                                                |
    // |  LEER PREFERENCIAS DE COLECCION PROPIA         |
    // |                                                |
    // +------------------------------------------------+
    public static void leerPreferencias(AppCompatActivity context) {
        SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        // Si no existe el valor, se devuelve el segundo parametro       
        String correo = prefs.getString("mail", "por_defecto @gmail.com");
        int numero = prefs.getInt("nombreDelValorIntAlmacenado", 10);
    }

    // +------------------------------------------------+
    // |                                                |
    // |  GUARDAR PREFERENCIAS DE COLECCION PROPIA      |
    // |                                                |
    // +------------------------------------------------+
    public static void guardarPreferencias(AppCompatActivity context) {
        SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        // GUARDAR o AÑADIR NUEVOS valores a preferencias        
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mail", "pruebas@gmail.com");
        editor.putInt("edad", 33);
        editor.apply();
    }
    
    // +------------------------------------------------+
    // |                                                |
    // |  BORRAR PREFERENCIAS DE COLECCION PROPIA       |
    // |                                                |
    // +------------------------------------------------+
    public static void borrarPreferencias(AppCompatActivity context) {
        SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // BORRAR preferencias                                                          
        // Asi eliminamos TODAS las preferencias del grupo prefs: 
        editor.clear();
        editor.apply();
        // y así eliminamos SOLO la preferencia titulo del grupo prefs: 
        editor.remove("titulo");
        editor.apply();
    }

    // ********************************************************************************************
    // ********************************************************************************************
    // *                           CASO   2                                                       *
    // *      USANDO SHARED PREFERENCES CON UN NOMBRE POR DEFECTO APLICADO POR ANDROID            *
    // * SOLO SE PUEDE USAR UN "PAQUETE" DE SHARED PREFERENCES POR DEFECTO EN TODA LA APLICACION  *
    // * ES EL PAQUETE QUE SE USA SI EXISTE UAN ACTIVIDAD DE PREFERENCIAS                         *
    // ********************************************************************************************
    // ********************************************************************************************

    // +--------------------------------------------------+
    // |                                                  |
    // |  LEER PREFERENCIAS POR DEFECTO DE LA APLICACION  |
    // |                                                  |
    // +--------------------------------------------------+
    public void leerPreferenciasPorDefecto(AppCompatActivity context) {
        SharedPreferences preferencias;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // RECORDAR AGREGAR DEPENDENCIA 
            //     implementation "androidx.preference:preference:1.1.0"
            preferencias = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferencias = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
        String nombre = preferencias.getString("nombreDelValorAlmacenado", "valorPorDefectoSiNoExiste");
        int numero = preferencias.getInt("nombreDelValorIntAlmacenado", 10);
    }

    // +-----------------------------------------------------+
    // |                                                     |
    // |  GUARDAR PREFERENCIAS POR DEFECTO DE LA APLICACION  |
    // |                                                     |
    // +-----------------------------------------------------+
    public void guardarPreferenciasPorDefecto(AppCompatActivity context) {
        SharedPreferences preferencias;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // RECORDAR AGREGAR DEPENDENCIA 
            //     implementation "androidx.preference:preference:1.1.0"
            preferencias = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferencias = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombreDelValorAlmacenado", "Valor");
        editor.putInt("nombreDelValorIntAlmacenado", 14);
        editor.commit();
    }


    // +------------------------------------------------+
    // |                                                |
    // |  BORRAR PREFERENCIAS DE COLECCION PROPIA       |
    // |                                                |
    // +------------------------------------------------+
    public static void borrarPreferenciasPorDefecto(AppCompatActivity context) {
        SharedPreferences preferencias;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // RECORDAR AGREGAR DEPENDENCIA 
            //     implementation "androidx.preference:preference:1.1.0"
            preferencias = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferencias = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor editor = preferencias.edit();
        // BORRAR preferencias                                                          
        // Asi eliminamos TODAS las preferencias del grupo prefs: 
        editor.clear();
        editor.apply();
        // y así eliminamos SOLO la preferencia titulo del grupo prefs: 
        editor.remove("titulo");
        editor.apply();
    }
}