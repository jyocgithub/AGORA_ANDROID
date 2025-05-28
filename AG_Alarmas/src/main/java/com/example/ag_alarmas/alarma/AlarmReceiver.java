package com.example.ag_alarmas.alarma;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.ag_alarmas.R;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "ALARM_CHANNEL";
    
    
    @Override
    public void onReceive(Context context, Intent intent) {



        Intent alarmIntent = new Intent(context, AlarmaSonandoActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Necesario si se inicia desde un BroadcastReceiver

        // Configura un PendingIntent para abrir la actividad desde la notificación
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Crea un canal de notificación (necesario en Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarma",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Canal de alarmas");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Construye la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // Ícono de la notificación
                .setContentTitle("¡Alarma!")
                .setContentText("Haz clic para detener la alarma.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent) // Al pulsar, abre la actividad
                .setAutoCancel(true);

        // Muestra la notificación
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, builder.build());
        }

        // Opcional: también puedes iniciar directamente la actividad de alarma (sin esperar clic en la notificación)
        context.startActivity(alarmIntent);
     
        //context.startActivity(alarmIntent);
        //
        //
        //
        //// Aquí se ejecuta el código cuando la alarma suena
        //// Por ejemplo, mostrar una notificación
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_id")
        //        .setSmallIcon(R.drawable.ic_launcher_background)
        //        .setContentTitle("¡Alarma!")
        //        .setContentText("¡Despiértate!")
        //        .setPriority(NotificationCompat.PRIORITY_HIGH);
        //
        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        //if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        //    // TODO: Consider calling
        //    //    ActivityCompat#requestPermissions
        //    // here to request the missing permissions, and then overriding
        //    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //    //                                          int[] grantResults)
        //    // to handle the case where the user grants the permission. See the documentation
        //    // for ActivityCompat#requestPermissions for more details.
        //    return;
        //}
        //notificationManager.notify(1, builder.build());
    }
}
