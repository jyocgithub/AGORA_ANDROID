package com.example.ag_comunicaciones.ficheros;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.ag_comunicaciones.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


public class AUX_WriteFile {

//        Las apps se guardan en el almacenamiento interno de forma predeterminada. 
//        Sin embargo, si el tamaño del archivo APK es muy grande, 
//        puedes indicar una preferencia en el archivo de manifiesto de la app 
//        para instalarla en el almacenamiento externo como se muestra a continuación:
//
//
//            <manifest ...
//                android:installLocation="preferExternal">
//                        ...
//            </manifest>

    // +----------------------------------------------------+
    // |                                                    |
    // |  ALMACENAMIENTO SIMPLE                             |
    // |  SIN FILE PROVIDER                                 |
    // |                                                    |
    // +----------------------------------------------------


    // +----------------------------------------------------+
    // |                                                    |
    // |  GUARDA UN STRING EN UN FILE                       |
    // |  PUEDE USARSE EN ALMACENAMIENTO PRIVADO            |
    // |                                                    |
    // +----------------------------------------------------
    public static void writeStringInFileObject(File fileDestino, String contenido) {
        try (PrintWriter pw = new PrintWriter(fileDestino)) {
            pw.println(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // +----------------------------------------------------------------------+
    // |                                                                      |
    // |  GUARDA UN STRING EN UN FICHERO DE TEXTO DEFINIDO POR UN CONTENT-URI |
    // |  PARA USARSE EN ALMACENAMIENTO PUBLICO                               |
    // |                                                                      |
    // +----------------------------------------------------------------------+
    public static boolean writeStringInContentUi(Context context, Uri contentUri, String texto) {
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
    // |  GUARDA UN BITMAP EN UN FICHERO DEFINIDO POR UN FILE SIMPLE |
    // |  PUEDE USARSE EN ALMACENAMIENTO PRIVADO                     |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static boolean writeBitMapInFileObject(Context context, File fileDestino, Bitmap bitmap) {
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
    // |  GUARDA UN BITMAP EN UN FICHERO DEFINIDO POR UN CONTENT-URI |
    // |  PARA USARSE EN ALMACENAMIENTO PUBLICO                      |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static boolean writeBitMapInContentUri(Context context, Uri contentUri, Bitmap bitmap) {
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




    // +------------------------------------------------------------------------------+
    // |                                                                              |
    // |  CREAR UN FICHERO TEXTO EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION  |
    // |  VALE DESDE API 28                                                           |
    // |                                                                              |
    // +------------------------------------------------------------------------------+
    public static void crearFicheroTexto_UnidadExternaPublica(AppCompatActivity activity, String nombrefile, String contenido) {
        ActivityResultLauncher<String> miARL_crearFicheroTexto;
        // usar registerForActivityResult siempre FUERA de los listeners
        miARL_crearFicheroTexto = activity.registerForActivityResult(
                new ActivityResultContracts.CreateDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        AUX_File.guardarStringEnContentUri(activity, uri, contenido);
                    }
                });
        // lanzar la peticion (en un listener, si se quiere)
        miARL_crearFicheroTexto.launch(nombrefile);
    }

    // +------------------------------------------------------------------------------+
    // |                                                                              |
    // |  CREAR UN FICHERO BITMAP EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION |
    // |                                                                              |
    // +------------------------------------------------------------------------------+
    public static void crearFicheroBitmap_UnidadExternaPublica(AppCompatActivity activity, String nombrefile, Bitmap bitmap) {
        ActivityResultLauncher<String> miARL_crearFicheroTexto;
        // usar registerForActivityResult siempre FUERA de los listeners
        miARL_crearFicheroTexto = activity.registerForActivityResult(
                new ActivityResultContracts.CreateDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        AUX_File.guardarBitMapEnContentUri(activity, uri, bitmap);
                    }
                });
        // lanzar la peticion (en un listener, si se quiere)
        miARL_crearFicheroTexto.launch(nombrefile);
    }





    // +------------------------------------------------------------------------------+
    // |                                                                              |
    // |  APOYO                                                                       |
    // |                                                                              |
    // +------------------------------------------------------------------------------+

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


    // +-------------------------------------------------------------+
    // |                                                             |
    // |  COPIA UN FICHERO BYTE A BYTE                               |
    // |                                                             |
    // +-------------------------------------------------------------+
    public static void copyFileBytetoByte(FileInputStream forigen, FileOutputStream fdestino) throws IOException {
        FileChannel channelOrigen = null;
        FileChannel channelDestino = null;
        try {
            channelOrigen = forigen.getChannel();
            channelDestino = fdestino.getChannel();
            channelOrigen.transferTo(0, channelOrigen.size(), channelDestino);
        } finally {
            try {
                if (channelOrigen != null) {
                    channelOrigen.close();
                }
            } finally {
                if (channelDestino != null) {
                    channelDestino.close();
                }
            }
        }
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