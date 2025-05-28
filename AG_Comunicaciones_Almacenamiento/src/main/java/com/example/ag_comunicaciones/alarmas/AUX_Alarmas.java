package com.example.ag_comunicaciones.alarmas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.AlarmClock;

public class AUX_Alarmas {

    /* poner una alarma
     *
     * @param file   Actividad a la que corresponde
     */
    public static void setAlarmaAbriendoApp(Context contex, String message, int hour, int minutes) {
        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
            //if (intent.resolveActivity(contex.getPackageManager()) != null) {
            contex.startActivity(intent);
            //}
        } catch (Exception e) {
            // puede dar un error si no hay permisos
            e.printStackTrace();
        }
    }

    private void setAlarm(Context context, int type) {
         AlarmManager alarmMgr;
         PendingIntent alarmIntent;
    
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000, alarmIntent);
        
    }
}
