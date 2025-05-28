package com.jyoc.aux;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import static android.content.Context.MODE_PRIVATE;

public class AUX_UTILIDADES_2024 {



    /**
     * ***************************************************************************
     * ************            PREFERENCIAS                         **************
     * ***************************************************************************
     */
    //:::::::::::::::::::::::::::::::  Atributos
    public static SharedPreferences preferenciasPrivadas, preferenciasPublicas;
    //:::::::::::::::::::::::::::::::  Metodos

    /**
     * leerPreferencias
     * <p>
     * Patron para leer preferencias, tanto públicas como privadas
     *
     * @param context
     */
    public static void leerPreferencias(Context context) {
        // Las privadas
        preferenciasPublicas = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferenciasPrivadas = context.getSharedPreferences("MisPreferenciasPrivadas", MODE_PRIVATE);
        // int numero = preferenciasPrivadas.getInt("base", 10);
        // String nombre = preferenciasPrivadas.getString("nombre", "nadapordefecto");
    }

    /**
     * guardarPreferencias
     * <p>
     * Patron para guardar preferencias, tanto públicas como privadas
     */
    public static void guardarPreferencias() {
        SharedPreferences.Editor editor = preferenciasPrivadas.edit();
        // editor.putInt("numero", 2233);
        // editor.putString("nombre", "Juan");
        editor.commit();
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

    /**
     * ***************************************************************************
     * ************            ALMACENAMIENTO EN DISPOSITIVO        **************
     * ***************************************************************************
     */

    /**
     * salvarBitMapEnDirectorioInternoApp
     * <p>
     * Guarda un Bitmap en un fichero, en el directorio de imagenes de la aplicacion (Internal Storage)
     * Devuelve un File que referencia directamente al fichero creado
     *
     * @param context
     * @param bitmap
     * @param nombredelficheroDestino
     *
     * @return Devuelve un File que referencia directamente al fichero creado
     */
    public static File salvarBitMapEnUnidadInternaApp(Context context, Bitmap bitmap, String nombredelficheroDestino) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File carpeta = wrapper.getDir("Images", MODE_PRIVATE);
        File fileDestino = new File(carpeta, nombredelficheroDestino);
        try {
            OutputStream stream = new FileOutputStream(fileDestino);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileDestino;
    }

    /**
     * salvarBitMapEnDirectorioLocalApp
     * <p>
     * Guarda un Bitmap en un fichero, en el directorio de imagenes de la aplicacion (Internal Storage)
     * Devuelve un File que referencia directamente al fichero creado
     *
     * @param context
     * @param contenido
     * @param nombredelficheroDestino
     *
     * @return Devuelve un File que referencia directamente al fichero creado
     */
    public static File salvarStringEnUnidadInterna(Context context, String contenido, String nombredelficheroDestino) {
        ContextWrapper wrapper = new ContextWrapper(context);
        // AL indicar "Images" almacena en /data/data/(nuestraappid)/app_Images ... aunque se puede
        // poner cualquier cosa, "Almacen" por ejemplo, y lo crea en /data/data/(nuestraappid)/app_Almacen
        File carpeta = wrapper.getDir("Images", MODE_PRIVATE);
        File fileDestino = new File(carpeta, nombredelficheroDestino);
        try {
            if (!fileDestino.exists())
                fileDestino.createNewFile();  // por si acaso android en el futuro ..... hace algo raro
            PrintWriter pw = new PrintWriter(fileDestino);
            pw.println(contenido);
            pw.close();
            return fileDestino;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileDestino;
    }


    /**
     * obtenerUriDesdeUnFile
     * <p>
     * Obtiene un objeto URI desde un objeto FILE
     *
     * @param file
     *
     * @return
     */
    public static Uri obtenerUriDesdeUnFile(File file) {
        Uri uriDelDFichero = Uri.fromFile(new File(file.getAbsolutePath()));
        return uriDelDFichero;
    }

    


    /**
     * ponerImagenConFormatoRedondoEnImageView
     * <p>
     * Pone en un ImageView una imagen, pero el ImageVIew queda con formato circular
     *
     * @param context
     * @param bitmap
     * @param imageview
     */
    public static void ponerImagenConFormatoRedondoEnImageView(Context context, Bitmap bitmap, ImageView imageview) {
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);
        imageview.setImageDrawable(circularBitmapDrawable);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }



    /**
     * ***************************************************************************
     * ************            ENVIANDO A OTRAS APPS                **************
     * ***************************************************************************
     */


    /**
     * enviarSMS
     * <p>
     * Envia un SMS aun numero determinado
     *
     * @param actividad
     * @param texto
     * @param numerodestino
     */
    public static void enviarSMS(Activity actividad, String texto, String numerodestino) {
        Uri uri = Uri.parse("smsto:" + numerodestino);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", texto);
        //i.setPackage("com.whatsapp");
        actividad.startActivity(i);
    }

    /**
     * enviarWhatsapp
     * <p>
     * Abre whatsapp con un texto preparado y espera a que el usuario elija destinatario
     * <p>
     * Si se desea una accion personalizada si no existe Whatsapp instalado en el dispositivo,
     * añadir las lineas comentadas y cambiar la accion en el catch
     *
     * @param actividad
     * @param mensaje
     */
    public static void enviarWhatsapp(Activity actividad, String mensaje) {
        //--- Si se desea una accion personalizada si no existe Whatsapp instalado en el dispositivo,
        //--- añadir las lineas comentadas y cambiar la accion en el catch
        // PackageManager pm = actividad.getPackageManager();
        //  try {
        //PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp"); // Si no se especifica la app aqui, Android muetra el "application picker" o selector de aplicaciones.
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        actividad.startActivity(Intent.createChooser(intent, "Compartiendo con whatsapp..."));
        //} catch (PackageManager.NameNotFoundException e) {
        //    Toast.makeText(actividad, "WhatsApp no esta instalado", Toast.LENGTH_SHORT).show();
        //}
    }

    /**
     * enviarMail
     * <p>
     * Version que no pregunta al usuario por la app que desea usar sino que usa la app por defecto del dispositivo
     *
     * @param actividad
     * @param asunto
     * @param mensaje
     * @param destinatariosSeparadosPorComas
     */
    public static void enviarMail(Activity actividad, String asunto, String mensaje, String destinatariosSeparadosPorComas) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + destinatariosSeparadosPorComas));
        // -- EXTRA_SUBJECT Es el asunto del correo y EXTRA_TEXT el cuerpo del correo
        i.putExtra(Intent.EXTRA_SUBJECT, asunto);
        i.putExtra(Intent.EXTRA_TEXT, mensaje);
        // -- EXTRA_HTML_TEXT se usa si el texto del correo tiene formato HTML
        // i.putExtra(Intent.EXTRA_HTML_TEXT, "<bold>TEXTO DEL MENSAJE</bold>");
        // -- En este caso el segundo parametro del createChooser es un mensaje que se muestra
        // -- por pantalla cuando se efectua el envio
        actividad.startActivity(Intent.createChooser(i, "Enviando mensaje"));
    }


    /**
     * tomarScreenShot
     *
     * Toma un ScreenShot de la pantalla actual y lo devuelve como un Bitmap
     * Para guadarlo en un fichero de disco, usar salvarBitmapEnEnDirectorioExternoApp() Ejemplo:
     *   Bitmap bitmap = JYOCUtilsv4.tomarScreenShot(mainactivity);
     *   File file = JYOCUtilsv4.salvarBitmapEnEnDirectorioExternoApp(Environment.DIRECTORY_PICTURES,bitmap,"ScreenShots","pantallazo.png");
     *
     * @param actividad
     */
    public static Bitmap tomarScreenShot(Activity actividad) {
        View rootView = actividad.getWindow().getDecorView().findViewById(android.R.id.content);
        View screenView = rootView.getRootView();
        Bitmap bitmap = Bitmap.createBitmap(screenView.getWidth(), screenView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = screenView.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        screenView.draw(canvas);
        return bitmap;
    }

    /**
     * Compartir Fichero
     *
     * Abre una ventana para que el usuario comparta el fichero con las app del dispositivo
     * Recibe un objeto File del fichero que va a compartir
     *
     * @param file
     * @param actividad
     */
    public static void CompartirFichero(File file, Activity actividad) {
        Context con = actividad.getApplicationContext();
        String str = actividad.getApplicationContext().getPackageName() + ".fileprovider";
        Uri uri = FileProvider.getUriForFile(con, str, file);
        //Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("*/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            actividad.startActivity(Intent.createChooser(intent, "Compartir...."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(actividad.getApplicationContext(), "No hay aplicacion instalada para esta accion", Toast.LENGTH_SHORT).show();
        }
    }
}
