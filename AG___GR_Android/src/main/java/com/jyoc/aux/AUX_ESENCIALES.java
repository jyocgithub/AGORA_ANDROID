package com.jyoc.aux;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jyoc.aux.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;

public class AUX_ESENCIALES extends AppCompatActivity {
    private  AUX_ESENCIALESBinding vistas;
    

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // +---------------------------------------------+
        // |                                             |
        // |  VIEW BINDING                               |
        // |                                             |
        // +---------------------------------------------+
        // --------- esto en el build.gradle, capitulo android { }
        //viewBinding {
        //    enabled = true
        //}

        // ---------- esto como atributo global de la actividad
        //private ActivityMainBinding vistas;

        // setContentView(R.layout.activity_main); // ------- quitar esto y poner :
        vistas = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(vistas.getRoot());

        // +---------------------------------------------+
        // |                                             |
        // |  INTENT EXPLICITO                           |
        // |                                             |
        // +---------------------------------------------+
        //Intent miIntent = new Intent(MainActivity.this, SegundaActivity.class);
        //miIntent.putExtra("nombre", "Juan");
        //startActivity(miIntent);


        // +---------------------------------------------+
        // |                                             |
        // |  TOAST SIMPLE                               |
        // |                                             |
        // +---------------------------------------------+
        Toast.makeText(this, "Mensaje", Toast.LENGTH_LONG).show();

        // +---------------------------------------------+
        // |                                             |
        // |  SNACKBAR                                   |
        // |                                             |
        // +---------------------------------------------+
        
        Snackbar.make(vistas.tvCabecera, "Mensaje", Snackbar.LENGTH_LONG).show();

        Snackbar.make(vistas.tvCabecera, "Esto es Snackbar mas COMPLEJO !!!", Snackbar.LENGTH_INDEFINITE)
                // esto es opcional, y pone un color al texto con enlace que dispara la accion 
                .setActionTextColor(Color.CYAN)
                // Esto muestra un texto con enlace que ejecuta la accion de su OnClickListener()  
                .setAction("Nueva Acción", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // COLOCAR AQUI LA ACCION QUE SE DESEE  
                    }
                }).show();
    }


    /**
     * ***************************************************************************
     * ************            DATE Y STRING                        **************
     * ***************************************************************************
     */
    /**
     * hoy_en_DATE
     *
     * @return la fecha actual en formato DATE
     */
    public static Date hoy_en_DATE() {
        Calendar cc = Calendar.getInstance();
        Date hoyEnDate = cc.getTime();
        return hoyEnDate;
    }

    /**
     * de_DATE_a_STRING
     * <p>
     * convertir de un Date de java.util a STRING, con un formato
     *
     * @param fechaEnDate Objeto Date de la fecha a cambiar
     * @param formato     formato, como p.e. "dd/MM/yyyy"
     *
     * @return fecha en string en dicho formato, por ejemplo, "12/22/2016"
     */
    public static String de_DATE_a_STRING(Date fechaEnDate, String formato) {
        SimpleDateFormat miFormato = new SimpleDateFormat(formato);
        String fechaEnString = miFormato.format(fechaEnDate);
        return fechaEnString;
    }

    /**
     * de_STRING_a_DATE
     * <p>
     * Convierte un String en un util.Date
     *
     * @param fechaEnString
     *
     * @return
     */
    public static Date de_STRING_a_DATE(String fechaEnString) {
        SimpleDateFormat miFormato2 = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaenjava = null;
        try {
            fechaenjava = miFormato2.parse(fechaEnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaenjava;
    }


}
