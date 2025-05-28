package com.jyoc.ag_sqlite_room.room;


import android.graphics.Bitmap;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//--- Creamos entidad CONTACTO
// tablename si la tabla no queremos que se llame como la clase-entity
// si queremos que se ignore algun atributo, que se este heredando por ejemplo, usar: 
//@Entity(ignoredColumns = "apodo")
@Entity(tableName = "CONTACTOS")
public class Contacto {

    // para indicar que este atributo es la clave primaria
    // autogenerate es opcional, para hacer el indice autoincremental
    @PrimaryKey(autoGenerate = true)
    // si son atributos publicos no necesitan gettes ni setters
    public int id;

    // asi indico que el atributo sera un campo obligatorio, 
    @NonNull
    // asi indico que el atributo sera un campo en la bbdd, 
    // puede incluso en la bbdd tener un nombre distinto al atributo
    @ColumnInfo(name = "nombre")
    public String nombre;
    
    @ColumnInfo(name = "apellidos")
    public String apellidos;

    @ColumnInfo(name = "edad")
    public int edad;

    @ColumnInfo(name = "fechanac")
    public String fechanac;
    
    @ColumnInfo(name = "email")
    public String email;
    
    // este atributo se ignora en la base de datos 
    @Ignore  
    Bitmap picture;

    public Contacto() {
    }
    
    // constructor SIN CLAVE PRIMARIA, no se necesita pues al insertar un objeto se añade automaticamente
    // al haber definido clave autoincremental
    public Contacto(String nombre, String apellidos, int edad, String fechanac, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.fechanac = fechanac;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", fechanac=" + fechanac +
                ", email='" + email + '\'' +
                '}';
    }
}
