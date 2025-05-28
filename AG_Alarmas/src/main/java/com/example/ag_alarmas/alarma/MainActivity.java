package com.example.ag_alarmas.alarma;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ag_alarmas.R;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Button btnDate = findViewById(R.id.btnDate);
        TextView tvDate = findViewById(R.id.tvDate);
        Button btnTime = findViewById(R.id.btnTime);
        TextView tvTime = findViewById(R.id.tvTime);
        Calendar calendar = Calendar.getInstance();
        btnDate.setOnClickListener(v -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                    {
                        year = selectedYear;
                        month = selectedMonth+1;
                        day= selectedDay;
                        tvDate.setText(day + "/" + (month ) + "/" + year);
                    }, year, month, day);
            datePickerDialog.show();
        });
        btnTime.setOnClickListener(v -> {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, selectedHour, selectedMinute) -> {
                        hour= selectedHour;
                        minute = selectedMinute;
                        tvTime.setText(hour + ":" + minute);
                    }, hour, minute, true);
            timePickerDialog.show();
        });


        ((Button) findViewById(R.id.btPoner)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(getApplicationContext(), AlarmService.class);
                //
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year); // Minutos
                calendar.set(Calendar.MONTH, month-1); // Hora
                calendar.set(Calendar.DAY_OF_MONTH, day); // Hora
                calendar.set(Calendar.HOUR_OF_DAY, hour); // Hora
                calendar.set(Calendar.MINUTE, minute); // Minutos
                calendar.set(Calendar.SECOND, 0); // Segundos
                //long timeInMillis = calendar.getTimeInMillis();
                //intent.putExtra("alarm_time", timeInMillis);

                
                String t = day + "/" + (month + 1) + "/" + year + "  " + hour + ":" + minute;
                ((TextView) findViewById(R.id.tvResultado)).setText(t);
                //
                //startService(intent);


                // Establece el tiempo para la alarma
                //Calendar calendar = Calendar.getInstance();
                //calendar.add(Calendar.MINUTE, 1); // Configura la alarma para que se dispare en 1 minuto

                // Intent para activar el BroadcastReceiver
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                // Programar la alarma
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                    Toast.makeText(MainActivity.this, "Alarma programada para "+t, Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }
}