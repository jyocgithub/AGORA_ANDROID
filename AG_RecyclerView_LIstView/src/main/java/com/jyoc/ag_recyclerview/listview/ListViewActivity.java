package com.jyoc.ag_recyclerview.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jyoc.ag_recyclerview.R;
import com.jyoc.ag_recyclerview.Cancion;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        ArrayList<Cancion> listaCanciones = new ArrayList<>();
        //imagenFoto.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.song));
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.imgcancion);

        listaCanciones.add(new Cancion("Obvious Song", "Joe Jackson", 4.23, bm));
        listaCanciones.add(new Cancion("The Other Me", "Joe Jackson", 2.55, bm));
        listaCanciones.add(new Cancion("Jamie G", "Joe Jackson", 2.11, bm));
        listaCanciones.add(new Cancion("Stranger Than Fiction", "Joe Jackson", 3.10, bm));
        listaCanciones.add(new Cancion("Goin' Downtown", "Joe Jackson", 4.11, bm));
        
        ListView listView = findViewById(R.id.listView1);

        AdaptadorCancionArrayAdapter adaptadorCancionArrayAdapter = new AdaptadorCancionArrayAdapter(this,listaCanciones);
        listView.setAdapter(adaptadorCancionArrayAdapter);
        
        //AdaptadorCancionBaseAdapter adaptadorCancionBaseAdapter = new AdaptadorCancionBaseAdapter(this,listaCanciones);
        //listView.setAdapter(adaptadorCancionBaseAdapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               
                //Cancion cancionelegida = listaCanciones.get(i);
                Cancion cancionelegida = (Cancion)adapterView.getAdapter().getItem(i);
               
                Toast.makeText(ListViewActivity.this,cancionelegida.toString(), Toast.LENGTH_SHORT).show();
                
                
                
                
            }
        });

    }
}