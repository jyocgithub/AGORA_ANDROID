package com.jyoc.elnotanemo23.utilidades;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/*
    // EJEMPLO DE USO
    // ==========================================================================================
 
        // CREAR DIALOGO
        ByteaDFragmentAviso dialog = ByteaDFragmentAviso.newInstance(
            "AVISO",
            "Se han borrado todos los datos"
        );
        // LANZAR DIALOGO
        dialog.show(activityContext.getSupportFragmentManager(), "Formulario1");
        // Si se lanza desde un fragment usar esto en vez de la linea anterior
        // dialog.show(getParentFragmentManager(), "Formulario1");

*/

public class ByteaDFragmentAviso extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private DialogListener listener;

    public interface DialogListener {
        void onDialogResult(boolean result);
    }

    // Factory method (newInstance) con un Bundle para pasar datos
    public static ByteaDFragmentAviso newInstance(String title, String message) {
        ByteaDFragmentAviso fragment = new ByteaDFragmentAviso();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args); // Guardamos los datos en un Bundle
        return fragment;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Recuperamos los argumentos del Bundle
        String title = getArguments() != null ? getArguments().getString(ARG_TITLE) : "MENSAJE";
        String message = getArguments() != null ? getArguments().getString(ARG_MESSAGE) : "";

        // Crear un fondo con borde amarillo y esquinas redondeadas
        int cornerRadius = 16;
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.BLACK);
        background.setStroke(5, Color.YELLOW);
        background.setCornerRadius(cornerRadius);

        // Construimos el AlertDialog
        Context context = requireContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(20f);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(Color.WHITE);

        TextView messageView = new TextView(context);
        messageView.setText(message);
        messageView.setTextSize(16f);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextColor(Color.WHITE);

        Button button_ok = new Button(context);
        button_ok.setText("ACEPTAR");
        button_ok.setBackgroundColor(Color.parseColor("#4CAF50")); // Verde
        button_ok.setTextColor(Color.WHITE);

        // Margen genérico para los elementos
        int margin = 16; // Puedes ajustar este valor

        // Margen para el título
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.setMargins(0, margin, 0, margin); // Sólo margen arriba y abajo
        titleView.setLayoutParams(titleParams);

        // Margen para el mensaje
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        messageParams.setMargins(0, 0, 0, margin); // Margen sólo abajo
        messageView.setLayoutParams(messageParams);

        // Margen para los botones
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 50, 0, 0); // Sólo margen arriba
        button_ok.setLayoutParams(buttonParams);
        //button_ko.setLayoutParams(buttonParams);
        
        // Añadimos las vistas al layout
        layout.addView(titleView);
        layout.addView(messageView);
        layout.addView(button_ok);
        //layout.addView(button_ko);

        // Configuramos los listeners de los botones
        button_ok.setOnClickListener(v -> {
            dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(true);

        // Aquí aplicamos el fondo personalizado
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(background);  // Aplicamos el fondo con bordes
        }
        return dialog;
    }
}
