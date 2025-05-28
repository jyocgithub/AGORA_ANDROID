package com.example.ag_alarmas.alarma;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class AlarmService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AlarmService", "onStartCommand called");
        long alarmTime = intent.getLongExtra("alarm_time", System.currentTimeMillis()); // Valor por defecto si no se encuentra el extra
   
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE);

        // Configura la alarma para que suene en 5 segundos
        //long alarmTime = System.currentTimeMillis() + (5 * 1000);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // ... otros métodos del servicio ...
}