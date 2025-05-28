package com.jyoc.aux;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class AUX_Recursos {
    
    
    public void getUriFromResource(){
        
    }

    /**
     * **************************************************
     *  getBitmapFromDrawableConId
     * **************************************************
     *
     * Obtiene un bitmap del nombre de un drawable
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param idDelDrwawable id de la imagen en drawables (R.drawable.mago , por ejemplo)
     * @return bitmap obtenido 
     */
    public static Bitmap getBitmapFromDrawableConId(Context context, int idDelDrwawable) {
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), idDelDrwawable);
        return imagenEnBmp;
    }


    /**
     * **************************************************
     *  getBitmapFromDrawableConNombre
     * **************************************************
     *
     * Obtiene un bitmap del nombre de un drawable
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombrerecurso nombre (sin extension) de la imagen en drawables
     * @return bitmap obtenido 
     */
    public static Bitmap getBitmapFromDrawableConNombre(Context context, String nombrerecurso) {
        // CUIDADO, EL NOMBRE DEL RECUSRO (FICHERO IMAGEN) ES SIN EXTENSION 
        int resourceId = getIdRecursoFromNombreRecurso(context, "drawable", nombrerecurso);
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return imagenEnBmp;
    }

    /**
     * **************************************************
     *  getIdRecursoFromNombreRecurso
     * **************************************************
     *
     * Obtiene el id de un recurso (drawable, menu, layout, etc) a partir de su nombre
     * Ejemplo de uso: 
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param tiporecurso  tipo de recurso que se busca: "drawable", "menu", etc
     * @param nombrerecurso nombre del recurso buscado
     * @return el int con el id del recurso, o 0 si no se encuentra
     */
    public static int getIdRecursoFromNombreRecurso(Context context, String tiporecurso, String nombrerecurso) {
        // CUIDADO, EL NOMBRE DEL RECURSO (FICHERO IMAGEN) ES SIN EXTENSION 
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(nombrerecurso, tiporecurso, context.getPackageName());
        return resourceId;
    }

    /**
     * **************************************************
     *  getBitmapFromAsset
     * **************************************************
     *
     * Obtiene un bitmap de un fichero imagen de la carpeta Assets
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombreFicheroImagen nombre del fichero imagen que se intenta obtener
     * @return un bitmap con el fichero obtenido, o null si hay error o no existe el fichero
     */
    public static Bitmap getBitmapFromAsset(final Context context, String nombreFicheroImagen) {
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
     *  getDrawableFromAsset
     * **************************************************
     *
     * Obtiene un objeto Drawable de un fichero imagen de la carpeta Assets
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param nombreFicheroImagen nombre del fichero imagen que se intenta obtener
     * @return un bitmap con el fichero obtenido, o null si hay error o no existe el fichero
     */

    public Drawable getDrawableFromAsset(final Context context, String nombreFicheroImagen) {
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
    
    
    
}
