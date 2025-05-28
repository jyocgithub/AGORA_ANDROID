package com.jyoc.ag_sqlite_room.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


/*
    Puede que solo sean necesarias estas dependencias
        implementation 'androidx.room:room-runtime:2.4.1'
        annotationProcessor 'androidx.room:room-compiler:2.4.1'
   
     dependencies{
           def room_version = "2.4.2"

           implementation "androidx.room:room-runtime:2.4.1"
           annotationProcessor "androidx.room:room-compiler:2.4.1"

           // optional - RxJava2 support for Room
           implementation "androidx.room:room-rxjava2:2.4.1"

           // optional - RxJava3 support for Room
           implementation "androidx.room:room-rxjava3:2.4.1"

           // optional - Guava support for Room, including Optional and ListenableFuture
           implementation "androidx.room:room-guava:2.4.1"

           // optional - Test helpers
           testImplementation "androidx.room:room-testing:2.4.1"

           // optional - Paging 3 Integration
           implementation "androidx.room:room-paging:2.5.0-alpha01"
    }
   
*/

@Dao
public interface IContactoDAO {

    // _______ INSERT ________
    
    @Insert
    public void insertar(Contacto contacto);
    
    @Insert
    void insertarDos(Contacto c1, Contacto c2);
    
    @Insert
    void insertarMuchos(Contacto... contactos);

    @Insert
    void insertarLista(List<Contacto> listacontactos);
    
    // _______ SELECT  ________

    @Query("SELECT * FROM CONTACTOS")
    public List<Contacto> selectTodos();

    @Query("SELECT * FROM CONTACTOS WHERE id = :id")
    public Contacto selectPorId(int id);
    
    
    @Query("SELECT * FROM CONTACTOS WHERE id IN (:contactosIds)")
    List<Contacto> selectPorIdEn(int[] contactosIds);

    @Query("SELECT * FROM CONTACTOS WHERE nombre LIKE :pnombre AND apellidos LIKE :papellidos LIMIT 1")
    Contacto selectPorNombreYApellidos(String pnombre, String papellidos);
    
    // _______ UPDATE  ________
    @Update
    void modificar(Contacto contacto);

    // la anotacion es Query por que realmente estamos haciendo una query
    @Query("UPDATE CONTACTOS SET apellidos = :ape WHERE nombre LIKE :nom ")
    void modificarApellidoPorNombre(String ape, String nom);
    
    // podria tener mas, como los de insert y select
    
    
    // _______ DELETE ________

    @Delete
    void borrar(Contacto contacto);
    

    @Delete
    public void borrarContactos(Contacto... contactos);

    // la anotacion es Query por que realmente estamos haciendo una query
    @Query("DELETE FROM CONTACTOS")
    void borrarTodos();

}
