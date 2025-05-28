package com.jyoc.ag_fragmentsdialogfragment.avanzado;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

/*
 * Cuando uses la biblioteca de compatibilidad, asegúrate de importar la clase:
 * android.support.v4.app.DialogFragment
 * y no: android.app.DialogFragment.
 */


public class MisilesDialog extends DialogFragment {

    MisilesDialogListener mdListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¿Quieres lanzar un misil?");
        builder.setMessage("La Mancha. Zona Radiactiva")
                .setPositiveButton("Disparar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ¡Lanzamos el misil!
                        Toast.makeText(getActivity(), "¡Fueeego!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        /*
                         * Gracias a esa referencia a la actividad obtenida en el método onAttach(),
                         * podremos invocar a los métodos del interfaz MisilesDialogListener.
                         * Interfaz implementado por la actividad principal.
                         */
                        mdListener.onDialogPositiveClick(MisilesDialog.this);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // El usuario aborta el lanzamiento del misil
                        dialog.dismiss();
                        /*
                         * Gracias a esa referencia a la actividad obtenida en el método onAttach(),
                         * podremos invocar a los métodos del interfaz MisilesDialogListener.
                         * Interfaz implementado por la actividad principal.
                         */
                        mdListener.onDialogNegativeClick(MisilesDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /*
     * Cuando el diálogo se crea, el fragmento se une “Attach” a la actividad principal. En ese
     * momento el método onAttach() se ejecuta y podemos quedarnos con una referencia a
     * la actividad.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the MisilesDialogListener so we can send events to the host
            mdListener = (MisilesDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " se debe implementar MisilesDialogListener");
        }
    }
}
