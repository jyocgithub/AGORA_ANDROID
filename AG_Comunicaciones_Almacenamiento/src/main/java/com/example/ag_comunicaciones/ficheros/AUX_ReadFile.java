package com.example.ag_comunicaciones.ficheros;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;

import com.example.ag_comunicaciones.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import androidx.core.content.FileProvider;

public class AUX_ReadFile {


    // +----------------------------------------------------+
    // |                                                    |
    // |  LEE UN FICHERO DE TEXTO DESDE UN FILE             |
    // |  DEVUELVE UN STRING                                |
    // |                                                    |
    // +----------------------------------------------------+
    public static String leerStringDeFile(File fileOrigen) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileOrigen))) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // +----------------------------------------------------+
    // |                                                    |
    // |  LEE UN FICHERO DE TEXTO DESDE UN FILE             |
    // |  DEVUELVE UN ARRAYLIST DE STRING                   |
    // |                                                    |
    // +----------------------------------------------------+
    public static ArrayList<String> leerArrayStringDeFile(File file, String nombredelficheroOrigen) {
        ArrayList<String> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lista.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // +----------------------------------------------------+
    // |                                                    |
    // |  LEE UN FICHERO DE TEXTO DESDE UN CONTENT-URI      |
    // |  DEVUELVE UN STRING                                |
    // |                                                    |
    // +----------------------------------------------------+
    public static String leerStringDeContentUri(Context context, Uri contentUri) {

        try {
            InputStream is = context.getContentResolver().openInputStream(contentUri);

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    // +----------------------------------------------------+
    // |                                                    |
    // |  LEE UN BITMAP DESDE UN FILE                       |
    // |  DEVUELVE EL BITMAP                                |
    // |                                                    |
    // +----------------------------------------------------+
    public static Bitmap leerBitMapDeFile(Context ctx, File file) {
        Uri uri = AUX_GetFile.getURIFromFile(file);
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
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