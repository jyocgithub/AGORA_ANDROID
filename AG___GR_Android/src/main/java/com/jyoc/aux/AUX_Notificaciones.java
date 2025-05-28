package com.jyoc.aux;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;




import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AUX_Notificaciones {




    // +---------------------------------------------+
    // |                                             |
    // |  LANZAR UNA NOTIFICACION                    |
    // |                                             |
    // +---------------------------------------------+
   /**
     *
     * @param context PRIMER PARAMETRO, el contexto donde se lanza la notificacoin
     * @param args    ORDEN DEL RESTO DE PARAMETROS (TODOS STRING) NO OBLIGATORIO NINGUNO
     *                - texto del la notificacion
     *                - titulo de la notificacion
     *                - texto largo de la notificacion
     *                - channel_id
     *                - channel_name
     *                - notification_id
     */
    private static void lanzarUnaNotification(Context context, Bitmap icono, String... args) {
        // cogemos de los parametros los valores pasados o cogemos unos por defecto
        final String TEXTO_NOTIFICACION = (args.length > 0) ? args[0] : "Aviso de " + context.getString(R.string.app_name);
        final String TITULO_NOTIFICACION = (args.length > 1) ? args[1] : "Notificación";
        final String TEXTO_LARGO=  (args.length > 2) ? args[2] : "";
        final String CHANNEL_ID = (args.length > 3) ? args[3] : context.getString(R.string.app_name);
        final String CHANNEL_NAME = (args.length > 4) ? args[4] : context.getString(R.string.app_name);
        final int NOTIFICATION_ID = (args.length > 5) ? Integer.parseInt(args[5]) : 1313;

        //Bitmap miBitMap  = BitmapFactory.decodeResource(getResources(),R.drawable.casa3);

        // 1.- Crear un Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(context.getString(R.string.app_name));
            notificationChannel.setSound(null, null);
            notificationChannel.setLightColor(Color.GREEN);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        // 2.- Crear el contenido de la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icono)
                .setContentTitle(TITULO_NOTIFICACION)
                .setContentText(TEXTO_NOTIFICACION)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(TEXTO_LARGO))
                // En vez de un texto largo, se puede añadir como expansible una imagen grande. Usar en este caso este otro style:
                //.setStyle(new NotificationCompat.BigPictureStyle()
                //        .bigPicture(icono)
                //        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // ver tabla en pagina previa

        // 3.- Lanzar notificacion
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}