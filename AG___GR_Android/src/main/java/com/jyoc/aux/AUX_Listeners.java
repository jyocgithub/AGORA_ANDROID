package com.jyoc.aux;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jyoc.gut.R;

import androidx.appcompat.app.AppCompatActivity;


public class AUX_Listeners extends AppCompatActivity implements View.OnClickListener {

    // +---------------------------------------------+
    // |                                             |
    // |   COMO CREAR LISTENERS DE VARIAS FORMAS     |
    // |                                             |
    // +---------------------------------------------+
    Button bt1, bt2, bt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gut_listeners);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);


        // +---------------------------------------------+
        // |                                             |
        // |  CASO 1 : APLICAR SETONCLICKLISTENER EN     |
        // |           EL OBJETO QUE SE QUIERA ACTIVAR   |
        // |                                             |
        // +---------------------------------------------+
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AUX_Listeners.this, "PULSADO EN LISTENER DE CLASE BT1", Toast.LENGTH_LONG).show();
            }
        });


        // +-----------------------------------------------------------------------+
        // |                                                                       |
        // |  CASO 2 : APLICAR SETONCLICKLISTENER EN EL OBJETO HABIENDO:           |
        // |            a) IMPLEMENTADO ONCLIKLISTENER EN LA CLASE                 |
        // |            b ) RECOGIENDO EL EVENTO EN UN METODO ONCLICK MAS ADELANTE |
        // |                                                                       |
        // +-----------------------------------------------------------------------+
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if (b.getId() == R.id.bt1) {
            Toast.makeText(this, "PULSADO EN LISTENER DE CLASE BT1", Toast.LENGTH_LONG).show();
            // pulsado el boton 1.....
        }
        if (b.getId() == R.id.bt2) {
            Toast.makeText(this, "PULSADO EN LISTENER DE CLASE BT2", Toast.LENGTH_LONG).show();
            // pulsado el boton 2.....
        }
    }


    // +---------------------------------------------+
    // |                                             |
    // |  CASO 3 : ASOCIAR UN METODO DE LA ACTIVIDAD |
    // |           CON EL EL ATRIBUTO ONCLIK DEL     |
    // |           XML-LAYOUT                        |
    // |           ESTO PERMITE APLICAR EL MISMO     |
    // |           METODO EN VARIOS OBJETOS          |
    // |                                             |
    // +---------------------------------------------+>

    public void botonPulsadoValidoParaVariosBotones(View v) {
        Button b = (Button) v;
        if (b.getId() == R.id.bt1) {
            // pulsado el boton 1.....
        }
        if (b.getId() == R.id.bt2) {
            // pulsado el boton 2.....
        }
    }

    // +---------------------------------------------+
    // |                                             |
    // |  CASO 4 : ASOCIAR UN METODO DE LA ACTIVIDAD |
    // |           A MULTIPLES OBJETOS USANDO SUS ID |
    // |           EN UN ARRAY QUE SE RECORRE        |
    // |                                             |
    // +---------------------------------------------+
    int[] arrayids = {R.id.bt1, R.id.bt2};

    public void activarElMismoListenersEnMuchosBotones() {
        for (int i = 0; i < arrayids.length; i++) {
            Button b = findViewById(arrayids[i]);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    if (b.getId() == arrayids[0]) {
                        // pulsado el boton 1.....
                    }
                    if (b.getId() == arrayids[1]) {
                        // pulsado el boton 2.....
                    }
                }
            });
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void listener_ActionMove() {

        // LISTENERS CON ACTION_MOVE ******************************************
        // si no se añade asistencia a personas ciegas o con dificultades visuales, 
        // nos da un warning muy molesto, que se puede eliminar añadiendo
        // @SuppressLint("ClickableViewAccessibility")  ANTES del METODO donde se usa
        EditText et1 = findViewById(R.id.et1);
        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("JYOC", "Action ejecutada: DOWN");
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("JYOC", "Action ejecutada: MOVE");
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d("JYOC", "Action ejecutada: UP");
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("JYOC", "Action ejecutada: CANCEL");
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        Log.d("JYOC", "Movimiento fuera de los limites del elemento");
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
    @SuppressLint("ClickableViewAccessibility")
    public void listener_Key() {

        // LISTENERS CON KEY  ******************************************
        // si no se añade asistencia a personas ciegas o con dificultades visuales, 
        // nos da un warning muy molesto, que se puede eliminar añadiendo
        // @SuppressLint("ClickableViewAccessibility")  ANTES del METODO donde se usa
        EditText et2 = findViewById(R.id.et2);
        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                int action = keyEvent.getAction();
                switch (action) {
                    case KeyEvent.ACTION_DOWN:
                        Log.d("JYOC", "Action ejecutada: KEY DOWN");
                        return true;
                    case KeyEvent.ACTION_UP:
                        Log.d("JYOC", "Action ejecutada: KEY UP");
                        return true;
                }

                return false;

            }
        });

    }


    public void listener_TextChanched() {
        EditText et2 = findViewById(R.id.et2);

        et2.addTextChangedListener(new TextWatcher() {
            // Este método se dispara con cada cambio en el texto del view
            // indicando que se ha modificado el String s,
            // se han reemplazado count caractéres
            // comenzando en start y han reemplazado before número caracteres
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Se han reemplazado de '" + s + "'" + count
                        + " caracteres a partir de " + start +
                        " que antes ocupaban " + before + " posiciones");
            }

            //Este método te avisa de que en la cadena s,
            // los count caracteres empezando en start,
            // están a punto de ser reemplazos por nuevo texto con longitud after
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("Se van a reemplazar de '" + s + "'" + count +
                        " caracteres a partir de " + start +
                        " por texto con longitud " + after);
            }

            //Este método te avisa de que,en algún
            //punto de la cadena s, el texto ha cambiado.
            public void afterTextChanged(Editable s) {
                System.out.println("Tu texto tiene " + s.length() + " caracteres");
            }
        });
    }
    

}
