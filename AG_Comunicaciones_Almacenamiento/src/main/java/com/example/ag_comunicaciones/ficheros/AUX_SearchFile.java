package com.example.ag_comunicaciones.ficheros;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.ag_comunicaciones.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


public class AUX_SearchFile {


    // +-------------------------------------------------------------------------------+
    // |                                                                               |
    // |  BUSCAR Y ABRIR FICHERO EN UNIDAD EXTERNA ZONA PUBLICA, ELIGIENDO UBICACION   |
    // |  ACCESO UNIDAD EXTERNA ZONA PUBLICA CARPETA DEL SISTEMA                       |
    // |  VALE DESDE API 28                                                            |
    // |                                                                               |
    // +-------------------------------------------------------------------------------+
    // usar buscarArchivoEnDispositivo_preparar siempre FUERA de los listeners
    ActivityResultLauncher<String> ARL_buscarArchivo;
    public void buscarArchivoEnDispositivo_preparar(AppCompatActivity activity, String tipoMIME) {
        ARL_buscarArchivo = activity.registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        buscarArchivoEnDispositivo_resultados(activity, uri, tipoMIME);
                    }
                });
    }

    public void buscarArchivoEnDispositivo_lanzar(AppCompatActivity activity, String tipoMIME) {
        ARL_buscarArchivo.launch(tipoMIME);
    }

    public void buscarArchivoEnDispositivo_resultados(AppCompatActivity activity, Uri uri, String tipoMIME) {
        switch (tipoMIME) {
            case "image/*":
                Bitmap bitmap = AUX_File.uriToBitmap(activity, uri);
                ((MainActivity) activity).ivImagen.setImageBitmap(bitmap);
                break;
            case "text/*":
                //File file = new File(uri.getPath());
                String texto = AUX_ReadFile.leerStringDeContentUri(activity, uri);
                Log.d("JYOC***", texto);
                break;
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