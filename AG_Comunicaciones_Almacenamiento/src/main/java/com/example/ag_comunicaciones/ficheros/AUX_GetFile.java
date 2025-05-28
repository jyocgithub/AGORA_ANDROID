package com.example.ag_comunicaciones.ficheros;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.example.ag_comunicaciones.BuildConfig;
import com.example.ag_comunicaciones.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


/*
    public static File getFile_UnidadInterna_CarpetaFiles           (Context context, String nombredelficheroDestino) {
    public static File getFile_UnidadInterna_CarpetaPropia          (Context context, String directoriopropio, String nombredelficheroDestino) {
    public static File getFile_UnidadExternaPrivada_CarpetaRaiz     (Context context, String nombredelficheroDestino) {
    public static File getFile_UnidadExternaPrivada_CarpetaPropia   (Context context, String carpeta, String nombredelficheroDestino) {
    public static File getFile_UnidadExternaPublica                 (String DIRECTORIOEXTERNO, String nombrecarpetaDestino, String nombrefichero) {

    public static void abrirFichero_UnidadExternaPublica            (AppCompatActivity activity, String tipoMIME) {
    public static void crearFicheroTexto_UnidadExternaPublica       (AppCompatActivity activity,String nombrefile, String contenido) {
    public static void crearFicheroBitmap_UnidadExternaPublica      (AppCompatActivity activity,String nombrefile, Bitmap bitmap) {

 */
public class AUX_GetFile {

    // +----------------------------------------------------+
    // |                                                    |
    // |  ACCESO UNIDAD INTERNA CARPETA FILES               |
    // |                                                    |
    // |  NO NECESITA DESPUES ACCEDER CON FILE PROVIDER     |
    // |                                                    |
    // +----------------------------------------------------+
    public static File getFile_UnidadInterna_CarpetaFiles(Context context, String nombredelficheroDestino) {
        File fileDestino = new File(context.getFilesDir(), nombredelficheroDestino);
        return fileDestino;
    }

    // +----------------------------------------------------+
    // |                                                    |
    // |  ACCESO UNIDAD INTERNA CARPETA PROPIA EN FILES     |
    // |                                                    |
    // |  NO NECESITA DESPUES ACCEDER CON FILE PROVIDER     |
    // |                                                    |
    // +----------------------------------------------------+
    public static File getFile_UnidadInterna_CarpetaPropia(Context context, String directoriopropio, String nombredelficheroDestino) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File carpeta = wrapper.getDir(directoriopropio, Context.MODE_PRIVATE);
        //File carpeta = wrapper.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE);  // otra fofrma
        File fileDestino = new File(carpeta, nombredelficheroDestino);
        return fileDestino;
    }


    // +-------------------------------------------------+
    // |                                                 |
    // |  ACCESO UNIDAD EXTERNA ZONA APP CARPETA FILES   |
    // |                                                 |
    // |  NO NECESITA DESPUES ACCEDER CON FILE PROVIDER  |
    // |                                                 |
    // +-------------------------------------------------+
    public static File getFile_UnidadExternaPrivada_CarpetaRaiz(Context context, String nombredelficheroDestino) {
        //ContextWrapper wrapper = new ContextWrapper(context);

        File fileDestino = new File(context.getExternalFilesDir("."), nombredelficheroDestino);
        try {
            if (!fileDestino.exists())
                fileDestino.createNewFile();  // por si acaso android en el futuro ..... hace algo raro
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileDestino;
    }


    // +-----------------------------------------------------------------+
    // |                                                                 |
    // |  ACCESO UNIDAD EXTERNA ZONA APP CARPETA PROPIA DENTRO DE FILES  |
    // |                                                                 |
    // |  NO NECESITA DESPUES ACCEDER CON FILE PROVIDER                  |
    // |                                                                 |
    // +-----------------------------------------------------------------+
    public static File getFile_UnidadExternaPrivada_CarpetaPropia(Context context, String carpeta, String nombredelficheroDestino) {
        File carpetaDestino = null;
        File fileDestino = null;
        try {
            carpetaDestino = new File(context.getExternalFilesDir("."), carpeta);
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            fileDestino = new File(carpetaDestino, nombredelficheroDestino);
            if (!fileDestino.exists())
                fileDestino.createNewFile();  // por si acaso android en el futuro ..... hace algo raro
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileDestino;
    }


    // +-------------------------------------------------------------------------------------------+
    // |                                                                                           |
    // |  ACCESO UNIDAD EXTERNA ZONA PUBLICA CARPETA DEL SISTEMA                                   |
    // |    SOLO VALE HASTA API 28                                                                 |
    // |    NO NECESITA DESPUES FILE PROVIDER PARA ESCRIBIR                                        |
    // |    NO NECESITA DESPUES FILE PROVIDER PARA LEER SI EL FICHERO FUE CREADO POR NUESTRA APP   |
    // |    NECESITA FILE PROVIDER PARA ABRIR FICHEROS CREADOS CON OTRA APP                        |
    // |                                                                                           |
    // |    SIEMPRE EXIGE PERMISOS WRITE_EXTERNAL_STORAGE o READ_EXTERNAL_STORAGE                  |
    // +-------------------------------------------------------------------------------------------+

    /**
     * @param DIRECTORIOEXTERNO    Ubicacion RAIZ del almacenamieno externo
     *                             El valor de DIRECTORIOEXTERNO que se puden usar con este metodo 
     *                             son (entre otros):
     *                                 Environment.DIRECTORY_DOWNLOADS
     *                                 Environment.DIRECTORY_PICTURES
     *                                 Environment.DIRECTORY_DOCUMENTS
     *                                 Environment.DIRECTORY_DCIM
     *                                 Environment.DIRECTORY_MOVIES
     *                                 Environment.DIRECTORY_MUSIC
     *                                 Environment.DIRECTORY_PODCASTS
     * @param nombrecarpetaDestino Por si deseamos una carpeta propia, sino, poner "."
     * @param nombrefichero        Fichero al que se desea acceder       
     * @return Objeto File con la referencia al fichero que se desea acceder 
     */
    public static File getFile_UnidadExternaPublica(String DIRECTORIOEXTERNO, String nombrecarpetaDestino, String nombrefichero) {
        File dir;
        if (nombrecarpetaDestino == null || nombrecarpetaDestino.trim().isEmpty()) {
            //File file =Environment.getExternalStorageDirectory();
            dir = Environment.getExternalStoragePublicDirectory(DIRECTORIOEXTERNO);
        } else {
            dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORIOEXTERNO), nombrecarpetaDestino);
            if (!dir.exists())
                dir.mkdirs();
        }
        return new File(dir, nombrefichero);
    }



    // +-------------------------------------------------------------+
    // |                                                             |
    // |  CREA UN FICHERO DE TEXTO TEMPORAL                          |
    // |  DEVUELVE LA REFERENCIA FILE DEL FICHERO CREADO             |
    // |  EL FICHERO SE BORRA AUTOMATICAMENTE AL CERRAR LA APP       |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static File getTempFile(Context context, String nombrefile, String extensionfile) {
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
    // |  OBTIENE UN URI-FILE A PARTIR DE UN FILE                    |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static Uri getURIFromFile(File file) {
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
    // |  OBTIENE UN URI DE UN FILE PROVIDER                         |
    // | 
    // | 
    // |            CREAR FICHERO res/xml/mispaths.xml 
    // |            ----------------------------------------------
    // |            <?xml version="1.0" encoding="utf-8"?>
    // |            <paths xmlns:android="http://schemas.android.com/apk/res/android">
    // |                <files-path
    // |                    name="nombredepath1"
    // |                    path="." />
    // |                <files-path
    // |                    name="nombredepath2"
    // |                    path="files/" />
    // |                <external-files-path
    // |                    name="nombredepath3"
    // |                    path="." />
    // |                <external-files-path
    // |                    name="nombredepath4"
    // |                    path="epubs/" />
    // |                <external-path
    // |                    name="nombredepath5"
    // |                    path="." />
    // |                <external-path
    // |                    name="nombredepath6"
    // |                    path="DCIM" />
    // |            </paths>
    
                
    // |            CREAR ETIQUETA PRIVIDER EN EL MANIFEST 
    // |            ----------------------------------------------
                //<application>
                //  . . . . . 
                //    <provider
                //        android:name="androidx.core.content.FileProvider"
                //        android:authorities="${applicationId}.mifileprovider"
                //        android:exported="false"
                //        android:grantUriPermissions="true">
                //        <meta-data
                //            android:name="android.support.FILE_PROVIDER_PATHS"
                //            android:resource="@xml/mispaths.xml" />
                //    </provider>
                //</application>




    //     getUriFromFileProvider(this, "cosas.txt", "mifileprovider");
    
    
    // |                                                             |
    // +-------------------------------------------------------------+




    public static Uri getUriFromFileProvider(Context context, String filename, String nameOfFileProvider) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + filename);
        String fileauthorities = BuildConfig.APPLICATION_ID + "." + nameOfFileProvider;
        Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), fileauthorities, file);
        return uri;

    }

    
    
    
    
    
    
    
    
    
}
/**************************************************************************************
 * TIPOS MIME 
 * ************************************************************************************
 * Esta lista muestra los tipos de ficheros mas habituales y su tipo MIME asociado
 * cualquier archivo    -> * /*
 * cualquier imagen     -> image/*
 * cualquier texto      -> text/*
 * cualquier audio      -> audio/*
 * cualquier video      -> video/*
 * .xml     -> text/xml
 * .txt     -> text/plain
 * .cfg     -> text/plain
 * .csv     -> text/plain
 * .conf    -> text/plain
 * .rc      -> text/plain
 * .htm     -> text/html
 * .html    -> text/html
 * .png     -> image/png
 * .gif     -> image/gif
 * .jpeg    -> image/jpeg
 * .jpg     -> image/jpeg
 * .mpeg    -> audio/mpeg
 * .aac     -> audio/aac
 * .wav     -> audio/wav
 * .ogg     -> audio/ogg
 * .midi    -> audio/midi
 * .wma     -> audio/x-ms-wma
 * .mpg     -> audio/mpeg4-generic
 * .mp4     -> video/mp4
 * .msv     -> video/x-msvideo
 * .wmv     -> video/x-ms-wmv
 * .pdf     -> application/pdf
 * .apk     -> application/vnd.android.package-archive
 */