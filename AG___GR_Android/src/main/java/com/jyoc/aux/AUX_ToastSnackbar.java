package com.jyoc.aux;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class AUX_ToastSnackbar {

    // +---------------------------------------------+
    // |                                             |
    // |      TOAST SIMPLE                           |
    // |                                             |
    // +---------------------------------------------+
    //   Toast.makeText( context , "TITULO" , "MENSAJE", Toast.LENGTH_SHORT).show();
    

    // +---------------------------------------------+
    // |                                             |
    // |      SNACKBAR SIMPLE                        |
    // |                                             |
    // +---------------------------------------------+
    //   Snackbar misnackbar = Snackbar.make(viewReference, "MENSAJE", Snackbar.LENGTH_SHORT);
    
    // +---------------------------------------------+
    // |                                             |
    // |      TOAST CON IMAGEN                       |
    // | Necesita su propio layout con el contenido  |
    // | que se desee, y un textview para el mensaje |
    // |                                             |
    // +---------------------------------------------+
// USO: toastPersonal ( this, "Se han cancelado las operaciones" , R.layout.toastlayout, R.id.constraintToast, R.id.tvToastBox)  
    public void toastPersonal(Activity context, String texto, int idLayout, int idrootLayout, int idTextBox){
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(idLayout, (ViewGroup)context.findViewById(idrootLayout));
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView caja = view.findViewById(idTextBox);
        caja.setText(texto);
        toast.show();
    }
    
    // +---------------------------------------------+
    // |                                             |
    // |      SNACKBAR CON BOTON   v2025             |
    // |                                             |
    // +---------------------------------------------+
      public static void snackConBoton(View view, String texto) {

        final Snackbar misnackbar = Snackbar.make(view, texto, Snackbar.LENGTH_INDEFINITE);

        // con esto se pone al snackbar un maximo de 5 lineas, aunque solo usará las que necesite
        // sin esto el máximo que puede mostrar son 2 lineas
        TextView textView = (TextView) misnackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);

        // esto es opcional, y pone color al texto con enlace que dispara la accion
        misnackbar.setActionTextColor(Color.MAGENTA);

        // esto es opcional, y pone color de fondo del snackbar
        misnackbar.getView().setBackgroundColor(Color.DKGRAY);

        // esto es opcional, y pone color al texto del snackbar
        textView.setTextColor(Color.CYAN);
        //textView.setTextColor(Color.parseColor("#00FFFF"));
        //textView.setTextColor(ContextCompat.getColor(this,R.color.colorSemitransparente));

        // Esto muestra un texto con enlace que ejecuta la accion de su OnClickListener()
        misnackbar.setAction("Cerrar", view1 -> {
            misnackbar.dismiss();  // esto para que normalmente se cierre el snack con lo que hagamos
        }).show();
    }

    
    // +---------------------------------------------+
    // |                                             |
    // |      DIALOG CON FRAGMENT                    |
    // |                                             |
    // +---------------------------------------------+
        // ver AG_FRAGMENTS_DIALOGFRAGMENT

}
