package com.jyoc.ag_fragmentsdialogfragment.avanzado;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jyoc.ag_fragmentsdialogfragment.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivityAvanzado extends AppCompatActivity implements MisilesDialogListener {

    MisilesDialog md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        md = new MisilesDialog();
        /*
         * El segundo argumento, "misiles", es un nombre de etiqueta único que el sistema usa para
         * guardar y restaurar el estado del fragmento cuando es necesario.
         */
        md.show(getSupportFragmentManager(), "misiles");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Toast.makeText(this, "Se ha recibido el misil, ¡gracias!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }
}