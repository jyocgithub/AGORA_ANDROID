package com.jyoc.ag_sqlite_room.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contacto.class}, version = 1)
public  abstract class DBContactos extends RoomDatabase {
    public abstract IContactoDAO contactoDao();

    private static  DBContactos contactoDBSingleton = null;

    public static DBContactos getDatabase(Context context) {
        if (contactoDBSingleton == null) {
            contactoDBSingleton = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DBContactos.class, "Contactos_db")   // nombre del fichero de la bbdd
                    .allowMainThreadQueries()
                    // !! CUIDADO !!
                    // ESTO ASEGURA QUE SI SE CAMBIA DE VERSION LA BD, SE BORRA LA VERSION ANTERIOR
                    // Y NO HACE FALTA UN UPGRADE, HAY QUE ELIMINARLO AL PASAR A PRODUCCION
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return contactoDBSingleton;
    }

}

