package com.jyoc.firestoredesdecero.dao.interfaces;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageMetadata;

public interface IDAOCloudStorage<T> {
    
    void trasObtenerImagen(Bitmap bitmap);

    // el parametro de este method tiene informacion de la imagen subida, por ejemplo:
    //   storageMetadata.getCreationTimeMillis()
    //   storageMetadata.getSizeBytes();
    //   storageMetadata.getName();
    //   storageMetadata.getPath();
    //   storageMetadata.getReference();
    void trasGuardarImagen( StorageMetadata storageMetadata);

}
