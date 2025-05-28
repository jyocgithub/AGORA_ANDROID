package com.jyoc.jga_sqlite_conimagenes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La forma normal de trabajar con BBDD sera creando clases que deriven de SQLiteOpenHelper.
 * SQLiteOpenHelper tiene sólo un constructor, que no es necesario sobrescribir,
 * y dos métodos abstractos a sobrescribir
 * - onCreate() para crear nuestra base de datos
 * - onUpgrade() para actualizar una bbdd existete con una nueva version
 */
public class MiClaseSqlite extends SQLiteOpenHelper {

    // ***********************************************************  ATRIBUTOS
    // **************************************************************************************
    // Objeto de la bbdd que se usa en todos los metodos de esta clase
    SQLiteDatabase objetoBBDD;

    // ***********************************************************  CONSTRUCTOR
    // **************************************************************************************
    // Constructor, que no se toca
    public MiClaseSqlite(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);

    }

    // ***********************************************************  METODOS A SOBRESCRIBIR
    // **************************************************************************************

    /**
     * onCreate()
     * Se ejecuta automáticamente cuando sea necesaria la creación de la base de datos por que ésta no exista.
     * Actividades típicas que se hacen aqui: crear todas las tablas e indices y
     * la inserción de los datos de carga iniciales
     * Para la gestion de la tabla usamos por ahora el metodo execSQL() de la API de SQLite
     * Este método simplemente ejecuta el código SQL pasado como parámetro.
     *
     * @param db Como parámetro el metodo recibe el nombre de la bbdd que esta procesandose
     *           y sobre la cual podemos llamar al metodo execSql();
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sentencia SQL para crear la tabla de Jugadores
        String sql = "CREATE TABLE JugadoresDB (idJugador INTEGER, nombre TEXT, puntos INTEGER, foto BLOB)";
        //Ejecutar la sentencia SQL de creación de la tabla
        db.execSQL(sql);
    }

    /**
     * OnUpdate()  se ejecuta cuando se adviete que existe una nueva version de la base de datos
     * Las BBDD en SQLite se almacenan con un nuemero de version.
     * Cuando una bbdd cambia (crece en campos, en tablas, ect) y necesita una nueva version
     * se ejecuta este metodo, que normalmente realiza tres pasos:
     * - copia la base de datos antigua,
     * - procesa la migracion de la estructura del modelo antiguo al nuevo
     * - actualiza los datos con el nuevo formato y deja este como el activo
     * onUpgrade() cuando se intente abrir una versión  de la base de datos que aún no exista,
     * ejecuta el código interno.
     * Para ello, como parámetros recibe :
     *
     * @param db              nombre de la bbdd
     * @param versionAnterior la versión actual de la base de datos en el sistema,
     * @param versionNueva    la nueva versión a la que se quiere convertir.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

        String sql = "CREATE TABLE JugadoresDB2 (idJugador INTEGER, nombre TEXT, puntos INTEGER, partidasganadas INTEGER)";
        db.execSQL(sql);
        sql = "INSERT INTO JugadoresDB2 (idJugador , nombre , puntos )  SELECT idJugador , nombre , puntos  FROM JugadoresDB";
        db.execSQL(sql);
        sql = "DROP TABLE JugadoresDB";
        db.execSQL(sql);
        sql = "ALTER TABLE JugadoresDB2 RENAME TO JugadoresDB";
        db.execSQL(sql);

    }

    // ***********************************************************  METODOS DE CONTROL DE BBDD
    // **************************************************************************************

    /**
     * conectar()
     * Obtiene la conexion de la base de datos y la abre
     */
    public void conectar() {
        objetoBBDD = getWritableDatabase();
    }

    /**
     * desconectar()
     * Cierra el objeto de la base de datos
     */
    public void desconectar() {
        objetoBBDD.close();
    }


    // ***********************************************************  METODOS DE ACCESO A DATOS
    // **************************************************************************************

    /**
     * guardarJugador
     *
     * @param j el juegador que se almacena en la bbdd
     */
    public void guardarJugador(Jugador j) {
        conectar();
        
        // convertir el BitMap en bitearray
        byte[] fotoenbytes = bitmap_to_ByteArray(j.getFoto());
        
        // version SIN parametros
        //String sql = "INSERT INTO JugadoresDB (idJugador , nombre , puntos , foto ) VALUES (" + j.getIdJugador() + ",'" + j.getNombre() + "'," + j.getPuntos()  + "'," + fotoenbytes + ")";
        //objetoBBDD.execSQL(sql);

        // version CON parametros
        String sql2 = "INSERT INTO JugadoresDB (idJugador , nombre , puntos , foto) VALUES (?,?,?,?)";

        Object[] parametrosdelsql = new Object[4];
        parametrosdelsql[0] = j.getIdJugador() + "";
        parametrosdelsql[1] = "'" + j.getNombre() + "'";
        parametrosdelsql[2] = j.getPuntos() + "";
        parametrosdelsql[3] = fotoenbytes;

        objetoBBDD.execSQL(sql2, parametrosdelsql);
        desconectar();
    }


    /**
     * leerTodosLosJugadores
     * Lee jugadores de la bbdd
     *
     * @return un ArrayList<Jugador> con todos los jugadores leidos de la bbdd
     */
    public ArrayList<Jugador> leerTodosLosJugadores() {
        conectar();

        // creo un arraylist para rellenarlo
        ArrayList<Jugador> listajugadores = new ArrayList<>();

        // Ejemplo con cursor sin parametros;
        String sql = "SELECT idJugador, nombre, puntos , foto FROM JugadoresDB";
        Cursor c = objetoBBDD.rawQuery(sql, null);

        // Se mueve al primer regiustro y se entra en el if si hay al menos un registro en el cursor
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no hay más registros
            // Cada campo se identifica por su ordinal, y se debe recuperar con el metodo adecuado
            // a su tipo (getString(), getInt(), etc)
            do {
                int idJugador = c.getInt(0);   // accedo al campo por su posicion en la select
                @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));  // accedo al campo por su nombre
                int puntos = c.getInt(2);
                
                byte[] fotoarray = c.getBlob(3);
                // convertir array de bytes en bitmap
                Bitmap bitmap = byteArray_to_Bitmap(fotoarray);
                
                // hacer con los datos leidos lo que haga falta
                Jugador nuevojugador = new Jugador(idJugador, nombre, puntos, bitmap);
                listajugadores.add(nuevojugador);
            } while (c.moveToNext());
        }

        // *****************************************************************************
        // ---- OTROS METODOS DE LA CLASE CURSOR (AUNQUE QUE NO SE USAN EN ESTE EJEMPLO)
        // *****************************************************************************
        int x = c.getCount();           // total de registros del cursor
        String n = c.getColumnName(1);  // devuelve el nombre de la columna con índice 1
        int k = c.getColumnIndex("apellido");  // devuelve el indice del orden de la de la columna con nombre "apellido"
        c.moveToPosition(1);            //  mueve el puntero del cursor al registro con índice 1

        desconectar();
        return listajugadores;
    }
    
    public static byte[] bitmap_to_ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e("JYOA-->", "ByteArrayOutputStream error");
                }
            }
        }
    }



    public static Bitmap byteArray_to_Bitmap(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;

    }


}
