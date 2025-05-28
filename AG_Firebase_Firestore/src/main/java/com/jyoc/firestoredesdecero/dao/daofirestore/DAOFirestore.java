package com.jyoc.firestoredesdecero.dao.daofirestore;

import android.app.Activity;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jyoc.firestoredesdecero.actividades.MainActivity;
import com.jyoc.firestoredesdecero.dao.interfaces.IDAOFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

/**
 * @author JYOC
 * @param <T> Cle objeto que se va a almacenar en Firestore
 */
public class DAOFirestore<T> {

    /**
     * ATRIBUTOS
     */
    FirebaseFirestore miFirestoreDB;

    IDAOFirestore activityOrigen;
    private String coleccion_base;
    private Class<T> classDelObjetoBase;

    /**
     * CONSTRUCTOR
     * @param activityOrigen          Actividad que invoca este DAO, debe implementar IDAOFirestore
     * @param coleccion_base    Nombre de la coleccion que se desea almanenar (como la tabla en sql)
     * @param classDelObjetoBase  .class de la clase que se almacena en la tabla
     *      Necesitamos el .class de la clase que se almacena en la bbdd
     *      No es facil obtenerlo de un generico, asi que mejor se solicita al construir el DAO
     */
    public DAOFirestore(IDAOFirestore activityOrigen, String coleccion_base, Class<T> classDelObjetoBase) {
        this.activityOrigen = activityOrigen;
        this.coleccion_base = coleccion_base;
        this.classDelObjetoBase = classDelObjetoBase;
        miFirestoreDB = FirebaseFirestore.getInstance();
        // si hay problemas de configuracion de versiones, puede que se nos pida inicializar la bbdd, si no no hace falta
         FirebaseApp.initializeApp((Activity)activityOrigen);  
    }


    //---------------------------------------------------------------------------------------------
    //----         leerTodo                      --------------------------------------------------
    //----         LEER TODOS LOS ELEMENTOS      --------------------------------------------------
    //---------------------------------------------------------------------------------------------
    public void leerTodo() {
        Query query1 = miFirestoreDB.collection(coleccion_base);

        //Query mQuery2 = mFirestoreDB.collection(COLECCION)  // consultar 100 primeros registros ordenados por id
        //        .orderBy("id");
        //Query mQuery3 = mFirestoreDB.collection(COLECCION)  // consultar 100 primeros registros ordenados por id DESCENDENTE
        //        .orderBy("id", Query.Direction.DESCENDING)
        //        .limit(100);

        realizarConsulta(query1, IDAOFirestore.ACCION_LEER_TODO);
    }

    //----------------------------------------------------
    //----         LeerElementosPorigualque       --------
    //----         LEER ELEMENTOS POR IGUAL A     --------
    //----------------------------------------------------
    public void leerElementosPorigualque(final String field, final String value) {
        Query query1 = miFirestoreDB.collection(coleccion_base)
                .whereEqualTo(field, value);
        realizarConsulta(query1, IDAOFirestore.ACCION_LEER_POR_IGUALQUE);
    }

    //-------------------------------------------------------------
    //----         leerElementosPorIn                      --------
    //----         LEER UN ELEMENTO POR UNA CONDICION IN   --------
    //-------------------------------------------------------------
    public void leerElementosPorIn(final String field, final String[] values) {
        Query query1 = miFirestoreDB.collection(coleccion_base)
                .whereIn(field, Arrays.asList(values));
        realizarConsulta(query1, IDAOFirestore.ACCION_LEER_POR_IN);
    }

    //-------------------------------------------------------------
    //----         realizarConsulta (apoyo a todos los LEER...  ---
    //-------------------------------------------------------------
    private void realizarConsulta(Query query1, int pTipoConsulta) {
        query1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<T> list = new ArrayList<T>();
                            Log.d("DAOFirestore ***>", "leyendo " + task.getResult().size() + " elementos");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //for (DocumentSnapshot document : task.getResult()) {    //  tambien vale
                                T element = document.toObject(classDelObjetoBase);
                                list.add(element);
                                Log.d("DAOFirestore ***>", "leido correctamente : " + element.toString());
                            }
                            activityOrigen.trasLeerFirebase(pTipoConsulta, list);
                        } else {
                            Log.d("DAOFirestore ***>", "Error en query : ", task.getException());
                        }
                    }
                });
    }


    //---------------------------------------------------------------------------------------------
    //----         AÑADIR UN ELEMENTO        ------------------------------------------------------
    //---------------------------------------------------------------------------------------------

    /**
     * addElement
     * @param idNuevoElemento        id con el que se añade, si el valor es NULL se deha a Firebase que lo cree 
     * @param nuevoElemento   Elemento que se añade
     */
    public void agregarElemento(String idNuevoElemento, T nuevoElemento) {
        CollectionReference collection = miFirestoreDB.collection(coleccion_base);

        if (idNuevoElemento == null) {   // si el id es null, FIRESTORE CREA UN ID para el documento 
            collection.add(nuevoElemento)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("DAOFirestore ***>", "Insercion correcta");
                            activityOrigen.trasGuardarFirebase(nuevoElemento);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("DAOFirestore ***>", "Error en query : " + e.getMessage());
                            activityOrigen.trasGuardarFirebase(null);
                        }
                    });

        } else {      // si el id no es null, se cre ael documento CON ESE ID PROPIO
            collection.document(idNuevoElemento).set(nuevoElemento)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("DAOFirestore ***>", "Insercion correcta");
                            activityOrigen.trasGuardarFirebase(nuevoElemento);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("DAOFirestore ***>", "Error en query : " + e.getMessage());
                            activityOrigen.trasGuardarFirebase(null);
                        }
                    });
        }
    }

    //----------------------------------------------------
    //----         BORRAR UN ELEMENTO            ---------
    //----------------------------------------------------
    public void borrarElemento(final String id) {
        if (miFirestoreDB == null)
            miFirestoreDB = FirebaseFirestore.getInstance();

        //// si sabemos SEGURO la reference del documento ....
        //miFirestoreDB.collection(COLECCION_BASE)
        //        .document(id)
        //        .delete();

        // si no sabemos la reference del documento ....
        Query query1 = miFirestoreDB.collection(coleccion_base)
                .whereEqualTo("id", id);
        query1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int tot= task.getResult().size();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            Log.d("DAOFirestore ***>", "borrado correcto de registro " + id);
                            Log.d("DAOFirestore ***>", "borrado correcto de " + tot+ "registros ");
                            activityOrigen.trasBorrarFirebase(IDAOFirestore.ACCION_BORRAR, tot);
                        } else {
                            Log.d("DAOFirestore ***>", "Error en borrado");
                            activityOrigen.trasBorrarFirebase(IDAOFirestore.ACCION_BORRAR, 0);
                        }
                    }
                });
    }

    //----------------------------------------------------
    //----         BORRAR TODOS LOS ELEMENTOS      ---------
    //----------------------------------------------------
    public void borrarTodo() {
        Query query1 = miFirestoreDB.collection(coleccion_base);// consultar todos los registros

        query1
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            
                            int tot= task.getResult().size();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            Log.d("DAOFirestore ***>", "borrado correcto de " + tot+ "registros ");
                            activityOrigen.trasBorrarFirebase(IDAOFirestore.ACCION_BORRAR_TODO, task.getResult().size());
                        } else {
                            Log.d("DAOFirestore ***>", "Error en borrado");
                            activityOrigen.trasBorrarFirebase(IDAOFirestore.ACCION_BORRAR_TODO,0);
                        }
                    }
                });
    }



    //----------------------------------------------------
    //----         CREAR UN ADAPTADOR SIMPLE     ---------
    //----------------------------------------------------
    public FirestoreRecyclerOptions getAdaptadorBase() {

        // La consulta firestore
        Query query = miFirestoreDB.collection(coleccion_base)
                .limitToLast(1000)
                .orderBy("id", Query.Direction.ASCENDING);


        // El adaptador firestore
        FirestoreRecyclerOptions<T> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<T>()
                .setQuery(query, classDelObjetoBase)
                .build();
        return firestoreRecyclerOptions;
    }


    //---------------------------------------------------------------------
    //----         CREAR UN ADAPTADOR CON CONDICION IGUAL QUE     ---------
    //---------------------------------------------------------------------
    public FirestoreRecyclerOptions getAdaptadorConIgualque(int valor, String... nodos) {
        FieldPath path = FieldPath.of(nodos);
        // La consulta firestore
        Query query = miFirestoreDB.collection(coleccion_base)
                .limitToLast(1000)
                .orderBy(path, Query.Direction.ASCENDING)
                .whereEqualTo(path, valor);
        Log.d("***J", query.toString());
        // El adaptador firestore
        FirestoreRecyclerOptions<T> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<T>()
                .setQuery(query, classDelObjetoBase)
                .build();
        return firestoreRecyclerOptions;
    }

    //---------------------------------------------------------------------
    //----         CREAR UN ADAPTADOR CON CONDICION MAYOR QUE     ---------
    //---------------------------------------------------------------------
    public FirestoreRecyclerOptions getAdaptadorConMayorque(int valor, String... nodos) {
        FieldPath path = FieldPath.of(nodos);
        // La consulta firestore
        Query query = miFirestoreDB.collection(coleccion_base)
                .limitToLast(1000)
                .orderBy(path, Query.Direction.ASCENDING)
                .whereGreaterThan(path, valor);

        // El adaptador firestore
        FirestoreRecyclerOptions<T> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<T>()
                .setQuery(query, classDelObjetoBase)
                .build();
        return firestoreRecyclerOptions;
    }

    //----------------------------------------------------
    //----         GENERAR CLAVE                 ---------
    //----------------------------------------------------

    /**
     * generateRandomKey
     * Crea una cadena de texto aleatoria con letras mayuscuasl minusculas y numeros, de un tamaño definido
     *
     * @param size tamaño de la cadena a crear
     * @return una cadena aleatoria
     */
    public static String generateRandomKey(int size) {
        String pattern = "ABCDEFGHIJKLMNOPQRSTUVWXUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        String result = "";
        for (int i = 0; i < size; i++) {
            result += pattern.charAt(random.nextInt(pattern.length()));
        }
        return result;
    }


}
