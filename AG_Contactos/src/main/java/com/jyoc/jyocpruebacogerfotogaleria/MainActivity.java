package com.jyoc.jyocpruebacogerfotogaleria;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask.GestionContactos;
import com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask.GestionPermisos;
import com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask.IGestionContactos;
import com.jyoc.jyocpruebacogerfotogaleria.SIN_AsyncTask.IGestionPermisos;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements IGestionPermisos, IGestionContactos {
    ImageView ivPrueba;
    public final int SELECT_DOCUMENTO = 654;
    public ArrayList<Contacto> listaContactos = new ArrayList<>();
    MainActivity mainActivity;
    TextView txtprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] ARR_PERMISOS = {
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        GestionPermisos.pedirVariosPermisos(this, ARR_PERMISOS);
    }

    @Override
    public void accionesConPermisos() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivity = this;
        ivPrueba = findViewById(R.id.ivPrueba);
        txtprogress = findViewById(R.id.txt_progress);

        findViewById(R.id.button_leer_un_contacto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.leerUnContacto(mainActivity, "2");
            }
        });
        findViewById(R.id.bt_ObtenerTelefonos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.obtenerLosTelefonosDeUnContacto(MainActivity.this, "2");
            }
        });

        findViewById(R.id.button_leertodoslosconrtactos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtProgress = findViewById(R.id.txt_progress);
                //new LeerContactosAsyncTask(MainActivity.this, listaContactos, txtProgress).execute("");
                GestionContactos.leerTodosLosContactos(MainActivity.this, null);
                //GestionContactos.leerTodosLosContactos(MainActivity.this,"%AN%");
            }
        });
        findViewById(R.id.bt_EditarContarcto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // version ligera
                //GestionContactos.editarContactoEnAppContactos(mainActivity, 2);
                // version larga
                GestionContactos.editarContactoEnAppContactos(mainActivity, "2");
            }
        });
        findViewById(R.id.button_agregarcontacto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.agregarContactoEnAPPContactos(MainActivity.this, "JUAN", "654654654");
            }
        });
        findViewById(R.id.bt_ObtenerTelefonos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.obtenerLosTelefonosDeUnContacto(MainActivity.this, "2");
            }
        });
        findViewById(R.id.bt_ObtenerCumpleaños).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.obtenerCumpleanosDeUnContactoPorSuId(MainActivity.this, "2");
            }
        });
        findViewById(R.id.bt_ObtenerFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GestionContactos.obtenerFotoDeUnContactoPorSuId(MainActivity.this, "2");
            }
        });
    }

    @Override
    public void accionesSinPermisos() {

    }


    public void trasleertodosloscontactos(List<Contacto> listaContactos) {
        // CUIDADO, ESTE METODO ES LLAMADO DESDE UN HILO EXTERNO
        // NO PUEDE ESCRIBIR EN LA UI SIN UN HIULO PROPIO
        for (Contacto c : listaContactos) {
            Log.d("***JYOC***", c.toString());
        }

    }

    @Override
    public void trasleerUnContacto(Contacto c) {
        Log.d("***JYOC***", c.toString());
        
    }

    @Override
    public void trasleertodoslosTelefonos(List<String> listaTelefonos) {
        // CUIDADO, ESTE METODO ES LLAMADO DESDE UN HILO EXTERNO
        // NO PUEDE ESCRIBIR EN LA UI SIN UN HIULO PROPIO
        for (String s : listaTelefonos) {
            Log.d("***JYOC***", s);
        }
    }

    @Override
    public void trasleerFotoDeUnContacto(Bitmap foto) {
        // CUIDADO, ESTE METODO ES LLAMADO DESDE UN HILO EXTERNO
        // NO PUEDE ESCRIBIR EN LA UI SIN UN HIULO PROPIO
        runOnUiThread(new Runnable() {
            public void run() {
                ivPrueba.setImageBitmap(foto);
            }
        });
    }

    @Override
    public void trasleerCumpleañosDeUnContacto(String cumpleanos) {
        // CUIDADO, ESTE METODO ES LLAMADO DESDE UN HILO EXTERNO
        // NO PUEDE ESCRIBIR EN LA UI SIN UN HIULO PROPIO
        Log.d("***JYOC***", cumpleanos);

    }

    // SOLO SE USA EN LA VERSION CON ASYNCTASK
    public void mostrarContactosTrasHiloLectura() {
        ivPrueba.setImageBitmap(listaContactos.get(1).getFoto());
    }


}

//
//class Contacto {
//    private String id, nombre, telefono, etiqueta;
//    private BitMap foto;
//
//    Contacto(String id, String nombre, String telefono, String etiqueta) {
//        this.id = id;
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.etiqueta = etiqueta;
//    }
//
//    @Override
//    public String toString() {
//        return nombre + " | " + etiqueta + " : " + telefono;
//    }
//}


//public Uri getPhotoUri() {
//    try {
//        Cursor cur = this.ctx.getContentResolver().query(
//                ContactsContract.Data.CONTENT_URI,
//                null,
//                ContactsContract.Data.CONTACT_ID + "=" + this.getId() + " AND "
//                        + ContactsContract.Data.MIMETYPE + "='"
//                        + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
//                null);
//        if (cur != null) {
//            if (!cur.moveToFirst()) {
//                return null; // no photo
//            }
//        } else {
//            return null; // error in cursor process
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//    }
//    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
//            .parseLong(getId()));
//    return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
//}
