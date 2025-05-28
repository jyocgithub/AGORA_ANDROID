package com.jyoc.ag_sqlite_room.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jyoc.ag_sqlite_room.R;
import com.jyoc.ag_sqlite_room.room.Contacto;
import com.jyoc.ag_sqlite_room.room.IContactoDAO;
import com.jyoc.ag_sqlite_room.room.DBContactos;

import java.util.ArrayList;
import java.util.List;
/*
La biblioteca de persistencias Room brinda una capa de abstracción para SQLite que permite acceder 
a la base de datos sin problemas y, al mismo tiempo, aprovechar toda la tecnología de SQLite. 
En particular, Room brinda los siguientes beneficios:
- Verificación del tiempo de compilación de las consultas en SQL
- Anotaciones de conveniencia que minimizan el código estándar repetitivo y propenso a errores
- Rutas de migración de bases de datos optimizadas
Debido a estas consideraciones, Google recomienda usar Room en lugar de usar las API de SQLite directamente.

 */

public class MainActivity extends AppCompatActivity {

    IContactoDAO contactoDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBContactos db = DBContactos.getDatabase(this);
        contactoDAO = db.contactoDao();


        anadir();
        seleccionar();

    }


    // ------- INSERT
    public void anadir() {
        contactoDAO.borrarTodos();


        Contacto c1 = new Contacto("Pepe", "Perez", 33, "12/12/2020", "pepe@gmail.com");
        Contacto c2 = new Contacto("Ana", "Anuska", 22, "12/12/2020", "ana@gmail.com");
        Contacto c3 = new Contacto("Eva", "Martinesz", 28, "12/12/2020", "eva@gmail.com");

        List<Contacto> listacontactos = new ArrayList<>();
        Contacto c4 = new Contacto("Luis", "Perez", 33, "12/12/2020", "pepe@gmail.com");
        Contacto c5 = new Contacto("Jorge", "Anuska", 22, "12/12/2020", "ana@gmail.com");
        Contacto c6 = new Contacto("Marta", "Martinesz", 28, "12/12/2020", "eva@gmail.com");
        listacontactos.add(c4);
        listacontactos.add(c5);
        listacontactos.add(c6);

        contactoDAO.insertar(c1);
        contactoDAO.insertarDos(c2, c3);
        contactoDAO.insertarLista(listacontactos);

    }


    // ------- SELECT ALL
    public void seleccionar() {
        ArrayList<Contacto> listaContactos = (ArrayList<Contacto>) contactoDAO.selectTodos();
        for (Contacto c : listaContactos) {
            Log.d("JYOC=== DE SELECT TODOS ", c.toString());
        }

        Contacto c2 = contactoDAO.selectPorId(32);
        Log.d("JYOC=== DE SELECT por id  ", c2.toString());
        Contacto c3 = contactoDAO.selectPorNombreYApellidos("Luis", "Perez");
        Log.d("JYOC=== DE SELECT por id  ", c3.toString());




    }


}
