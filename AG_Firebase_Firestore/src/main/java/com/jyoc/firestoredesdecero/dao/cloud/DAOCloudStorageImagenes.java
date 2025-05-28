package com.jyoc.firestoredesdecero.dao.cloud;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jyoc.firestoredesdecero.dao.interfaces.IDAOCloudStorage;
import com.jyoc.firestoredesdecero.utilidades.AUXAndroidImagenes;

import androidx.annotation.NonNull;

/**
 * @author JYOC
 * @param <T>  objeto que se va a almacenar en Firestore
 */
public class DAOCloudStorageImagenes<T> {

    /**
     * ATRIBUTOS
     */
    IDAOCloudStorage mActivityOrigen;

    /**
     * CONSTRUCTOR
     * @param activityOrigen              Actividad que invoca este DAO, debe implementar IDAOFirestore
     * @param firebaseCollection    Nombre de la coleccion que se desea almanenar (como la tabla en sql)
     */
    public DAOCloudStorageImagenes(IDAOCloudStorage activityOrigen, String firebaseCollection) {
        this.mActivityOrigen = activityOrigen;
        // si hay problemas de configuracion de versiones, puede que se nos pida inicializar la bbdd, si no no hace falta
        // FirebaseApp.initializeApp((MainActivity)mActivity);  
    }


    //-----------------------------------------------
    //----         guardarImagenUri          --------
    //----    AÑADIR UNA URI A CLOUD STORE   --------
    //-----------------------------------------------
    StorageReference sref;

    /**
     * addElement
     * @param imagUri   Elemento que se añade
     * @param carpeta 
     */
    public void guardarImagenUri(Uri imagUri, String carpeta) {
        // Crear una referencia del Firebase asociado a nuestra app
        sref = FirebaseStorage.getInstance().getReference(); // please go to above link and setup firebase storage for android

        if (imagUri != null) {

            final StorageReference imageRef = sref.child(carpeta) // folder path in firebase storage
                    .child(imagUri.getLastPathSegment());

            imageRef.putFile(imagUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                            // Esto tiene informacion del fichero subido...
                            StorageMetadata storageMetadata = snapshot.getMetadata();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // show message on failure may be network/disk ?
                        }
                    });

        }
    }

    //-----------------------------------------------
    //----        guardarImagenBitmap        --------
    //----    AÑADIR UNA FOTO A CLOUD STORE  --------
    //-----------------------------------------------

    /**
     * addImagenDesdeBitmap
     * @param id        id con el que se añade, NO PUEDE SER NULL 
     *                  puede ser un string cualquiera o el mismo nombre de la imagen
     */
    public void guardarImagenBitmap(Bitmap imagenEnBmp, String carpeta, @NonNull String id) {
        // Crear una referencia del CloudStorage asociado a nuestra app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference referenciaBase = storage.getReference();

        // Crear una referencia a un fichero en concreto dentro de una carpeta
        StorageReference referenciaConPath = referenciaBase.child(carpeta+"/"+id);

        // Convertimos la imagen en un array de bytes 
        byte[] imagenEnByteArray = AUXAndroidImagenes.getByteArrayFromBitmap(imagenEnBmp);

        // Subimos la imagen 
        UploadTask uploadTask = referenciaConPath.putBytes(imagenEnByteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Este objeto tiene informacion del fichero subido...
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                mActivityOrigen.trasGuardarImagen(storageMetadata);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
            }
        });



    }

    //-----------------------------------------------
    //----         obtenerImagen                -----
    //----    OBTENER UNA IMAGEN DE CLOUD STORE -----
    //-----------------------------------------------

    public  void obtenerImagen(String carpeta, String id) {
        // Crear una referencia del Firebase asociado a nuestra app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference referenciaBase = storage.getReference();

        // O bien crear una referencia a un fichero a partir de una Google Cloud Storage URI
        //StorageReference gsRef = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

        // Descargar fichero en memoria
        StorageReference referenciaConPath = referenciaBase.child(carpeta+"/"+id);

        final long ONE_MEGABYTE = 1024 * 1024;
        referenciaConPath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = AUXAndroidImagenes.getBitmapFromByteArray(bytes);
                mActivityOrigen.trasObtenerImagen(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                
            }
        });
    }

}
