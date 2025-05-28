package com.example.ag_comunicaciones.compartir;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ag_comunicaciones.BuildConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;





/*
 * INDICE - COMUNICACION CON OTRAS APPS   (ANDROID)
 * ################################################################################
 *
 *     - buscarArchivoEnDispositivo      (Activity actividad, String tipomime)
 *     - hacerLlamadaTelefonica          (Activity actividad, String telefono)
 *     - enviarSMS                       (Activity actividad, String texto, String numerodestino)
 *     - enviarWhatsappTextoSinNumero    (Activity actividad, String mensaje)
 *     - enviarWhatsappANumero           (Activity actividad, String numero, String mensaje)
 *     - enviarMail                      (Activity actividad, String asunto, String mensaje, String destinatariosSeparadosPorComas)
 *     - enviarMailConFichero            (Activity actividad, String asunto, String mensaje, String destinatariosSeparadosPorComas)
 *     - compartirTextoConOtrasAPPs      (String texto)
 *     - compartirArchivoConOOtrasApps   (File file, Activity actividad)
 *
 * ################ (fin)
 */

public class AUX_Compartir {


    // +------------------------------------------------+
    // |                                                |
    // |    BUSCAR UN ARCHIVO EN EL SISTEMA DE ARCHIVOS |
    // |    DEL MOVIL, CON ACTIVITYRESULTLAUNCHER       |
    // |    FILTRA POR EL TIPO DE FICHERO               |
    // +------------------------------------------------+
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

    public void buscarArchivoEnDispositivo_lanzar(AppCompatActivity activity, String tipofichero) {
        ARL_buscarArchivo.launch(tipofichero);
    }

    public void buscarArchivoEnDispositivo_resultados(AppCompatActivity activity, Uri uri, String tipofichero) {
        //try {
        //
        //    Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        //    ImageView ivImagen = findViewById(R.id.ivImagen);
        //    ivImagen.setImageBitmap(bitmap);
        //
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }


    // +------------------------------------------------+
    // |                                                |
    // |    BUSCAR UN ARCHIVO EN EL SISTEMA DE ARCHIVOS |
    // |    DEL MOVIL, SIN ACTIVITYRESULTLAUNCHER       |
    // |    OBSOLETO DESDE API 28  (ANDROIDX)           |
    // +------------------------------------------------+
    //public static void seleccionarFicheroDelDispositivo(Activity actividad, String tipoMIME) {
    //    //--  Version 1
    //    //--      - sin elegir explorador de archivos, se usara la aplicaion por defecto del movil
    //    //--      - para un tipo especifico de fichero
    //    //--      (cambiar el tipo MIME de seleccion de text a lo que se desee, ver patrones MIME en el metodo enviarMailConFichero() )
    //    //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    //    //intent.setType(tipoMIME);  // cambiar el tipo MIME para seleccionar otros tipos de ficheros
    //    //actividad.startActivityForResult(intent, JYOC_CONSTANTE_SELECCIONAR_FICHERO);
    //
    //    //-- Version 2
    //    //--      - para elegir entre las diferentes apps que puedan abrir el MIME seleccionado
    //    Intent intent_del_get = new Intent(Intent.ACTION_GET_CONTENT);
    //    intent_del_get.setType(tipoMIME);// cambiar el tipo MIME para seleccionar otros tipos de ficheros
    //
    //    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //    pickIntent.setType(tipoMIME);// cambiar el tipo MIME para seleccionar otros tipos de ficheros
    //
    //    Intent chooserIntent = Intent.createChooser(intent_del_get, "Seleccione archivo:");
    //    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
    //
    //    actividad.startActivityForResult(chooserIntent, JYOC_CONSTANTE_SELECCIONAR_FICHERO);
    //
    //    //-- SI SE DESEA ELEGIR SOLO ENTRE CIERTOS TIPOS MIME, USAR ESTE MODELO :
    //    //final String[] SOLO_CIERTOS_MIME = {
    //    //        "application/pdf",
    //    //        "image/*"
    //    //};
    //    //intent.putExtra(Intent.EXTRA_MIME_TYPES, SOLO_CIERTOS_MIME);
    //}
    //
    ///**
    // * onActivityResult
    // * Complemento al metodo anterior (seleccionarFicheroDelDispositivo) que trata el fichero seleccionado
    // * ESTE METODO HA DE DESCOMENTARSE Y COPIARSE EN LA ACTIVIDAD INICIAL, DONDE SE INCLUYE EL METODO ANTERIOR;
    // * NO VALE INVOCARLO CON UN OBJETO DE ESTA CLASE
    // */
    //// DESCOMENTAR Y COPIAR EN LA ACTIVIDAD INICIAL DE LA APLICACION
    ////@Override
    ////public void onActivityResult(int requestCode, int resultCode, Intent data) {
    ////    if (requestCode == JUT_AndroidCompartiendo.JYOC_CONSTANTE_SELECCIONAR_FICHERO) {
    ////        BufferedReader br = null;
    ////        String fichCompleto = "", lin;
    ////        FileInputStream inputStream = null;
    ////        try {
    ////            if (data != null) {  // data puede ser null si el usuario cancela la seleccion del archivo
    ////                inputStream = (FileInputStream) this.getContentResolver().openInputStream(data.getData());
    ////                //--  OPCION 1:
    ////                //--  Si el fichero es un fichero de texto, asi lo leo y lo recojo en una variable String
    ////                br = new BufferedReader(new InputStreamReader(inputStream));
    ////                while ((lin = br.readLine()) != null) {
    ////                    fichCompleto += lin;
    ////                }
    ////
    ////                //--  OPCION 2:
    ////                //--  Si el fichero es una imagen, asi obtengo un bitmap de la misma y lo pongo en un ImageView;
    ////                //Bitmap fotoTomada  = BitmapFactory.decodeStream(inputStream);
    ////                //im.setImageBitmap(fotoTomada);
    ////                //im.setImageBitmap(fotoTomada);
    ////                //im.setImageBitmap(fotoTomada);
    ////                //im.setImageBitmap(fotoTomada);
    ////                //im.setImageBitmap(fotoTomada);
    ////            }
    ////
    ////        } catch (IOException e) {
    ////            e.printStackTrace();
    ////        } finally {
    ////            try {
    ////                if (br != null)
    ////                    br.close();
    ////            } catch (IOException e) {
    ////                e.printStackTrace();
    ////            }
    ////        }
    ////    }
    ////}


    // +---------------------------------------------+
    // |                                             |
    // |  HACER LLAMADA TELEFONICA                   |
    // |                                             |
    // +---------------------------------------------+
    // REQUIERE PERMISO OTORGADO PARA Manifest.permission.CALL_PHONE
    public static void hacerLlamadaTelefonica(Activity actividad, String telefono) {
        if (actividad.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + telefono));
            actividad.startActivity(i);
        } else {
            Toast.makeText(actividad.getApplicationContext(), "NECESITA OTORGAR PERMISOS PARA LLAMADAS ", Toast.LENGTH_SHORT).show();
        }
    }

    // +---------------------------------------------+
    // |                                             |
    // |  ENVIAR SMS CON APP DEL MOVIL               |
    // |                                             |
    // +---------------------------------------------+
    // REQUIERE PERMISO OTORGADO PARA Manifest.permission.SEND_SMS
    public static void enviarSMSConAPP(Activity actividad, String texto, String numerodestino) {
        if (actividad.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse("smsto:" + numerodestino);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", texto);
            //i.setPackage("com.whatsapp");
            actividad.startActivity(i);

        } else {
            Toast.makeText(actividad.getApplicationContext(), "NECESITA OTORGAR PERMISOS PARA SMS ", Toast.LENGTH_SHORT).show();

        }

    }

    // +---------------------------------------------+
    // |                                             |
    // |  ENVIAR SMS DIRECTAMENTE SIN APP DEL MOVIL  |
    // |                                             |
    // +---------------------------------------------+
    // REQUIERE PERMISO OTORGADO PARA Manifest.permission.SEND_SMS
    public static void enviarSMSSinAPP(Activity actividad, String texto, String numerodestino) {
        if (actividad.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numerodestino, null, texto, null, null);
                Toast.makeText(actividad, "Mensaje enviado", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(actividad.getApplicationContext(), "NECESITA OTORGAR PERMISOS PARA SMS ", Toast.LENGTH_SHORT).show();
        }
    }


    // +---------------------------------------------+
    // |                                             |
    // |  ENVIAR WHATSAPP                            |
    // |  ABRIENDO LA APP SIN CONTACTO ELEGIDO       |
    // |                                             |
    // +---------------------------------------------+
    // Si se desea una accion personalizada si no existe Whatsapp instalado en el dispositivo,
    // añadir las lineas comentadas y cambiar la accion en el catch
    public static void enviarWhatsappSinNumero(Activity actividad, String mensaje) {

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


    // +---------------------------------------------+
    // |                                             |
    // |  ENVIAR WHATSAPP                            |
    // |  ABRIENDO LA APP Y YA CON CONTACTO ELEGIDO  |
    // |                                             |
    // +---------------------------------------------+
    // Si se desea una accion personalizada si no existe Whatsapp instalado en el dispositivo,
    // añadir las lineas comentadas y cambiar la accion en el catch
    public static void enviarWhatsappANumero(Activity actividad, String numero, String mensaje) {
        try {
            String numeroConPrefijo = "34" + numero;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + numeroConPrefijo + "&text=" + mensaje));
            actividad.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // +---------------------------------------------+
    // |                                             |
    // |  ENVIAR MAIL , SIN ADJUNTOS                 |
    // |  ABRE LA APP DE CORREO POR DEFECTO          |
    // |  CON DATOS LISTOS; SOLO PARA DAR A 'ENVIAR' |
    // +---------------------------------------------+
    public static void enviarMail(Activity actividad, String asunto, String mensaje, String[] arraydestinatarios) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        // -- EXTRA_EMAIL es un array con los destinatarios, EXTRA_SUBJECT Es el asunto del correo y EXTRA_TEXT el cuerpo del correo
        intent.putExtra(Intent.EXTRA_EMAIL, arraydestinatarios);
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        //intent.putExtra(Intent.EXTRA_CC, destinatariosconcopia);

        // comprobar que hay una app para tratar correos
        PackageManager packageManager = actividad.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean hayApp = activities.size() > 0;
        if (hayApp) {
            //____ ABRIR LA APP DE CORREO POR DEFECTO ______
            actividad.startActivity(intent);
            //____ ABRIR DIALOGO PARA ELEGIR APP DE CORREO ______
            //actividad.startActivity(Intent.createChooser(intent, "Elije tu aplicacion de correo:"));
        } else {
            Toast.makeText(actividad, "Lo siento, no hay una aplicación de correo en su movil", Toast.LENGTH_SHORT).show();
        }
    }

    // +------------------------------------------------+
    // |                                                |
    // |  ENVIAR MAIL , CON ADJUNTOS PROPIOS            |
    // |  ABRE LA APP DE CORREO POR DEFECTO             |
    // |  CON DATOS LISTOS; SOLO PARA DAR A 'ENVIAR'    |
    // |                                                |
    // |  NO TIENE SENTIDO QUE SEAN ADJUNTOS DE LA      |
    // |  ZONA PUBLICA DEL MOVIL                        |      
    // |  PUES EN TAL CASO SE PUEDEN ANEXAR YA CON      |
    // |  LA APP DE CORREO ABIERTA                      |
    // |                                                |
    // |  USA FILE PROVIDER                             |
    // |  LEE DE LOS DIRECTORIOS DEL                    |
    // |  PATH_DEL_PROVIDER.XML                         | 
    // +------------------------------------------------+
    public static void enviarMailConFichero(Activity actividad, String asunto, String mensaje, String[] arrayDestinatarios, File fileParaUnProvider) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        intent.putExtra(Intent.EXTRA_EMAIL, arrayDestinatarios);
        //intent.putExtra(Intent.EXTRA_CC, destinatariosconcopia);

        Context context = actividad.getApplicationContext();
        String fileauthorities = actividad.getPackageName() + ".fileprovider";
        //String fileauthorities = BuildConfig.APPLICATION_ID + ".fileprovider";
        Uri uri = FileProvider.getUriForFile(context, fileauthorities, fileParaUnProvider);
        actividad.grantUriPermission(actividad.getPackageName() , uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //actividad.grantUriPermission(BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        actividad.grantUriPermission(actividad.getPackageName() , uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //actividad.grantUriPermission(BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("text/*");

        // comprobar que hay una app para tratar correos
        PackageManager packageManager = actividad.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean hayApp = activities.size() > 0;
        if (hayApp) {
            //____ ABRIR LA APP DE CORREO POR DEFECTO ______
            actividad.startActivity(intent);
            //____ ABRIR DIALOGO PARA ELEGIR APP DE CORREO ______
            //actividad.startActivity(Intent.createChooser(intent, "Elije tu aplicacion de correo:"));

        } else {
            Toast.makeText(actividad, "Lo siento, no hay una aplicación de correo en su movil", Toast.LENGTH_SHORT).show();
        }
    }


    // +---------------------------------------------+
    // |                                             |
    // |  COMPARTIR UN TEXTO                         |
    // |                                             |
    // +---------------------------------------------+
    public static void compartirTextoConOtrasAPPs(Activity actividad, String texto) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        actividad.startActivity(shareIntent);
    }


    // +---------------------------------------------+
    // |                                             |
    // |  COMPARTIR UN ARCHIVO CON OTRA APP          |
    // |  USA FILE PROVIDER                          |
    // |                                             |
    // +---------------------------------------------+
    public static void compartirArchivoConOOtrasApps(Activity actividad, File fileParaProvider) {
        Context con = actividad.getApplicationContext();
        String str = actividad.getApplicationContext().getPackageName() + ".fileprovider";
        Uri uri = FileProvider.getUriForFile(con, str, fileParaProvider);
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


    /**
     * insertarContacto
     * Añade un nuevo contacto a la agenda del dispositivo usando Contents Provider
     * Solo añade un nombre y un telefono
     * <p>
     * @param pNombre nombre de la persona a añadir
     * @param pNumeroTelefono numero de movil de la persona a añadir
     */
    public void insertarContacto(Context context, String pNombre, String pNumeroTelefono) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, pNombre) // nombre del contacto
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, pNumeroTelefono) // numero de telefono
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Tipo de numero de telefono
        try {
            ContentProviderResult[] res = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e("****JYOC", "Error añadiendo contacto");
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