package com.jyoc.jyocsqlite_guion;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * La forma normal de trabajar con BBDD sera creando clases que deriven de SQLiteOpenHelper.
 * SQLiteOpenHelper tiene sólo un constructor, que no es necesario sobrescribir,
 * y dos métodos abstractos a sobrescribir
 * - onCreate() para crear nuestra base de datos
 * - onUpgrade() para actualizar una bbdd existete con una nueva version
 */
public class MiClaseSqliteMetodosSqlite extends SQLiteOpenHelper {

    // ***********************************************************  ATRIBUTOS
    // **************************************************************************************
    // Objeto de la bbdd que se usa en todos los metodos de esta clase
    SQLiteDatabase objetoBBDD;
    public static final String NOMBREBBDD = "miBBDD.db";
    public static final int VERSION = 1;


    // ***********************************************************  CONSTRUCTOR
    // **************************************************************************************
    // Constructor, que no se toca
    public MiClaseSqliteMetodosSqlite(Context contexto) {
        super(contexto, NOMBREBBDD, null, VERSION);
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
        String sql = "CREATE TABLE JugadoresDB (idJugador INTEGER, nombre TEXT, puntos INTEGER)";
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

        //--- En el ContentValues se indican los datos que se van a almacenar en la bbdd
        ContentValues nuevoRegistro = new ContentValues();
        // añadir varias líneas, una por cada campo que se añade 
        nuevoRegistro.put("idJugador", j.getIdJugador());
        nuevoRegistro.put("nombre", j.getNombre());
        nuevoRegistro.put("puntos", j.getPuntos());
        // Insertamos el registro en la base de datos
        objetoBBDD.insert("JugadoresDB", null, nuevoRegistro);

        desconectar();
    }

    /**
     * borrarJugador
     *
     * @param idjugadoraborrar el jugador que se ha de borrar, se busca por el idJugador para encontrarlo
     */
    public void borrarJugador(int idjugadoraborrar) {
        conectar();


        // crear un String con la clausula where con interrogantges en las variables, 
        // y un array de Strings con los valores de los interrogantes 
        // los valores en el array van evidentemente todos como String,
        // sin importarnos el tipo de dato que luego tengan en la bbdd
        String clausulaWhere = "idJugador=?";
        String[] valoresClausulaWhere = new String[]{idjugadoraborrar + ""};

        objetoBBDD.delete("JugadoresDB", clausulaWhere, valoresClausulaWhere);

        desconectar();

    }


    /**
     * actualizarJugador
     * Se modifican todos los campos menos el idJugador
     *
     * @param j el jugador que se ha de mmodificar, se busca por el idJugador para encontrarlo
     */
    public void actualizarJugador(Jugador j) {
        conectar();
        //--- En el ContentValues se indican los datos que se van a modificar en la bbdd
        ContentValues actualizarRegistro = new ContentValues();
        // añadir varias líneas, una por cada campo que se modifica (este ejemplo solo cambia el nombre
        actualizarRegistro.put("nombre", j.getNombre());
        actualizarRegistro.put("puntos", j.getPuntos());

        // crear un String con la clausula where con interrogantges en las variables, 
        // y un array de Strings con los valores de los interrogantes 
        // los valores en el array van evidentemente todos como String,
        // sin importarnos el tipo de dato que luego tengan en la bbdd
        String clausulaWhere = "idJugador=?";
        String[] valoresClausulaWhere = new String[]{j.getIdJugador()+""};

        // Actualizamos el registro en la base de datos
        objetoBBDD.update("JugadoresDB", actualizarRegistro, clausulaWhere, valoresClausulaWhere);

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

        //String[] camposElegidos = new String[]{"nombre", "puntos"};
        String[] camposElegidos = null;
        String clausulaWhere = null;
        String[] valoresClausulaWhere = null;

        Cursor c = objetoBBDD.query("JugadoresDB", camposElegidos, clausulaWhere, valoresClausulaWhere, null, null, null);

        ArrayList<Jugador> listajugadores = new ArrayList<>();

        while (c.moveToNext()) {
            int idJugador = c.getInt(c.getColumnIndex("idJugador"));    
            String nombre = c.getString(c.getColumnIndex("nombre"));  
            int puntos = c.getInt(c.getColumnIndex("puntos"));
            
            Jugador nuevojugador = new Jugador(idJugador, nombre, puntos);
            listajugadores.add(nuevojugador);
        }
        desconectar();
        return listajugadores;
    }


    /**
     * leerUnJugadoresPorSuNombre
     * Devuelve un jugador, sabiendo su nombre
     *
     * @param nombreabuscar el nombre del jugador a buscar
     * @return un objeto jugador con el jugador encontrado, o null si no existe
     */
    public Jugador leerUnJugadoresPorSuNombre(String nombreabuscar) {
        conectar();

        Jugador jugador =null;

        String[] camposElegidos = null;  // todos los campos
        //String[] camposElegidos = new String[]{"nombre", "puntos"};
        String clausulaWhere = "nombre = ? ";
        String[] valoresClausulaWhere = new String[]{nombreabuscar};

        Cursor c = objetoBBDD.query("JugadoresDB", camposElegidos, clausulaWhere, valoresClausulaWhere, null, null, null);

        ArrayList<Jugador> listajugadores = new ArrayList<>();

        if (c.moveToNext()) {
            int idJugador = c.getInt(c.getColumnIndex("idJugador"));
            String nombre = c.getString(c.getColumnIndex("nombre"));
            int puntos = c.getInt(c.getColumnIndex("puntos"));
            jugador = new Jugador(idJugador, nombre, puntos);
        }

        desconectar();
        return jugador;
    }

}
