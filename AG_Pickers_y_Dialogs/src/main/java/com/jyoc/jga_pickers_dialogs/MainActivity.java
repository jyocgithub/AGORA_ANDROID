package com.jyoc.jga_pickers_dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        datepickerComoDialog();

        Toast.makeText(this,"SON PICKERS ASINCRONOS; ASI QUE ESTO SE MUESTRA ANTES DE LA RESPUESTA DEL USUARIO",Toast.LENGTH_LONG).show();


    }


    public void timepickerComoDialog() {

        int horainicialdelpicker = 13;
        int minutoinicialdelpicker = 15;
        boolean formato24horas = true;
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                int horaelegida = selectedHour;
                int minutoelegido = selectedMinute;
            }
        }, horainicialdelpicker, minutoinicialdelpicker, formato24horas);//Yes 24 hour time
        mTimePicker.setTitle("Indique la hora deseada:");
        mTimePicker.show();

    }

    public void datepickerComoDialog() {

        final Calendar hoy = Calendar.getInstance();
         Calendar nuevafecha = Calendar.getInstance();

        int diainicialdelpicker = hoy.get(Calendar.DAY_OF_MONTH);  // o poner cuaquier otra
        int mesinicialdelpicker = hoy.get(Calendar.MONTH);
        int anoinicialdelpicker = hoy.get(Calendar.YEAR);

        final DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                nuevafecha.set(year, monthOfYear, dayOfMonth);
            }

        }, anoinicialdelpicker, mesinicialdelpicker, diainicialdelpicker);
        mDatePicker.setTitle("Indique la fecha deseada:");
        mDatePicker.show();

        Toast.makeText(this,nuevafecha.toString(),Toast.LENGTH_LONG).show();

    }
}