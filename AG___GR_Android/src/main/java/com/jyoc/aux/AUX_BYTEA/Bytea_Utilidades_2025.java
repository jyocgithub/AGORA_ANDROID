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

   public static String de_MILISECONDS_a_EUROPEANSTRING(long miliseconds) {
        SimpleDateFormat miFormato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEnString = miFormato.format(new Date(miliseconds));
        return fechaEnString;
    }

    public static long de_EUROPEANSTRING_a_MILISECONDS(String fechaEnString, String formato) {
        SimpleDateFormat miFormato2 = new SimpleDateFormat(formato);
        Date fechaenjava = null;
        try {
            fechaenjava = miFormato2.parse(fechaEnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = fechaenjava.getTime();
        return millis;
    }

    public static long hoy_en_MILISECONDS() {
        return Calendar.getInstance().getTimeInMillis();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String localdateToString(LocalDate fechaEnlocaldate, String formato) {
        if (fechaEnlocaldate == null) {
            return null;
        }
        return fechaEnlocaldate.format(DateTimeFormatter.ofPattern(formato));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long localdateToLong(LocalDate fechaEnlocaldate) {
        if (fechaEnlocaldate == null) {
            return 0;
        }
        long milliseconds = fechaEnlocaldate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return milliseconds;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long localdateTimeToLong(LocalDateTime fechaEnlocaldate) {
        if (fechaEnlocaldate == null) {
            return 0;
        }

        long milliseconds = fechaEnlocaldate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return milliseconds;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate longToLcaldate(long millis) {
        LocalDate fecha = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return fecha;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime longToLocaldateTime(long millis) {
        LocalDateTime fecha = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return fecha;
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


    public static File salvarStringEnUnidadExternaDowload(Context context, String contenido, String nombredelficheroDestino) {
        File fileDestino = null;
        try {
            fileDestino = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), 
                nombredelficheroDestino
            );
            if (!fileDestino.exists())
                fileDestino.createNewFile();  // por si acaso android en el futuro ..... hace algo raro
            PrintWriter pw = new PrintWriter(fileDestino);
            pw.println(contenido);
            pw.close();
            return fileDestino;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileDestino;
    }

    public static File getFile_UnidadExternaPublicaDownload(Context context, String contenido,String nombredelficheroDestino) {
        File fileDestino = null;
        try {
            fileDestino = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), 
                nombredelficheroDestino
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


    public static Bitmap createLetterBitmap(int backgroundColor, String letter, int letterColor, int size) {
        // Crear un bitmap cuadrado con el tamaño especificado
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Radio para las esquinas redondeadas (1/8 del tamaño)
        float cornerRadius = size / 8f;

        // Configurar el paint para el fondo
        Paint backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundColor);

        // Dibujar el fondo redondeado
        //RectF rect = new RectF(0, 0, size, size);
        //canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint);


        float centerX = size / 2f;
        float centerY = size / 2f;
        float radius = size / 2f;
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
        
        // Configurar el paint para la letra
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(letterColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        // Calcular el tamaño de la letra (aproximadamente 1/2 del tamaño del bitmap)
        float textSize = size * 0.5f;
        textPaint.setTextSize(textSize);

        // Obtener las métricas del texto para centrarlo verticalmente
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        float textOffset = textHeight / 2 - fontMetrics.bottom;
        float centerY2 = size / 2f + textOffset;

        // Dibujar la letra centrada
        //canvas.drawText(String.valueOf(letter), size / 2f, centerY, textPaint);
        canvas.drawText(letter.charAt(0)+""+letter.charAt(1), size / 2f, centerY2, textPaint);

        return bitmap;
    }



    public static String arrayToCSV(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder csv = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            // Escapar comas y comillas dobles si es necesario
            String value = array[i].replace("\"", "\"\"");
            if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                value = "\"" + value + "\"";
            }
            csv.append(value);
            if (i < array.length - 1) {
                csv.append(",");
            }
        }
        return csv.toString();
    }

    public static String[] csvToArray(String csv) {
        if (csv == null || csv.isEmpty()) {
            return new String[0];
        }
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentValue = new StringBuilder();
        for (char c : csv.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        result.add(currentValue.toString());
        return result.toArray(new String[0]);
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

    public static void compartirString(Context context, String recipient, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            //context.startActivity(emailIntent);
            context.startActivity(Intent.createChooser(emailIntent, "Compartir actividades utilizando:"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Maneja el caso donde no hay aplicaciones de correo instaladas
            Toast.makeText(context, "No hay aplicaciones para compartir instaladas.", Toast.LENGTH_SHORT).show();
        }
    }


    public static void enviarStringToAppMail(Context context, String asunto, String mensaje, String destinatariosSeparadosPorComas) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Configuración básica
        intent.putExtra(Intent.EXTRA_EMAIL, destinatariosSeparadosPorComas.split(","));
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        context.startActivity(intent);
        //// -- EXTRA_HTML_TEXT se usa si el texto del correo tiene formato HTML
        //// i.putExtra(Intent.EXTRA_HTML_TEXT, "<bold>TEXTO DEL MENSAJE</bold>");
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
     * USA FILE PROVIDER
     * Abre una ventana para que el usuario comparta el fichero con las app del dispositivo
     * Recibe un objeto File del fichero que va a compartir
     *
     * @param file
     * @param actividad
     */
    public static void compartirFichero(File file, Activity actividad) {
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

    public static void snackConBoton(View view, String texto) {

        final Snackbar misnackbar = Snackbar.make(view, texto, Snackbar.LENGTH_INDEFINITE);

        // con esto se pone al snackbar un maximo de 5 lineas, aunque solo usará las que necesite
        // sin esto el máximo que puede mostrar son 2 lineas
        TextView textView = (TextView) misnackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);

        // esto es opcional, y pone color al texto con enlace que dispara la accion
        misnackbar.setActionTextColor(Color.MAGENTA);

        // esto es opcional, y pone color de fondo del snackbar
        misnackbar.getView().setBackgroundColor(Color.DKGRAY);

        // esto es opcional, y pone color al texto del snackbar
        textView.setTextColor(Color.CYAN);
        //textView.setTextColor(Color.parseColor("#00FFFF"));
        //textView.setTextColor(ContextCompat.getColor(this,R.color.colorSemitransparente));

        // Esto muestra un texto con enlace que ejecuta la accion de su OnClickListener()
        misnackbar.setAction("Cerrar", view1 -> {
            misnackbar.dismiss();  // esto para que normalmente se cierre el snack con lo que hagamos
        }).show();
    }


    // +------------------------------------------------+
    // |                                                |
    // |  REINICIAR APLICACION                          |
    // |                                                |
    // +------------------------------------------------+
    public static void reiniciarAplicacion(Context context, Class clasebase) {
        Intent intent = new Intent(context, clasebase);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

        // Finaliza la aplicación
        if (context instanceof Activity) {
            ((Activity) context).finishAffinity();
        }
        System.exit(0);
    }


    public static String indentarString(String texto, int cantidadDeEspacios) {
        StringBuilder resultado = new StringBuilder();
        String espacios = " ".repeat(cantidadDeEspacios); // Java 11+
        String[] lineas = texto.split("\n");

        for (String linea : lineas) {
            resultado.append(espacios).append(linea).append("\n");
        }

        return resultado.toString();
    }

    public static void mostrarDatePicker(Context context, final Long[] selectedDate, final TextView textview) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDate[0]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        selectedDate[0] = calendar.getTimeInMillis();
                        if (textview != null) {
                            textview.setText(Utilidades.localdateToString(Utilidades.longToLcaldate(selectedDate[0]), "dd/MM/yyyy"));
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }






























 // +------------------------------------------------+
    // |                                                |
    // |  alertDialogDeConsultaConTextoConScroll        |
    // |      y fondo negro con marco                   |
    // |                                                |
    // +------------------------------------------------+
    public static void alertDialogDeConsultaConTextoConScroll(Context context, String message, int accion) {
        // Crear el fondo con borde amarillo y esquinas redondeadas
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.BLACK);
        background.setStroke(5, Color.YELLOW);
        background.setCornerRadius(16);

        // Create main LinearLayout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        // Create ScrollView
        ScrollView scrollView = new ScrollView(context);

        // Set maximum height for the ScrollView
        int maxHeight = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                maxHeight
        ));

        // Create and setup TextView with white text (changed from black for dark background)
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextSize(16f);
        textView.setPadding(0, 0, 0, 16);
        textView.setTextIsSelectable(false);
        textView.setTextColor(Color.WHITE);  // Cambiado a blanco para el fondo negro

        // Add TextView to ScrollView
        scrollView.addView(textView);

        // Add ScrollView to main layout
        layout.addView(scrollView);

        // Create custom button layout
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonLayout.setPadding(16, 16, 16, 16);
        buttonLayout.setGravity(Gravity.CENTER);

        // Build AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        // Establecer el fondo después de crear el diálogo
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getWindow().setBackgroundDrawable(background);
        });

            // Create custom buttons
            Button mailButton = new Button(context);
            mailButton.setText("Mail");
            mailButton.setBackgroundColor(Color.parseColor("#2196F3"));
            mailButton.setTextColor(Color.WHITE);

            Button cancelButton = new Button(context);
            cancelButton.setText("Volver");
            cancelButton.setBackgroundColor(Color.parseColor("#4CAF50"));
            cancelButton.setTextColor(Color.WHITE);

            Button shareButton = new Button(context);
            shareButton.setText("Compartir");
            shareButton.setBackgroundColor(Color.parseColor("#9A22F3"));
            shareButton.setTextColor(Color.WHITE);

            // Set layout parameters for buttons with fixed width
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,                                    // Ancho 0 para usar weight
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.weight = 1;                     // Usar weight para distribuir el espacio
            buttonParams.setMargins(8, 0, 8, 0);

            // Add buttons to layout with proper spacing
            buttonLayout.addView(mailButton, buttonParams);
            buttonLayout.addView(shareButton, buttonParams);
            buttonLayout.addView(cancelButton, buttonParams);

            // Add button layout to main layout
            layout.addView(buttonLayout);

            // Set button click listeners
            mailButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    if (accion == MainActivity.INFORME_HOY) {
                        Utilidades.enviarMailDirectamente(context, "ACTIVIDADES", deportistaActual.informacionParaCompartirSoloHoy(), MainActivity.correo+","+deportistaActual.getCorreo());
                    }
                    if (accion == MainActivity.INFORME_SIEMPRE) {
                        Utilidades.enviarMailDirectamente(context, "ACTIVIDADES", deportistaActual.informacionParaCompartirTodasLasActividades(), MainActivity.correo+","+deportistaActual.getCorreo());
                    }
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    if (accion == MainActivity.INFORME_HOY) {
                        Utilidades.compartir(context, MainActivity.correo, "ACTIVIDADES", deportistaActual.informacionParaCompartirSoloHoy());
                    }
                    if (accion == MainActivity.INFORME_SIEMPRE) {
                        Utilidades.compartir(context, MainActivity.correo, "ACTIVIDADES", deportistaActual.informacionParaCompartirTodasLasActividades());
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

 

        // [Resto del código de los listeners igual...]

        dialog.show();
    }


}
