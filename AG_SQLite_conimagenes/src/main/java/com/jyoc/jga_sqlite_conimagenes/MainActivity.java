package com.jyoc.jga_sqlite_conimagenes;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView et = findViewById(R.id.etcentro);
        ImageView ivfoto = findViewById(R.id.ivFoto);
        MiClaseSqlite miClaseSqlite = new MiClaseSqlite(this, "JugadoresDB", null, 1);

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.cara_1);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.cara_2);
        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.cara_3);

        Jugador jug = new Jugador(323, "pepe", 100,b1);
        miClaseSqlite.guardarJugador(jug);
        Jugador jug2 = new Jugador(222, "luis", 300,b2);
        miClaseSqlite.guardarJugador(jug2);
        Jugador jug3 = new Jugador(11, "dani", 400,b3);
        miClaseSqlite.guardarJugador(jug3);


        ArrayList<Jugador> listajugadoresleidos = miClaseSqlite.leerTodosLosJugadores();
     
        et.setText(listajugadoresleidos.size() + "");
        ivfoto.setImageBitmap(listajugadoresleidos.get(1).getFoto());
        


    }
    @SuppressLint("Range")
    public void otrosMetodosDeSqlite() {



        ContentResolver cr =getContentResolver();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            Cursor c = cr.query(ContactsContract.Data.CONTENT_URI, null,null,null);

            while(c.moveToNext()){

               String nombre  = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            }

        }

    }


}



