package com.jyoc.firestoredesdecero.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageMetadata;
import com.jyoc.firestoredesdecero.R;
import com.jyoc.firestoredesdecero.dao.daofirestore.AdaptadorFirestore;
import com.jyoc.firestoredesdecero.dao.daofirestore.DAOFirestore;
import com.jyoc.firestoredesdecero.dao.interfaces.IDAOCloudStorage;
import com.jyoc.firestoredesdecero.dao.interfaces.IDAOFirestore;
import com.jyoc.firestoredesdecero.pojo.Barco;
import com.jyoc.firestoredesdecero.pojo.Usuario;

import java.util.List;

public class UsandoFirestoreActivity extends AppCompatActivity  implements IDAOFirestore, IDAOCloudStorage {


    AdaptadorFirestore adaptadorFirebase;
    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("BBDDPORTAAVIONES");
    FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
    DAOFirestore<Barco> daoFirestoreBarcos;
    DAOFirestore<Usuario> daoFirestoreUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_firestore);

        daoFirestoreBarcos = new DAOFirestore<>(this, "BARCOS", Barco.class);
        daoFirestoreUsuarios = new DAOFirestore<>(this, "USUARIOS", Usuario.class);

        // el recycler view
        RecyclerView recyclerView = findViewById(R.id.rvListaFirestore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // el adaptador de barcos

        FirestoreRecyclerOptions<Barco> firebaseoptions = daoFirestoreBarcos.getAdaptadorBase();
        FirestoreRecyclerOptions<Barco> firebaseoptions2 = daoFirestoreBarcos.getAdaptadorConMayorque(20,"eslora");
        FirestoreRecyclerOptions<Barco> firebaseoptions3 = daoFirestoreBarcos.getAdaptadorConIgualque(45, "capitan", "edad");
       
       
        adaptadorFirebase = new AdaptadorFirestore(this, firebaseoptions);
        recyclerView.setAdapter(adaptadorFirebase);


        // ------------------------------ PRUEBAS
        // --- la lectura de usuarios
        daoFirestoreUsuarios.leerTodo();
        // --- añadir varios tipos de cosas 
        Usuario e = new Usuario("cniu9asn", "Luisa", "12345", "");
        daoFirestoreUsuarios.agregarElemento(e.getId(), e);
        Barco b = new Barco("55", "Catamaran", 46, 12, false, new Barco.Capitan("PELUKAS", 55));
        daoFirestoreBarcos.agregarElemento(b.getId(), b);
        // --- borrar algunas cosas        
        //Usuario e = new Usuario("cniu9asn", "Luisa", "12345", "");
        //daoFirestoreUsuarios.borrarElemento(e.getId());
        //Barco b = new Barco("55", "Catamaran", 46, 12, false, new Barco.Capitan("PELUKAS", 55));
        //daoFirestoreBarcos.borrarElemento(b.getId());

    }


    @Override
    protected void onStart() {
        super.onStart();
        adaptadorFirebase.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorFirebase.stopListening();
    }


    //*******************************************************************************************
    //********       acciones TRAS FIREBASE     *************************************************
    //*******************************************************************************************

    @Override
    public void trasLeerFirebase(int PTipoAccion, List pListaLeida) {

        if (pListaLeida == null || pListaLeida.size() == 0) {

        } else {
            switch (PTipoAccion) { // ANALIZAMOS QUE TIPO DE LECTURA SE HA REALIZADO
                case IDAOFirestore.ACCION_LEER_TODO:
                    if (pListaLeida.get(0) instanceof Usuario) {
                        for (Object o : pListaLeida) {
                            Usuario u = (Usuario) o;
                            Log.d("DAOFirestore ***>", "Leido "+ u.getNombre());
                        }
                    }
                    break;
                case IDAOFirestore.ACCION_LEER_POR_IGUALQUE:

                    break;
                case IDAOFirestore.ACCION_LEER_POR_MAYORQUE:

                    break;
                case IDAOFirestore.ACCION_LEER_POR_MENORQUE:

                    break;
            }

        }

    }

    @Override
    public void trasBorrarFirebase(int PTipoAccion, int pNumeroElementosBorrados) {

        switch (PTipoAccion) {        // ANALIZAMOS QUE TIPO DE BORRADO SE HA REALIZADO
            case IDAOFirestore.ACCION_BORRAR:
                Log.d("DAOFirestore ***>", "se ha borrado  "+ pNumeroElementosBorrados + " elementos");
                break;
            case IDAOFirestore.ACCION_BORRAR_TODO:
                Log.d("DAOFirestore ***>", "se ha borrado  "+ pNumeroElementosBorrados + " elementos");
                break;
        }
    }

    @Override
    public void trasGuardarFirebase(Object c) {
        if (c == null) {
            Log.d("DAOFirestore ***>", "elemento no creado  ");

            return;
        }
        if (c instanceof Barco) {
            Log.d("DAOFirestore ***>", "barco creado : " + ((Barco) c).getTipo());
        }
        if (c instanceof Usuario) {
            Log.d("DAOFirestore ***>", "usuario creado : " + ((Usuario) c).getNombre());
        }

    }

    //*******************************************************************************************
    //********       acciones TRAS CLOUDSTORAGE *************************************************
    //*******************************************************************************************

    @Override
    public void trasObtenerImagen(Bitmap bitmap) {

    }

    @Override
    public void trasGuardarImagen(StorageMetadata storageMetadata) {

        
        
    }

    //public void añadirBarcos(){
    //
    //    Barco b = new Barco("11","Torpedera",13,6,false, new Barco.Capitan("PACO", 34));
    //    Barco c = new Barco("22","Yate",8,2,true, new Barco.Capitan("LUIS", 45));
    //    Barco d = new Barco("33","Lancha",23,9,false, new Barco.Capitan("ANA", 19));
    //    Barco e = new Barco("55","Fragata",43,19,false, new Barco.Capitan("MARCOS", 71));
    //    
    //    
    //    daoFirestoreBarcos.agregarElemento(b.getId(),b);
    //    daoFirestoreBarcos.agregarElemento(c.getId(),c);
    //    daoFirestoreBarcos.agregarElemento(d.getId(),d);
    //    daoFirestoreBarcos.agregarElemento(e.getId(),e);
    //}

    //public void añadirUsuarios() {
    //
    //    Usuario b = new Usuario("aiuhsaac", "Paco", "12345", "");
    //    Usuario c = new Usuario("he487ycb", "Ana", "12345", "");
    //    Usuario d = new Usuario("8nqiuhas", "Eva", "12345", "");
    //    Usuario e = new Usuario("cniu9asn", "Luisa", "12345", "");
    //
    //    daoFirestoreUsuarios.agregarElemento(b.getId(), b);
    //    daoFirestoreUsuarios.agregarElemento(c.getId(), c);
    //    daoFirestoreUsuarios.agregarElemento(d.getId(), d);
    //    daoFirestoreUsuarios.agregarElemento(e.getId(), e);
    //}


}