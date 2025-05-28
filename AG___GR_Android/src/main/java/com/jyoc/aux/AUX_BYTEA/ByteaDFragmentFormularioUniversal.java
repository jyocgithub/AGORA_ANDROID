package com.jyoc.elnotanemo23.utilidades;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jyoc.elnotanemo23.adaptadores.ByteaSpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 *  ByteaDFragmentFormularioUniversal
 *
 *  Ofrece un alerta con un formulario estandar de tantos campos como se desee, incluidos SPINNERS
 *  los valores de los spinners se envian en un string CSV, 
 *  con el añadido de un prefijo @@ en el valor seleccionado, si no, se selecciona el 0
 *  Este String se puede construir a partide de unarray con el metodo creaStringCSVConSeleccion() (mas abajo)
 *  Los arrays de tipos y de enabled pueden ser null 
 */

/*
// === Ejemplo de uso ======================================================= 
// ========================================================================== 

    public void lanzarFormulario() {

        // SI HAY UN SPINNER.....    
        String[] datosDelSpinnerTipoMaquina = new String[7];
        datosDelSpinnerTipoMaquina[0] = "-> ACTIVIDAD MULTIPLE";
        datosDelSpinnerTipoMaquina[1] = "-> De Piernas";
        datosDelSpinnerTipoMaquina[2] = "-> De Empujar";
        datosDelSpinnerTipoMaquina[3] = "-> De Tirar";
        datosDelSpinnerTipoMaquina[4] = "-> De Crossfit";
        datosDelSpinnerTipoMaquina[5] = "-> De Boxeo";
        datosDelSpinnerTipoMaquina[6] = "-> Otras funciones";
        String stringSpinnerCSV = ByteaDFragmentFormularioUniversal.creaStringCSVConSeleccion(datosDelSpinnerTipoMaquina, 1);

        // DECIDIR QUE CAMPOS SON EDITABLES
        boolean[] enabled = new boolean[3];
        Arrays.fill(enabled, true);

        // DATOS BASICOS
        String[] campos = {"Nombre", "Tipo"};
        String[] valores = {"", stringSpinnerCSV};
        String[] tipos = {ByteaDFragmentFormularioUniversal.TIPO_STRING, ByteaDFragmentFormularioUniversal.TIPO_SPINNER};

        // CREAR DIALOGO
        ByteaDFragmentFormularioUniversal dialog = ByteaDFragmentFormularioUniversal.newInstance(
                "DATOS DE LA MAQUINA",
                "Introduce los datos de la maquina:",
                campos,
                valores,
                tipos,
                enabled
        );

        // LISTENERS
        dialog.setDialogListener(result -> {
            if (result == null) {
                // si han cancelado el dialogo formulario
            } else {
                // si han aceptado el dialogo formulario
                //onDialogoConfirmado(result);
            }
        });
        // LANZAR DIALOGO
        dialog.show(getSupportFragmentManager(), "Formulario1");
        // Si se lanza desde un fragment usar esto en vez de la linea anterior
        // dialog.show(getParentFragmentManager(), "Formulario1");
    }

    public void onDialogoConfirmado(String[] result) {
        // acciones con el array resul con el formulario
    }


*/

public class ByteaDFragmentFormularioUniversal extends DialogFragment {

    public static final String TIPO_STRING = "1";
    public static final String TIPO_SPINNER = "0";
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_VALORES = "valores";
    private static final String ARG_LABELS = "labels";
    private static final String ARG_TIPOS = "tipos";
    private static final String ARG_ENABLED = "enabled";
    private DialogListener listener;
    private int heightDialog;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private static int numeroCampos;

    public interface DialogListener {
        void onDialogResult(String[] arrayresult);
    }

    // Factory method (newInstance) con un Bundle para pasar datos
    public static ByteaDFragmentFormularioUniversal newInstance(String title, String message, String[] labels, String[] valores, String[] tipos, boolean[] enabled) {
        ByteaDFragmentFormularioUniversal fragment = new ByteaDFragmentFormularioUniversal();

        numeroCampos = labels.length; // Tamaño deseado del array

        if (tipos == null) {
            tipos = new String[numeroCampos];
            Arrays.fill(tipos, TIPO_STRING);
        }
        if (enabled == null) {
            enabled = new boolean[numeroCampos];
            Arrays.fill(enabled, true);
        }

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putStringArray(ARG_LABELS, labels);
        args.putStringArray(ARG_VALORES, valores);
        args.putStringArray(ARG_TIPOS, tipos);
        args.putBooleanArray(ARG_ENABLED, enabled);
        fragment.setArguments(args); // Guardamos los datos en un Bundle
        return fragment;
    }

    // Factory method (newInstance) con un Bundle para pasar datos
    public static ByteaDFragmentFormularioUniversal newInstance(String title, String message, String[] labels, String[] valores) {
        ByteaDFragmentFormularioUniversal fragment = new ByteaDFragmentFormularioUniversal();

        numeroCampos = labels.length; // Tamaño deseado del array

        String[] tipos = new String[numeroCampos];
        Arrays.fill(tipos, TIPO_STRING);
        boolean[] enabled = new boolean[numeroCampos];
        Arrays.fill(enabled, true);

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putStringArray(ARG_LABELS, labels);
        args.putStringArray(ARG_VALORES, valores);
        args.putStringArray(ARG_TIPOS, tipos);
        args.putBooleanArray(ARG_ENABLED, enabled);
        fragment.setArguments(args); // Guardamos los datos en un Bundle
        return fragment;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Dialog dialog = getDialog();
        if (dialog != null) {
            // COLOCAMOS EL ALTO DEL DIALOGO AL MINIMO POSIBLE O SI SE SALE, AL MAXIMO POSIBLE
            DisplayMetrics displayMetrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int maxWidth = (int) (displayMetrics.widthPixels * 0.9);
            int maxHeight = (int) (displayMetrics.heightPixels * 0.9);
            dialog.getWindow().getDecorView().measure(0, 0);
            heightDialog = dialog.getWindow().getDecorView().getMeasuredHeight();
            Window window = dialog.getWindow();
            if (window != null) {
                if (heightDialog > maxHeight)
                    window.setLayout(maxWidth, maxHeight);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Recuperamos los argumentos del Bundle
        String title = getArguments() != null ? getArguments().getString(ARG_TITLE) : "MENSAJE";
        String message = getArguments() != null ? getArguments().getString(ARG_MESSAGE) : "";
        String[] labels = getArguments() != null ? getArguments().getStringArray(ARG_LABELS) : new String[1];
        String[] valores = getArguments() != null ? getArguments().getStringArray(ARG_VALORES) : new String[1];
        String[] tipos = getArguments() != null ? getArguments().getStringArray(ARG_TIPOS) : new String[1];
        boolean[] enabled = getArguments() != null ? getArguments().getBooleanArray(ARG_ENABLED) : new boolean[1];


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

        // titulo
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(20f);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(Color.WHITE);

        // mensaje
        TextView messageView = new TextView(context);
        messageView.setText(message);
        messageView.setTextSize(16f);
        messageView.setGravity(Gravity.CENTER);
        messageView.setBackgroundColor(Color.BLACK);
        messageView.setTextColor(Color.parseColor("#91ACEF"));

        Button button_ok = new Button(context);
        button_ok.setText("ACEPTAR");
        button_ok.setBackgroundColor(Color.parseColor("#4CAF50")); // Verde
        button_ok.setTextColor(Color.WHITE);

        Button button_ko = new Button(context);
        button_ko.setText("CANCELAR");
        button_ko.setBackgroundColor(Color.parseColor("#2196F3")); // Azul
        button_ko.setBackgroundColor(Color.RED);
        button_ko.setTextColor(Color.WHITE);

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
        buttonParams.setMargins(100, 30, 100, 0); // Sólo margen arriba
        button_ok.setLayoutParams(buttonParams);
        button_ko.setLayoutParams(buttonParams);


        // campos del formulario
        LinearLayout.LayoutParams fieldsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        fieldsParams.setMargins(0, margin, 0, margin); // margen arriba y abajo

        TextView[] arraylabels = new TextView[labels.length];
        for (int i = 0; i < labels.length; i++) {
            arraylabels[i] = new TextView(context);
            arraylabels[i].setText(labels[i]+ " :");
            arraylabels[i].setTextSize(16f);
            arraylabels[i].setGravity(Gravity.LEFT);
            arraylabels[i].setTextColor(Color.WHITE);
            arraylabels[i].setLayoutParams(fieldsParams);
        }

        View[] arrayviews = new View[labels.length];
        for (int i = 0; i < arrayviews.length; i++) {
            // el tiop es un string o no se ha definido
            if (tipos == null || tipos[i] == TIPO_STRING) {
                EditText e = new EditText(context);
                e.setText(valores[i]);
                e.setEnabled(enabled[i]);
                e.setTextSize(16f);
                e.setGravity(Gravity.CENTER);
                e.setEnabled(enabled[i]);
                if (enabled[i]) {
                    e.setTextColor(Color.WHITE);
                    e.setBackgroundColor(Color.parseColor("#434D66"));
                } else {
                    e.setTextColor(Color.LTGRAY);
                    e.setBackgroundColor(Color.parseColor("#1B1E27"));
                }
                e.setLayoutParams(fieldsParams);
                arrayviews[i] = e;
                // el tipo es un spinner
            } else if (tipos[i].equals(TIPO_SPINNER)) {

                Spinner e = new Spinner(context);
                e.setLayoutParams(fieldsParams);

                ArrayList<String> datosSpinner = new ArrayList<>();
                int posicion = 0;
                int indice = 0;
                for (String t : valores[i].split(",")) {
                    if (t.startsWith("@@")) {
                        t = t.substring(2);
                        posicion = indice;
                    }
                    datosSpinner.add(t);
                    indice++;
                }
                datosSpinner.add(0,"(TODAS)");
             
                //ArrayAdapter adaptador = getAdapterSpinner(context, datosSpinner);
                //adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //e.setAdapter(adaptador);
                // Crear y configurar el adaptador SIN IMAGENES
                ByteaSpinnerAdapter adapter = new ByteaSpinnerAdapter(
                        context,
                        datosSpinner.toArray(new String[0]),
                        null);
                e.setAdapter(adapter);
             
                e.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                spinnerParams.setMargins(50, margin, 20, margin); // margen arriba y abajo
                e.setLayoutParams(spinnerParams);
                //e.setBackgroundColor(Color.parseColor("#434D66"));
                e.setSelection(posicion);
                arrayviews[i] = e;

            } else {
                Toast.makeText(context, "Informacion de tipos del formulario no congruente.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        // Crear el ScrollView
        ScrollView scrollView = new ScrollView(context);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Crear un contenedor para el contenido
        LinearLayout contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(32, 32, 32, 32);

        // Añadimos las vistas al layout
        contentLayout.addView(titleView);
        contentLayout.addView(messageView);
        for (int i = 0; i < arrayviews.length; i++) {
            contentLayout.addView(arraylabels[i]);
            contentLayout.addView(arrayviews[i]);
        }
        contentLayout.setLayoutParams(scrollParams);

        contentLayout.addView(button_ok);
        contentLayout.addView(button_ko);

        // Añadir el contentLayout al ScrollView
        scrollView.addView(contentLayout);

        // Usar el ScrollView como vista principal
        builder = new AlertDialog.Builder(context);
        builder.setView(scrollView);
        builder.setCancelable(true);

        // Configuramos los listeners de los botones
        String[] finalTipos = tipos;
        button_ok.setOnClickListener(v -> {
            if (listener != null) {
                for (int i = 0; i < arrayviews.length; i++) {
                    if (finalTipos == null || finalTipos[i] == TIPO_STRING) {
                        valores[i] = ((EditText) arrayviews[i]).getText().toString();
                    } else if (finalTipos[i].equals(TIPO_SPINNER)) {
                        valores[i] = ((Spinner) arrayviews[i]).getSelectedItemPosition() + "";
                    }
                }
                listener.onDialogResult(valores);
            }
            dismiss();
        });

        button_ko.setOnClickListener(v -> {
            if (listener != null)
                listener.onDialogResult(null); // KO devuelve null
            dismiss();
        });

        // Aquí aplicamos el fondo personalizado
        dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(background);  // Aplicamos el fondo con bordes
        }
        return dialog;
    }

    public ArrayAdapter getAdapterSpinner(Context ct, ArrayList lista) {
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ct, android.R.layout.simple_spinner_item, lista) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setBackgroundColor(Color.parseColor("#941751"));
                text.setTextColor(Color.WHITE);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setBackgroundColor(Color.parseColor("#941751"));
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        return adaptador;
    }

    /**
     * Crea un string CSV con los valores del array
     * Añade @@ al valor que pueda ser elefido como seleccionado
     *
     * @param valores
     * @param posicionSeleccionada
     * @return
     */
    public static String creaStringCSVConSeleccion(String[] valores, int posicionSeleccionada) {
        String res = "";
        for (int i = 0; i < valores.length; i++) {
            if (i == posicionSeleccionada) {
                res += "@@";
            }
            res += valores[i] + ",";
        }
        return res.substring(0, res.length() - 1);
    }
}
