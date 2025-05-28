package com.jyoc.aux;

import android.content.Context;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AUX_Alarmas {


public void crearAlarma(int hora, int minutos) {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);

        Intent intent = new Intent(getApplicationContext(), MiAlarma.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }public void crearAlarma(int hora, int minutos) {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);

        Intent intent = new Intent(getApplicationContext(), MiAlarma.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }
}


class MiAlarma extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { //Alarma disparada!
        // añadir aqui las acciones que se han de realizar cuando salte la alarma  
    }

}