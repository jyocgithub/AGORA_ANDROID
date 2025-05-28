package com.jyoc.elnotanemo23.utilidades;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyoc.elnotanemo23.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/*
    // EJEMPLO DE USO
    // ==========================================================================================
        // CREAR DIALOGO
        ByteaDFragmentAcercade dialog = ByteaDFragmentAcercade.newInstance(
            "El Nota",
            "Versión 5\n(c) 2025 Bytea Sw - Ilñaki Martín"
        );
        // LANZAR DIALOGO
        dialog.show(activityContext.getSupportFragmentManager(), "FormularioAcercade");
        // Si se lanza desde un fragment usar esto en vez de la linea anterior
        // dialog.show(getParentFragmentManager(), "Formulario1");
    // ==========================================================================================
*/
public class ByteaDFragmentAcercade extends DialogFragment {

    public static final boolean RESPUESTA_FALSE = false;
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private DialogListener listener;

    public interface DialogListener {
        void onDialogResult(boolean result);
    }

    public static ByteaDFragmentAcercade newInstance(String title, String message) {
        ByteaDFragmentAcercade fragment = new ByteaDFragmentAcercade();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Ajustar el ancho del diálogo al 85% del ancho de la pantalla
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments() != null ? getArguments().getString(ARG_TITLE) : "MENSAJE";
        String message = getArguments() != null ? getArguments().getString(ARG_MESSAGE) : "";

        // Crear un fondo con borde amarillo y esquinas redondeadas
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.BLACK);
        background.setStroke(5, Color.YELLOW);
        background.setCornerRadius(24); // Aumentado para mejor apariencia

        Context context = requireContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(
                dpToPx(24), // padding horizontal aumentado
                dpToPx(24), // padding top
                dpToPx(24), // padding horizontal aumentado
                dpToPx(24)  // padding bottom
        );

        // Configurar título
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(20f);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(Color.WHITE);
        titleView.setTypeface(null, Typeface.BOLD);

        // Configurar mensaje
        TextView messageView = new TextView(context);
        messageView.setText(message);
        messageView.setTextSize(16f);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextColor(Color.WHITE);

        // Configurar imagen
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.bytea_logo_texto);
        imageView.setAdjustViewBounds(true); // Mantener proporción de aspecto
        imageView.setMaxHeight(dpToPx(220)); // Altura máxima de la imagen

        // Configurar botón
        Button button_aceptar = new Button(context);
        button_aceptar.setText("ACEPTAR");
        button_aceptar.setBackgroundColor(Color.GREEN);
        button_aceptar.setTextColor(Color.BLACK);

        // Parámetros de layout para los elementos
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.setMargins(0, 0, 0, dpToPx(16));
        titleView.setLayoutParams(titleParams);

        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        messageParams.setMargins(0, 0, 0, dpToPx(24));
        messageView.setLayoutParams(messageParams);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageParams.setMargins(0, 0, 0, dpToPx(24));
        imageView.setLayoutParams(imageParams);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(48) // Altura fija para el botón
        );
        button_aceptar.setLayoutParams(buttonParams);

        // Añadir vistas al layout
        layout.addView(titleView);
        layout.addView(imageView);
        layout.addView(messageView);
        layout.addView(button_aceptar);

        // Configurar listeners
        imageView.setOnLongClickListener(v -> {
            JYMUtils.snackConBoton(button_aceptar, "\"He aquí al asno salvaje del desierto precipitándose hacia su trabajo\" (Jeremías 14:6)");
            return true;
        });

        button_aceptar.setOnClickListener(v -> {
            if (listener != null)
                listener.onDialogResult(RESPUESTA_FALSE);
            dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(background);
        }
        return dialog;
    }

    private int dpToPx(int dp) {
        return (int) (dp * requireContext().getResources().getDisplayMetrics().density);
    }
}

