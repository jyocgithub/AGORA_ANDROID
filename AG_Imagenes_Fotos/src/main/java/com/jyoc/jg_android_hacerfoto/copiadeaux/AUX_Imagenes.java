package com.jyoc.jg_android_hacerfoto.copiadeaux;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class AUX_Imagenes {


    // +---------------------------------------------+
    // |                                             |
    // |    CAMBIAR TAMAÑO DE UNA IMAGEN             |
    // |                                             |
    // +---------------------------------------------+
    public static Bitmap cambiarTamano(Bitmap bitmap, int ancho, int alto) {
        return Bitmap.createScaledBitmap(bitmap, ancho, alto, false);
    }


    /**
     * **************************************************
     * bajarFotoDeInternet
     * **************************************************
     * 
     * Baja un foto de internet a partir de su URL
     * Recibe el ImageView donde se colocará la foto
     * Si no se desea este comportamiento, de ha de cambiar el 
     * @param urlDeLaFoto
     * @param imageView
     */
    static public void bajarFotoDeInternet(String urlDeLaFoto, ImageView imageView) {
        // la urlDeLaFoto es un string como por ejemplo de este tipo ;
        // String urlDeLaFoto = "https://bit.ly/2uUlAzw";
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap imagenbajada = null;
                try {
                    String urlelegida = params[0];
                    InputStream input = new java.net.URL(urlelegida).openStream();
                    imagenbajada = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagenbajada;
            }

            @Override
            protected void onPostExecute(Bitmap imagenbajada) {
                // solo decimos "terminada" si doInBackground devolvio true
                // podemos poner la foto en un imageview, por ejemplo
                // Si no, hay que considerar que esta actividad es asincrona....
                imageView.setImageBitmap(imagenbajada);
            }
        }.execute(urlDeLaFoto);
    }

    // +------------------------------------------------+
    // |                                                |
    // |    CREAR IMAGEN CON FORMA REDONDA              |
    // |                                                |
    // +------------------------------------------------+
    public static void crearImagenConFormaRedonda(Context context, Bitmap bitmap) {
        RoundedBitmapDrawable circularBitmap = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        circularBitmap.setCircular(true);
        Bitmap b = circularBitmap.getBitmap();
    }

    // +------------------------------------------------+
    // |                                                |
    // |    CREAR IMAGEN CON ESQUINAS REDONDAS          |
    // |                                                |
    // +------------------------------------------------+
    public static void crearImagenConEsquinasRedondas(Context context, Bitmap bitmap, Float factor) {
        RoundedBitmapDrawable circularBitmap = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        circularBitmap.setCornerRadius(Math.max(circularBitmap.getMinimumWidth(), circularBitmap.getMinimumHeight()) / factor);
        Bitmap b = circularBitmap.getBitmap();
    }

    /**
     * **************************************************
     *  bitmapFromDrawableConId
     * **************************************************
     *
     * Obtiene un bitmap del nombre de un drawable
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param idDelDrwawable id de la imagen en drawables (R.drawable.mago , por ejemplo)
     * @return bitmap obtenido 
     */
    public static Bitmap bitmapFromDrawableConId(Context context, int idDelDrwawable) {
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), idDelDrwawable);
        return imagenEnBmp;
    }


    /**
     * **************************************************
     *  bitmapFromDrawableConNombre
     * **************************************************
     * 
     * Obtiene un bitmap del nombre de un drawable
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombrerecurso nombre (sin extension) de la imagen en drawables
     * @return bitmap obtenido 
     */
    public static Bitmap bitmapFromDrawableConNombre(Context context, String nombrerecurso) {
        // CUIDADO, EL NOMBRE DEL RECUSRO (FICHERO IMAGEN) ES SIN EXTENSION 
        int resourceId = idRecursoFromNombreRecurso(context, "drawable", nombrerecurso);
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return imagenEnBmp;
    }

    /**
     * **************************************************
     *  idRecursoFromNombreRecurso
     * **************************************************
     *
     * Obtiene el id de un recurso (drawable, menu, layout, etc) a partir de su nombre
     * Ejemplo de uso: 
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param tiporecurso  tipo de recurso que se busca: "drawable", "menu", etc
     * @param nombrerecurso nombre del recurso buscado
     * @return el int con el id del recurso, o 0 si no se encuentra
     */
    public static int idRecursoFromNombreRecurso(Context context, String tiporecurso, String nombrerecurso) {
        // CUIDADO, EL NOMBRE DEL RECURSO (FICHERO IMAGEN) ES SIN EXTENSION 
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(nombrerecurso, tiporecurso, context.getPackageName());
        return resourceId;
    }

    /**
     * **************************************************
     *  bitmapFromAsset
     * **************************************************
     *
     * Obtiene un bitmap de un fichero imagen de la carpeta Assets
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombreFicheroImagen nombre del fichero imagen que se intenta obtener
     * @return un bitmap con el fichero obtenido, o null si hay error o no existe el fichero
     */
    public static Bitmap bitmapFromAsset(final Context context, String nombreFicheroImagen) {
        try {
            // get input stream
            InputStream ims = context.getAssets().open(nombreFicheroImagen);
            // load image as Drawable
            //res = Drawable.createFromStream(ims, null);
            Bitmap mIcon1 = BitmapFactory.decodeStream(ims);
            return mIcon1;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * **************************************************
     *  drawableFromAsset
     * **************************************************
     *
     * Obtiene un objeto Drawable de un fichero imagen de la carpeta Assets
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombreFicheroImagen nombre del fichero imagen que se intenta obtener
     * @return un bitmap con el fichero obtenido, o null si hay error o no existe el fichero
     */

    public Drawable drawableFromAsset(final Context context, String nombreFicheroImagen) {
        Drawable res = null;
        try {
            // get input stream
            InputStream ims = context.getAssets().open(nombreFicheroImagen);
            // load image as Drawable
            res = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return res;
    }


    // +------------------------------------------------+
    // |                                                |
    // |    BITMAP TO DRAWABLE                          |
    // |                                                |
    // +------------------------------------------------+
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable d = new BitmapDrawable(context.getResources(), bitmap);
        return d;
    }

    // +------------------------------------------------+
    // |                                                |
    // |    IMAGEVIEW TO BITMAP                         |
    // |                                                |
    // +------------------------------------------------+
    public static Bitmap imageviewToBitmap(Context context, ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap imagenEnBmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return imagenEnBmp;
    }

    // +------------------------------------------------+
    // |                                                |
    // |    BITMAP TO BYTEARRAY                         |
    // |                                                |
    // +------------------------------------------------+
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // +------------------------------------------------+
    // |                                                |
    // |    BYTEARRAY TO BITMAP                         |
    // |                                                |
    // +------------------------------------------------+
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    // +------------------------------------------------+
    // |                                                |
    // |    BITMAP TO STRINGBASE64                      |
    // |                                                |
    // +------------------------------------------------+
    public static String bitMapToStringBase64(Context context, Bitmap bitmap) {
        // LIMITACION: La imagen no debe superar 1MB (es algo mas pero asi aseguramos)
        // LIMITACION: Solo vale con un MIN API 26 
        String imagenCodificadaComoStringEnBase64 = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            imagenCodificadaComoStringEnBase64 = Base64.getEncoder().encodeToString(byteArray);
            // este String se mete en un pojo que tenga solo el string como atributp y 
            // se sube como un registro normal a firestore
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagenCodificadaComoStringEnBase64;
    }

    // +------------------------------------------------+
    // |                                                |
    // |    STRINGBASE64 TO BITMAP                      |
    // |                                                |
    // +------------------------------------------------+
    public static Bitmap stringBase64ToBitmap(String imagenCodificadaComoBase64) {
        byte[] imagenEnByteArray = Base64.getDecoder().decode(imagenCodificadaComoBase64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenEnByteArray, 0, imagenEnByteArray.length);
        return bitmap;
    }

    // +------------------------------------------------+
    // |                                                |
    // |  CONVERTIR UN BITMAP EN UN DRAWABLE CIRCULAR   |
    // |                                                |
    // +------------------------------------------------+
    public static void ponerImagenConFormatoRedondoEnImageView(Context context, Bitmap bitmap) {
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);

        // ESTE DRAWABLE SE PUEDE PONER EN UN IMAGEVIEW, POR EJEMPLO
        //imageview.setImageDrawable(circularBitmapDrawable);
        //imageview.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageview.setMinimumHeight(100);
    }

    // +------------------------------------------------+
    // |                                                |
    // |    GET DRAWABLE ID  BY NAME                    |
    // |                                                |
    // +------------------------------------------------+
    public static int getDrawableIdByName(Context context, String nombre) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(nombre, "drawable", context.getPackageName());
        return resourceId;
    }




}
