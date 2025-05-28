package com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import com.jyoc.jyocpruebacogerfotogaleria.Contacto;
import com.jyoc.jyocpruebacogerfotogaleria.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GestionContactos {

    /**
     * leerUnContacto
     * Obtiene un contacto a partir de su id
     * 
     * @param actividadLLamante    Objeto de IGestionContactos
     * @param idDelContacto  id del contacto que se desea consultar
     */
    public static void leerUnContacto(IGestionContactos actividadLLamante, String idDelContacto) {
        AppCompatActivity context = (AppCompatActivity) actividadLLamante;
        new Thread(new Runnable() {
            @SuppressLint("Range")
            @Override
            public void run() {
                String nombretodojunto = "", nombrepropio = "", segundonombrepropio = "", apellidos = "", direccion = "";
                String cumpleanos = "", calle = "", ciudad = "", pais = "", emailprincipal = "";
                Contacto contacto = null;

                // CURSOR DE UN CONTACTO POR SU ID
                // The Cursor that contains the Contact row
                Cursor cursorContactos = context.getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
                        new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, Long.valueOf(idDelContacto).toString()},
                        null);
                cursorContactos.moveToFirst();

                if (Integer.parseInt(cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    //idDeUnContacto = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts._ID));
                    //idDeUnContacto = idDelContacto;

                    int tieneTelefonosSiEsteValorEsMayorQue0 = cursorContactos.getInt(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    // CURSOR CON LOS DATOS PERSONALES DE UN CONTACTO ------------------------------------
                    Cursor cursorDeUnContacto = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
                            new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, Long.valueOf(idDelContacto).toString()},
                            null);

                    if (cursorDeUnContacto != null) {
                        if (cursorDeUnContacto.moveToFirst()) {
                            nombretodojunto = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            nombrepropio = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                            segundonombrepropio = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                            apellidos = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));

                            // CURSOR CON LOS TELEFONOS DE UN CONTACTO ------------------------------------
                            Cursor cursorTelefonos = context.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                                    new String[]{idDelContacto}, null);
                            List<Contacto.Telefono> listaTelefonos = new ArrayList<>();
                            if (cursorTelefonos != null && cursorTelefonos.getCount() > 0) {
                                while (cursorTelefonos.moveToNext()) {
                                    String iddeltelefono = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                                    //String cadaLabel = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
                                    String tipotelefono = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(),
                                            cursorTelefonos.getInt(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)), null);
                                    String cadaNumero = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Contacto.Telefono tel = new Contacto.Telefono(tipotelefono, cadaNumero);
                                    listaTelefonos.add(tel);
                                }
                                cursorTelefonos.close();
                            }

                            // coger el cumpleaños
                            ContentResolver bd = context.getContentResolver();
                            Cursor bdc = bd.query(ContactsContract.Data.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Event.DATA},
                                    ContactsContract.Data.CONTACT_ID + " = " +
                                            idDelContacto + " AND " + ContactsContract.Data.MIMETYPE + " = '" +
                                            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " +
                                            ContactsContract.CommonDataKinds.Event.TYPE + " = " +
                                            ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
                                    null, ContactsContract.Data.DISPLAY_NAME);
                            if (bdc.getCount() > 0) {
                                while (bdc.moveToNext()) {
                                    cumpleanos = bdc.getString(0);
                                }
                            }

                            // coger la direccion
                            Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
                            Cursor postal_cursor = context.getContentResolver().query(
                                    postal_uri, 
                                    null, 
                                    ContactsContract.Data.CONTACT_ID + "=" + idDelContacto.toString(), 
                                    null, 
                                    null);
                            while (postal_cursor.moveToNext()) {
                                calle = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                ciudad = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                pais = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                            }
                            postal_cursor.close();

                            // coger el email
                            ContentResolver cr = context.getContentResolver();
                            Cursor cur1 = cr.query(
                                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{idDelContacto}, 
                                    null);
                            while (cur1.moveToNext()) {
                                //to get the contact names
                                String nameQueAquiNoUso = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                emailprincipal = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            }
                            cur1.close();

                            // LA FOTO DE UN CONTACTO ------------------------------------
                            Bitmap photo = null;
                            try {
                                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(idDelContacto)));

                                if (inputStream != null) {
                                    photo = BitmapFactory.decodeStream(inputStream);
                                }
                                if (inputStream != null)
                                    inputStream.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (apellidos == null)
                                apellidos = "";
                            if (nombrepropio == null)
                                nombrepropio = "";
                            if (segundonombrepropio == null)
                                segundonombrepropio = "";
                            if (calle == null)
                                calle = "";
                            if (ciudad == null)
                                ciudad = "";
                            if (pais == null)
                                pais = "";
                            if (cumpleanos == null)
                                cumpleanos = "";
                            contacto = new Contacto(idDelContacto, nombrepropio, segundonombrepropio, apellidos, calle, ciudad, pais, cumpleanos, emailprincipal, listaTelefonos, photo);
                        }
                        cursorDeUnContacto.close();
                    }
                }
                // ======  NO OLVIDAR QUE ESTO ES UN HILO Y POR LO TANTO UNA LLAMADA ASINCRONA
                // ======  PARA SABER CUANDO ACABA LA LECTURA, LO MEJOR ES UNA LLAMADA A UN MÉTODO DE LA ACTIVIDAD LLAMANTE
                //actividadLLamante.trasleerFotoDeUnContacto(foto);
                actividadLLamante.trasleerUnContacto(contacto);
            }
        }).start();
    }

    /** **********************************************************************
     * leerTodosLosContactos
     * Lee contactos de la app de contactos del movil, SIN usar ASYNCTASK
     * Puede leer todos o aplicar un filtro
     *
     * @param actividadLLamante    Objeto de IGestionContactos
     * @param pFiltroParaContactos Filtro de busqueda, o null si no se quiere filtrar
     *                                     EL filtro es tipo LIKE de SQL, como por ejemplo "%AN%"    
     */
    public static void leerTodosLosContactos(IGestionContactos actividadLLamante, String pFiltroParaContactos) {
        AppCompatActivity context = (AppCompatActivity) actividadLLamante;
        new Thread(new Runnable() {
            @SuppressLint("Range")
            @Override
            public void run() {
                List<Contacto> listaContactos = new ArrayList<>();
                int totalContactos = 0, totalContactosLeidos = 0;
                String idDeUnContacto = "", nombretodojunto = "", nombrepropio = "", segundonombrepropio = "", apellidos = "", direccion = "";
                String cumpleanos = "", calle = "", ciudad = "", pais = "", emailprincipal = "";
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                String[] projection = new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                };

                // CURSOR CON TODOS LOS CONTACTOS -------datos personales, telefonos, cumpleaños. direccion, mail, foto ---------
                Cursor cursorContactos;
                if (pFiltroParaContactos != null && pFiltroParaContactos.length() > 0) {
                    cursorContactos = contentResolver.query(uri,
                            projection,
                            ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                            new String[]{pFiltroParaContactos},
                            ContactsContract.Contacts.DISPLAY_NAME + " ASC");
                } else {
                    cursorContactos = contentResolver.query(uri,
                            projection,
                            null,
                            null,
                            ContactsContract.Contacts.DISPLAY_NAME + " ASC");
                }
                if (cursorContactos != null && cursorContactos.getCount() > 0) {
                    totalContactos = cursorContactos.getCount();
                    while (cursorContactos.moveToNext()) {


                        if (Integer.parseInt(cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                            idDeUnContacto = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts._ID));

                            int tieneTelefonosSiEsteValorEsMayorQue0 = cursorContactos.getInt(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                            // CURSOR CON LOS DATOS PERSONALES DE UN CONTACTO ------------------------------------
                            Cursor cursorDeUnContacto = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                                    null,
                                    ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
                                    new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, Long.valueOf(idDeUnContacto).toString()},
                                    null);

                            if (cursorDeUnContacto != null) {
                                if (cursorDeUnContacto.moveToFirst()) {
                                    nombretodojunto = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                    nombrepropio = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                                    segundonombrepropio = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                                    apellidos = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                                    // NO VA       String emailprincipal = cursorDeUnContacto.getString(cursorDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                    // CURSOR CON LOS TELEFONOS DE UN CONTACTO ------------------------------------
                                    Cursor cursorTelefonos = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{idDeUnContacto}, null);
                                    List<Contacto.Telefono> listaTelefonos = new ArrayList<>();
                                    if (cursorTelefonos != null && cursorTelefonos.getCount() > 0) {
                                        while (cursorTelefonos.moveToNext()) {
                                            String iddeltelefono = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                                            //String cadaLabel = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
                                            String tipotelefono = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(),
                                                    cursorTelefonos.getInt(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)), null);
                                            String cadaNumero = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                            Contacto.Telefono tel = new Contacto.Telefono(tipotelefono, cadaNumero);
                                            listaTelefonos.add(tel);
                                        }
                                        cursorTelefonos.close();
                                    }

                                    // coger el cumpleaños
                                    ContentResolver bd = context.getContentResolver();
                                    Cursor bdc = bd.query(android.provider.ContactsContract.Data.CONTENT_URI,
                                            new String[]{ContactsContract.CommonDataKinds.Event.DATA},
                                            android.provider.ContactsContract.Data.CONTACT_ID + " = " +
                                                    idDeUnContacto + " AND " + ContactsContract.Data.MIMETYPE + " = '" +
                                                    ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " +
                                                    ContactsContract.CommonDataKinds.Event.TYPE + " = " +
                                                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
                                            null, android.provider.ContactsContract.Data.DISPLAY_NAME);
                                    if (bdc.getCount() > 0) {
                                        while (bdc.moveToNext()) {
                                            cumpleanos = bdc.getString(0);
                                            // now "id" is the user's unique ID, "name" is his full name and "birthday" is the date and time of his birth
                                        }
                                    }

                                    // coger la direccion
                                    Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
                                    Cursor postal_cursor = context.getContentResolver().query(postal_uri, null, ContactsContract.Data.CONTACT_ID + "=" + idDeUnContacto.toString(), null, null);
                                    while (postal_cursor.moveToNext()) {
                                        calle = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                        ciudad = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                        pais = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                                    }
                                    postal_cursor.close();

                                    // coger el email
                                    ContentResolver cr = context.getContentResolver();
                                    Cursor cur1 = cr.query(
                                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                            new String[]{idDeUnContacto}, null);
                                    while (cur1.moveToNext()) {
                                        //to get the contact names
                                        String nameQueAquiNoUso = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                        emailprincipal = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                        Log.e("Email", emailprincipal);
                                    }
                                    cur1.close();

                                    // LA FOTO DE UN CONTACTO ------------------------------------
                                    Bitmap photo = null;
                                    try {
                                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(idDeUnContacto)));

                                        if (inputStream != null) {
                                            photo = BitmapFactory.decodeStream(inputStream);
                                        }
                                        if (inputStream != null)
                                            inputStream.close();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if (apellidos == null)
                                        apellidos = "";
                                    if (nombrepropio == null)
                                        nombrepropio = "";
                                    if (segundonombrepropio == null)
                                        segundonombrepropio = "";
                                    if (calle == null)
                                        calle = "";
                                    if (ciudad == null)
                                        ciudad = "";
                                    if (pais == null)
                                        pais = "";
                                    if (cumpleanos == null)
                                        cumpleanos = "";

                                    Contacto c = new Contacto(idDeUnContacto, nombrepropio, segundonombrepropio, apellidos, calle, ciudad, pais, cumpleanos, emailprincipal, listaTelefonos, photo);
                                    Log.d("***JYOC***", c.toString());
                                    listaContactos.add(c);
                                }
                                cursorDeUnContacto.close();
                            }
                        }
                        totalContactosLeidos++;
                    }
                    cursorContactos.close();
                } else {
                    totalContactos = 0;
                }

                // ======  NO OLVIDAR QUE ESTO ES UN HILO Y POR LO TANTO UNA LLAMADA ASINCRONA
                // ======  PARA SABER CUANDO ACABA LA LECTURA, LO MEJOR ES UNA LLAMADA A UN MÉTODO DE LA ACTIVIDAD LLAMANTE
                actividadLLamante.trasleertodosloscontactos(listaContactos);
                // ===============================================


            }
        }).start();
    }

    /** **********************************************************************
     * agregarContactoEnAPPContactos 
     * Crea un contacto en la app de contactos del movil
     *
     * @param context    Objeto de Activity desde donde se llama a este método
     * @param pNombre           int del id del contacto que se desea editar 
     * @param pNumeroTelefono           int del id del contacto que se desea editar 
     */
    public static void agregarContactoEnAPPContactos(@NonNull Activity context, String pNombre, String pNumeroTelefono) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
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
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, pNumeroTelefono) // numero de telefono
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Tipo de numero de telefono
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e("****JYOC", "Error añadiendo contacto");
        }
    }


    /** **********************************************************************
     * editarContactoEnAppContactos  (VERSION CORTA)
     * Abre la app de contactos del móvil, en un determinado contacto, para poder editarlo
     *
     * @param actividad    Objeto de Activity desde donde se llama a este método
     * @param pId          int del Id del contacto que se desea editar 
     */
    public static void editarContactoEnAppContactos(Activity actividad, int pId) {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = actividad.getContentResolver().query(uri, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        String id1 = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        long idContact = -1;
        int id2;

        boolean sigue = true;
        while (cursor.moveToNext() && sigue == true) {
            id2 = cursor.getColumnIndex(id1);
            idContact = cursor.getLong(id2);
            if (idContact == pId) {
                sigue = false;
            }
        }

        if (sigue == false) {
            Intent i = new Intent(Intent.ACTION_EDIT);
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, idContact);
            i.setData(contactUri);
            /*
            En Android 4.0 (API nivel 14) y versiones posteriores, un problema en la App de Contactos 
            lleva a una navegación incorrecta. Cuando tu app envía un intent a la App de Contactos, y los usuarios editan 
            y guardan un contacto, al hacer clic en Atrás, verán la pantalla de la lista de contactos. 
            Para volver a tu app, deberán hacer clic en Recientes y seleccionarla.
        
            Para solucionar este problema en Android 4.0.3 (API nivel 15) y versiones posteriores, 
            se agrega la clave de datos extendidos finishActivityOnSaveCompleted al intent, 
            con un valor de true. 
            Las versiones de Android anteriores a la 4.0 aceptan esta clave, pero no tiene ningún efecto. 
            Para configurar los datos extendidos se hace asi;
             */
            i.putExtra("finishActivityOnSaveCompleted", true);
            actividad.startActivity(i);
        }

    }


    /** **********************************************************************
     * editarContactoEnAppContactos  (VERSION LARGA)
     * Abre la app de contactos del móvil, en un determinado contacto, para poder editarlo
     *
     * @param actividad    Objeto de Activity desde donde se llama a este método
     * @param pId          String del id del contacto que se desea editar 
     */
    public static void editarContactoEnAppContactos(Activity actividad, String pId) {
        // The index of the lookup key column in the cursor
        int lookupKeyIndex;
        // The index of the contact's _ID value
        int idIndex;
        // The lookup key from the Cursor
        String mCurrentLookupKey;
        // The _ID value from the Cursor
        long currentId;
        // A content URI pointing to the contact
        Uri selectedContactUri;

        // CURSOR DE UN CONTACTO POR SU ID
        // The Cursor that contains the Contact row
        Cursor mCursor = actividad.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
                new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, Long.valueOf(pId).toString()},
                null);
        mCursor.moveToFirst();

        /*
         * Once the user has selected a contact to edit,
         * this gets the contact's lookup key and _ID values from the
         * cursor and creates the necessary URI.
         */
        // Gets the lookup key column index
        lookupKeyIndex = mCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
        // Gets the lookup key value
        mCurrentLookupKey = mCursor.getString(lookupKeyIndex);
        // Gets the _ID column index
        idIndex = mCursor.getColumnIndex(ContactsContract.Contacts._ID);
        currentId = mCursor.getLong(idIndex);
        selectedContactUri = ContactsContract.Contacts.getLookupUri(currentId, mCurrentLookupKey);

        // Creates a new Intent to edit a contact
        Intent editIntent = new Intent(Intent.ACTION_EDIT);
        /*
         * Sets the contact URI to edit, and the data type that the
         * Intent must match
         */
        editIntent.setDataAndType(selectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                
        /*
        En Android 4.0 (API nivel 14) y versiones posteriores, un problema en la App de Contactos 
        lleva a una navegación incorrecta. Cuando tu app envía un intent a la App de Contactos, y los usuarios editan 
        y guardan un contacto, al hacer clic en Atrás, verán la pantalla de la lista de contactos. 
        Para volver a tu app, deberán hacer clic en Recientes y seleccionarla.
    
        Para solucionar este problema en Android 4.0.3 (API nivel 15) y versiones posteriores, 
        se agrega la clave de datos extendidos finishActivityOnSaveCompleted al intent, 
        con un valor de true. 
        Las versiones de Android anteriores a la 4.0 aceptan esta clave, pero no tiene ningún efecto. 
        Para configurar los datos extendidos se hace asi;
         */
        editIntent.putExtra("finishActivityOnSaveCompleted", true);
        /*
        CUIDADO QUE AL VOLVER DE LA APP DE CONTACTOS NO SE ACTIVA EL onActivitResult, hay que controlar la vuelta 
        con el método ONRESTART() 
         */
        
        /*
        lanzamos el intent
         */
        actividad.startActivity(editIntent);

    }

    /** **********************************************************************
     * editarContactoEnAppContactos  (VERSION LARGA)
     * Abre la app de contactos del móvil, en un determinado contacto, para poder editarlo
     *
     * @param actividadLLamante    Objeto de Activity desde donde se llama a este método
     * @param pId        String del id del contacto que se desea consultar 
     */
    @SuppressLint("Range")
    public static void obtenerLosTelefonosDeUnContacto(IGestionContactos actividadLLamante, String pId) {
        AppCompatActivity context = (AppCompatActivity) actividadLLamante;
        new Thread(new Runnable() {
            @SuppressLint("Range")
            @Override
            public void run() {
                ArrayList<String> todoslostelefonos = new ArrayList<>();

                // CURSOR CON LOS TELEFONOS DE UN CONTACTO ------------------------------------
                Cursor cursorTelefonosDeUnContacto = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{pId},
                        null);

                if (cursorTelefonosDeUnContacto != null && cursorTelefonosDeUnContacto.getCount() > 0) {
                    while (cursorTelefonosDeUnContacto.moveToNext()) {
                        String iddeltelefono = cursorTelefonosDeUnContacto.getString(cursorTelefonosDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                        String tipotelefono = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(),
                                cursorTelefonosDeUnContacto.getInt(cursorTelefonosDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)), null);
                        String untelefono = cursorTelefonosDeUnContacto.getString(cursorTelefonosDeUnContacto.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        todoslostelefonos.add(untelefono);
                        //return untelefono;
                    }
                    cursorTelefonosDeUnContacto.close();
                }

                // ======  NO OLVIDAR QUE ESTO ES UN HILO Y POR LO TANTO UNA LLAMADA ASINCRONA
                // ======  PARA SABER CUANDO ACABA LA LECTURA, LO MEJOR ES UNA LLAMADA A UN MÉTODO DE LA ACTIVIDAD LLAMANTE
                actividadLLamante.trasleertodoslosTelefonos(todoslostelefonos);
            }
        }).start();
    }

    public static void obtenerCumpleanosDeUnContactoPorSuId(IGestionContactos actividadLLamante, String idDelContacto) {
        AppCompatActivity context = (AppCompatActivity) actividadLLamante;
        new Thread(new Runnable() {
            @SuppressLint("Range")
            @Override
            public void run() {
                String cumpleanos = "";
                ContentResolver bd = context.getContentResolver();
                Cursor bdc = bd.query(android.provider.ContactsContract.Data.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Event.DATA},
                        android.provider.ContactsContract.Data.CONTACT_ID + " = " +
                                idDelContacto + " AND " + ContactsContract.Data.MIMETYPE + " = '" +
                                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " +
                                ContactsContract.CommonDataKinds.Event.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
                        null, android.provider.ContactsContract.Data.DISPLAY_NAME);
                if (bdc.getCount() > 0) {
                    while (bdc.moveToNext()) {
                        cumpleanos = bdc.getString(0);
                    }
                }
                if (cumpleanos == null) {
                    cumpleanos = "";
                }

                // ======  NO OLVIDAR QUE ESTO ES UN HILO Y POR LO TANTO UNA LLAMADA ASINCRONA
                // ======  PARA SABER CUANDO ACABA LA LECTURA, LO MEJOR ES UNA LLAMADA A UN MÉTODO DE LA ACTIVIDAD LLAMANTE
                actividadLLamante.trasleerCumpleañosDeUnContacto(cumpleanos);

            }
        }).start();
    }

    /**
     * obtenerFotoDeUnContactoPorSuId
     * Obtiene el bitmap con la foto de un contacto
     * @param actividadLLamante Objeto de IGestionContactos
     * @param idDelContacto  id del contacto cuya foto queremos recoger
     */
    public static void obtenerFotoDeUnContactoPorSuId(IGestionContactos actividadLLamante, String idDelContacto) {
        AppCompatActivity context = (AppCompatActivity) actividadLLamante;
        new Thread(new Runnable() {
            @SuppressLint("Range")
            @Override
            public void run() {
                Bitmap foto = null;
                try {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(idDelContacto));
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                            context.getContentResolver(),
                            uri);
                    if (inputStream != null) {
                        foto = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // ======  NO OLVIDAR QUE ESTO ES UN HILO Y POR LO TANTO UNA LLAMADA ASINCRONA
                // ======  PARA SABER CUANDO ACABA LA LECTURA, LO MEJOR ES UNA LLAMADA A UN MÉTODO DE LA ACTIVIDAD LLAMANTE
                actividadLLamante.trasleerFotoDeUnContacto(foto);
            }
        }).start();
    }

}
    
    
