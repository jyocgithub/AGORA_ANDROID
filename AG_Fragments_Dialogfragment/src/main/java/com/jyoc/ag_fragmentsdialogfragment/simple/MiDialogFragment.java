package com.jyoc.ag_fragmentsdialogfragment.simple;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.widget.EditText;

/**
 */
public class MiDialogFragment extends DialogFragment {
    MainActivity objetoMainActivity;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Construimos referencia a la actividad contenedora 
        // tambien se puede hacer con 
         objetoMainActivity = (MainActivity) getActivity();
        
        // Construimos el Dialog que retorna el método
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        EditText etSolicitud = new EditText(objetoMainActivity);
        etSolicitud.setInputType(InputType.TYPE_CLASS_NUMBER );
        //etSolicitud.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        builder.setView(etSolicitud);

        builder.setTitle("PRUEBA DE DIALOG FRAGMENT")
        .setMessage("ACEPTA O CANCELA")
                //// .setCancelable(false)
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                objetoMainActivity.onRespuestaDialogo("PULSO ACEPTAR");
                                dialog.cancel();
                            }

                        })
                .setNegativeButton("cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                objetoMainActivity.onRespuestaDialogo("PULSO CANCELAR");
                                // Cancelar > cerrar el cuadro de diálogo
                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }
    
    // Otro modo de coger una referenciar la actividad superior
    // onAttach se invoca cuando el fragmento se añade a la actividad 
    // El contexto que recibe es una instancia de la propia actividad !!!
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity objetoMainActivity = (MainActivity) context;
    }
    
    
}


