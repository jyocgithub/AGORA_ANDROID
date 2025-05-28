package com.jyoc.aux;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class AUX_Pickers {

    // +---------------------------------------------+
    // |                                             |
    // |      DATE PICKER                            |
    // |                                             |
    // +---------------------------------------------+

    static public Date pedirFecha(final Context ct, String mensaje) {
        final Calendar hoy = Calendar.getInstance();
        Calendar nuevafecha = Calendar.getInstance();
        int diainicialdelpicker = hoy.get(Calendar.DAY_OF_MONTH); // o poner cuaquier otra int 
        int mesinicialdelpicker = hoy.get(Calendar.MONTH);
        int anoinicialdelpicker = hoy.get(Calendar.YEAR);
        final DatePickerDialog mDatePicker = new DatePickerDialog(ct, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                nuevafecha.set(year, monthOfYear, dayOfMonth);
            }
        }, anoinicialdelpicker, mesinicialdelpicker, diainicialdelpicker);
        mDatePicker.setTitle(mensaje);
        mDatePicker.show();
        return nuevafecha.getTime();
    }
    
}