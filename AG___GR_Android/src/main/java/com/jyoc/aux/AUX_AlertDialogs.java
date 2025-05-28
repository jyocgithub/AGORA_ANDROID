package com.jyoc.aux;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.jyoc.R;

import java.util.ArrayList;


public class AUX_AlertDialogs {

    
    // +---------------------------------------------+
    // |                                             |
    // |      DIALOG CON FRAGMENT                    |
    // |      VER AG_DIALOGFRAGMENT PARA 
    // |      VERSIONES INTELIGENTES DE JY
    // +---------------------------------------------+
      

      
    // +---------------------------------------------+
    // |                                             |
    // |      ALERT DIALOG CONFIRMAR - UN BOTON      |
    // |                                             |
    // +---------------------------------------------+

    static public void alertDialogConfirmarUnBoton(final Context ct, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON ACEPTAR
                        // ........... 
                        // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // +---------------------------------------------+
    // |                                             |
    // |   ALERT DIALOG CONFIRMAR - DOS BOTONES      |
    // |                                             |
    // +---------------------------------------------+

    static public void alertDialogConfirmarDosBotontes(final Context ct, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON SI
                        // ........... 
                        // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                        dialog.cancel();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON NO
                        // ........... 
                        // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
 
    // +---------------------------------------------+
    // |                                             |
    // |    ALERT DIALOG CONFIRMAR - PEDIR UN DATO   |
    // |                                             |
    // +---------------------------------------------+

    static public void alertDialogParaPedirUnDato(final Context ct, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        final EditText input = new EditText(ct);
        // input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD); // si es como un password, usar este 
        // input.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL); // para texto normal
        builder.setView(input);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON ACEPTAR
                // ........... 
                // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON CANCELAR
                // ........... 
                // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                dialog.cancel();
            }
        });
        builder.show();
    }


    // +---------------------------------------------+
    // |                                             |
    // |      ALERT DIALOG                           |
    // |      ELEGIR DE UNA LISTA SIMPLE             |
    // |                                             |
    // +---------------------------------------------+

    static public void alertDialogParaElegirDeUnaListaSimple(final Context ct, String mensaje) {
        final CharSequence[] items = {"Arriba", "Abajo", "Derecha", "Izquierda"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("Elige una direccion");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String elegido = items[item] + "";
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // +---------------------------------------------+
    // |                                             |
    // |      ALERT DIALOG                           |
    // |      CON DISEÑO SIMPLE PROPIO SIN LAYOUT    |
    // |                                             |
    // +---------------------------------------------+
    static public void alertDialogConDiseñoPropioSINLayout(final Context ct, String mensaje) {
        AlertDialog.Builder alertacrear = new AlertDialog.Builder(ct);

        alertacrear.setTitle("Elegir el Producto");
        alertacrear.setMessage("Escoge un producto:");

        Spinner spin = new Spinner(ct);
        ArrayList<String> lista = new ArrayList<>();
        lista.add("BALON");
        lista.add("MESA");
        lista.add("CARTEL");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, lista);
        spin.setAdapter(adaptador);

        alertacrear.setView(spin);
        alertacrear.setPositiveButton("Elegir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // código de botón ELEGIR
            }
        });

        alertacrear.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // código de botón CANCELAR
            }
        });

        alertacrear.show();
    }


    // +---------------------------------------------+
    // |                                             |
    // |      ALERT DIALOG                           |
    // |      CON DISEÑO SIMPLE PROPIO Y CON LAYOUT  |
    // |                                             |
    // +---------------------------------------------+
    public void alertDialogConDiseñoPropioCONLayoutPROPIO(final Context ct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        LayoutInflater inflater = LayoutInflater.from(ct);
        View dialogo = inflater.inflate(R.layout.activity_login_registro, null);
        builder.setView(dialogo);
        builder.setView(dialogo);
        final EditText diUsuario = dialogo.findViewById(R.id.etUsuario_registro);
        final EditText diPassword = dialogo.findViewById(R.id.etPassword_registro);
        final EditText diMail = dialogo.findViewById(R.id.etMail_Registro);
        final Button diACeptar = dialogo.findViewById(R.id.btAceptar_registro);
        final Button diCancelar = dialogo.findViewById(R.id.btCancelar_registro);
        final AlertDialog alertDialog = builder.show();
        diACeptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diUsuario.getText().toString().trim().isEmpty() ||
                        diPassword.getText().toString().trim().isEmpty() ||
                        diMail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ct, "Debe rellenar todos los datos", Toast.LENGTH_SHORT).show();
                    return;
                }
                String usuarioActual = diUsuario.getText().toString().trim();
                String passwordActual = diPassword.getText().toString().trim();
                String mailActual = diMail.getText().toString().trim();

                //pedirUsuariosAFirestore(SOLICITUD_PARA_REGISTRO);
                alertDialog.dismiss();
            }
        });
        diCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    // +---------------------------------------------+
    // |                                             |
    // |      ALERT DIALOG                           |
    // |      PARA MOSTRAR UN TEXTO CON SCROLL       |
    // |      Y TRES BOTONES ALINEADOS ABAJO         |
    // |                                             |
    // +---------------------------------------------+
    public static void alertDialogDeConsultaConTextoConScroll(Context context, String message, int accion) {
        // Create main LinearLayout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        // Create ScrollView
        ScrollView scrollView = new ScrollView(context);

        // Set maximum height for the ScrollView
        int maxHeight = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7); // 70% of screen height
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                maxHeight // Limit the height of the ScrollView
        ));

        // Create and setup TextView with darker text
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextSize(16f);
        textView.setPadding(0, 0, 0, 16);
        textView.setTextIsSelectable(false);
        textView.setTextColor(Color.BLACK);

        // Add TextView to ScrollView
        scrollView.addView(textView);

        // Add ScrollView to main layout
        layout.addView(scrollView);

        // Create custom button layout
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonLayout.setPadding(16, 16, 16, 16);
        buttonLayout.setGravity(Gravity.CENTER);

        // Create custom buttons
        Button mailButton = new Button(context);
        mailButton.setText("Mail");
        mailButton.setBackgroundColor(Color.parseColor("#2196F3"));
        mailButton.setTextColor(Color.WHITE);

        Button cancelButton = new Button(context);
        cancelButton.setText("Cancelar");
        cancelButton.setBackgroundColor(Color.parseColor("#F44336"));
        cancelButton.setTextColor(Color.WHITE);

        Button shareButton = new Button(context);
        shareButton.setText("Compartir");
        shareButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        shareButton.setTextColor(Color.WHITE);

        // Set layout parameters for buttons with fixed width
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0,                                    // Ancho 0 para usar weight
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.weight = 1;                     // Usar weight para distribuir el espacio
        buttonParams.setMargins(8, 0, 8, 0);

        // Add buttons to layout with proper spacing
        buttonLayout.addView(mailButton, buttonParams);
        buttonLayout.addView(shareButton, buttonParams);
        buttonLayout.addView(cancelButton, buttonParams);

        // Add button layout to main layout
        layout.addView(buttonLayout);

        // Build and show AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        // Set button click listeners
        mailButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (accion == MainActivity.INFORME_HOY) {
                    Utilidades.enviarMailDirectamente(context, "ACTIVIDADES", deportistaActual.informacionParaCompartirSoloHoy(), MainActivity.correo);
                }
                if (accion == MainActivity.INFORME_SIEMPRE) {
                    Utilidades.enviarMailDirectamente(context, "ACTIVIDADES", deportistaActual.informacionParaCompartirTodasLasActividades(), MainActivity.correo);
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (accion == MainActivity.INFORME_HOY) {
                    Utilidades.compartir(context, MainActivity.correo, "ACTIVIDADES", deportistaActual.informacionParaCompartirSoloHoy());
                }
                if (accion == MainActivity.INFORME_SIEMPRE) {
                    Utilidades.compartir(context, MainActivity.correo, "ACTIVIDADES", deportistaActual.informacionParaCompartirTodasLasActividades());
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
