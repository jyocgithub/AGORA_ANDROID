package com.android.tema_citasagenda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    
    public void onMeterCita(View v) {
        Calendar fechacitainicio = Calendar.getInstance();
        Calendar fechacitafin = Calendar.getInstance();
        //int mes = 4;
        //int  anio = 2021;
        //int dia = 27;
        //fechacitainicio.set(anio,mes,dia);
        //fechacitainicio.set(anio,mes,dia, 12, 30);
        //fechacitafin.set(anio,mes,dia, 13, 45);

        if (Build.VERSION.SDK_INT >= 14) {
            Intent intent0 = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechacitainicio.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechacitafin.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "CLASE")
                    .putExtra(CalendarContract.Events.ALL_DAY, false)
                    .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Android con sensores...")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Calle Pez 12")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, "javayotrascosas@gmail.com");
            startActivity(intent0);
        }

        else {
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
            intent.putExtra("title", "A Test Event from android app");
            startActivity(intent);
        }
    }
}

