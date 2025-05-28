package com.example.ag_fileprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GUT_Files {

    
    public static File getFileAreaPrivadaFiles(Context context, String pNombreFichero){
        // AREA PRIVADA DE LA APLICACION: UN FICHERO EN LA CARPETA FILES
        return new File(context.getFilesDir(), pNombreFichero);
    }
    
    public static File getFileAreaPrivadaMiCarpeta(Context context, String pNombreCarpeta, String pNombreFichero){
        // AREA PRIVADA DE LA APLICACION: UN FICHERO EN LA CARPETA FILES Y DENTRO EN UN CARPETA PROPIA
        File carpeta = new File(context.getFilesDir().getAbsolutePath() + "/" + pNombreCarpeta);
        if (! carpeta.exists()) {
            carpeta.mkdirs();
        }
        return new File(carpeta, pNombreFichero);
    }

    public static File getFileAreaPublicaAppFiles(Context context, String pNombreFichero) {
        // AREA PUBLICA DE LA APLICACION: UN FICHERO EN LA CARPETA FILES
        File dir = context.getExternalFilesDir(null);
        File file = new File(dir, pNombreFichero);
        return file;
    }

    public static  File getFileAreaPublicaAppCarpetaPropia(Context context, String pNombreFichero, String pNombreCarpeta){
        // AREA PUBLICA DE LA APLICACION: UN FICHERO EN LA CARPETA FILES Y DENTRO EN UN CARPETA PROPIA
        File dir = context.getExternalFilesDir(pNombreCarpeta);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, pNombreFichero);
    }
    // MÉTODO INVENTADO PARA OBTENER UNA REFERENCIA DE UN FICHERO 
    // EN DIRECTORIOS PUBLICOS DEL DISPOSITIVO

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
    

    public static void escribirStringEnFichero(File f, String contenido, boolean append) {
        try (FileOutputStream fos = new FileOutputStream(f, append)) {
            if (!f.exists()) {
                f.createNewFile();
            }
            fos.write(contenido.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String leerStringDeFichero(File f) {
        StringBuilder sb = new StringBuilder();
        if (f.exists()) {
            String lineaLeida;
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                while ((lineaLeida = br.readLine()) != null) {
                    sb.append(lineaLeida);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
