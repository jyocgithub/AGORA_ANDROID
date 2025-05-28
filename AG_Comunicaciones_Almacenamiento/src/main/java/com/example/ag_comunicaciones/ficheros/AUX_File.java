package com.example.ag_comunicaciones.ficheros;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
public class AUX_File {



    /**
     *
     * @param context
     * @param pNombreFichero
     * @param pNombreCarpetaDispositivo
     *            Ejemplos: Environment.DIRECTORY_PICTURES
     *                      Environment.DIRECTORY_DOWNLOADS
     *                      Environment.DIRECTORY_DOCUMENTS
     *                      Environment.DIRECTORY_MUSIC
     * @return
     */
    public static File getFileAreaPublicaDispositivo( Context context, String pNombreCarpetaDispositivo, String pNombreFichero) {
        // AREA PUBLICA DEL DISPOSITIVO: UN FICHERO EN LA RAIZ DE LA CARPETA DETERMINADA
        //File carpeta = context.getExternalFilesDir(pNombreCarpetaDispositivo);
        //return  new File(carpeta, pNombreFichero);

        //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        ContentResolver resolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pNombreFichero);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,pNombreCarpetaDispositivo);

        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        String pathfinal = uri.getPath();
        return new File(pathfinal);

    }

    ///**
    // * 
    // * @param context
    // * @param pNombreFichero
    // * @param pNombreCarpetaDispositivo   
    // *            Ejemplos: Environment.DIRECTORY_PICTURES
    // *                      Environment.DIRECTORY_DOWNLOADS
    // *                      Environment.DIRECTORY_DOCUMENTS
    // *                      Environment.DIRECTORY_MUSIC
    // * @return
    // */
    //public static File getFileAreaPublicaDispositivoCarpetaPropia( Context context, String pNombreCarpetaDispositivo, String pNombreCarpetaPropia, String pNombreFichero ) {
    //    // AREA PUBLICA DEL DISPOSITIVO: UN FICHERO EN LA RAIZ DE LA CARPETA DETERMINADA Y DENTRO EN NUESTRAS CARPETA
    //    //File carpeta = context.getExternalFilesDir(pNombreCarpetaDispositivo);
    //    //File f1 =   new File(carpeta, pNombreCarpetaPropia);
    //    //if (! f1.exists()) {
    //    //    f1.mkdirs();
    //    //}
    //    //return  new File(f1, pNombreFichero);
    //}

    

    /* ****************************************************************************
     *  ***************************************************************************
     *  *******      GUARDAR Y LEER COSAS CON UN FILE YA EXISTENTE      ***********
     *  ***************************************************************************
     *  ***************************************************************************/


    // +----------------------------------------------------+
    // |                                                    |
    // |  GUARDA UN STRING EN UN FILE                       |
    // |                                                    |
    // +----------------------------------------------------
    public static void guardarStringEnFile(File fileDestino, String contenido) {
        try (PrintWriter pw = new PrintWriter(fileDestino)) {
            pw.println(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // +----------------------------------------------------------------------+
    // |                                                                      |
    // |  GUARDA UN STRING EN UN FICHERO DE TEXTO DEFINIDO POR UN CONTENT-URI |
    // |                                                                      |
    // +----------------------------------------------------------------------+
    public static boolean guardarStringEnContentUri(Context context, Uri contentUri, String texto) {
        try (OutputStream os = context.getContentResolver().openOutputStream(contentUri)) {
            PrintWriter pw = new PrintWriter(os);
            pw.print(texto);
            pw.flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // +-------------------------------------------------------------+
    // |                                                             |
    // |  GUARDA UN BITMAP EN UN FICHERO DEFINIDO POR UN CONTENT-URI |
    // |  PARA USARSE EN ALMACENAMIENTO PUBLICO                      |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static boolean guardarBitMapEnContentUri(Context context, Uri contentUri, Bitmap bitmap) {
        try (OutputStream os = context.getContentResolver().openOutputStream(contentUri)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    // +-------------------------------------------------------------+
    // |                                                             |
    // |  GUARDA UN BITMAP EN UN FICHERO DEFINIDO POR UN FILE SIMPLE |
    // |  PUEDE USARSE EN ALMACENAMIENTO PRIVADO                     |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static boolean guardarBitMapEnFile(Context context, File fileDestino, Bitmap bitmap) {
        try (OutputStream os = new FileOutputStream(fileDestino)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    // +-------------------------------------------------------------+
    // |                                                             |
    // |  OBTIENE UN URI-FILE A PARTIR DE UN FILE                    |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static Uri obtenerUriDesdeUnFile(File file) {
        Uri uriDelDFichero = Uri.fromFile(new File(file.getAbsolutePath()));
        return uriDelDFichero;
    }

    // +-------------------------------------------------------------+
    // |                                                             |
    // |  OBTIENE UN JSON DESDE UNA URL-WEB                          |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static String obtenerJSONDesdeUrl(String urlALeer) {
        URL url = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        BufferedReader br_delaurl = null;
        String todo = "";
        try {
            url = new URL(urlALeer);
            br_delaurl = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea;
            while ((linea = br_delaurl.readLine()) != null) {
                todo += linea;
            }
        } catch (Exception e) {
            e.printStackTrace();
            todo = null;
        } finally {
            if (br_delaurl != null) {
                try {
                    br_delaurl.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    todo = null;
                }
            }
            return todo;
        }


    }



    // +-------------------------------------------------------------+
    // |                                                             |
    // |  CREA UN FICHERO DE TEXTO TEMPORAL                          |
    // |  DEVUELVE LA REFERENCIA FILE DEL FICHERO CREADO             |
    // |  EL FICHERO SE BORRA AUTOMATICAMENTE AL CERRAR LA APP       |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static File crearTempFile(Context context, String nombrefile, String extensionfile) {
        File temp = null;
        // El directorio temporal se recoge con la funcion getCacheDir()
        File dirtem = context.getCacheDir();
        try {
            // El fichero se crea, pero solo el objeto FiLe, su referencia.
            temp = File.createTempFile(nombrefile, extensionfile, dirtem);
            // El fichero se borrará, el solo, cuando el programa termine
            temp.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    // +-------------------------------------------------------------+
    // |                                                             |
    // |  LEE LISTA NOMBRES DE FICHEROS DE UNIDAD INTERNA            |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static String[] leerListaFicherosInternos(Context context) {
        String[] files = context.fileList();
        return files;
    }


    public static Bitmap uriToBitmap(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}