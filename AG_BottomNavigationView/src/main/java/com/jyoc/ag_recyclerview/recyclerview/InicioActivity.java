package com.jyoc.ag_recyclerview.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ag_bottomnavigation_cardview.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jyoc.ag_recyclerview.Cancion;
import com.jyoc.ag_recyclerview.listview.ListViewActivity;


import java.util.ArrayList;

public class InicioActivity extends AppCompatActivity {
    RecyclerView recyclerViewPrueba;
    //ImageView imagenFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Cancion> listaCanciones = new ArrayList<>();
        //imagenFoto.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.song));
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.imgcancion);

        listaCanciones.add(new Cancion("Obvious Song", "Joe Jackson", 4.23, bm));
        listaCanciones.add(new Cancion("The Other Me", "Joe Jackson", 2.55, bm));
        listaCanciones.add(new Cancion("Jamie G", "Joe Jackson", 2.11, bm));
        listaCanciones.add(new Cancion("Stranger Than Fiction", "Joe Jackson", 3.10, bm));
        listaCanciones.add(new Cancion("Goin' Downtown", "Joe Jackson", 4.11, bm));

        // creamos la referencia del RecyclerView añadido en el layout
        recyclerViewPrueba = findViewById(R.id.rvCanciones);

        // definimos que el view va a tener tamaño fijo
        recyclerViewPrueba.setHasFixedSize(true);

        // creamos un objeto de nuestro Adaptador y le pasamos la lista de objetos de canciones a mostrar
       
        // TRES EJEMPLOS DE ADAPTADOR
       
        // 1.- ////////////////  USAMOS EL ADAPTADOR SIN LISTENERS
        //  AdaptadorCancion adaptadorcancion = new AdaptadorCancion(listaCanciones);
       
        // 2.- //////////////// USAMOS EL ADAPTADOR IMPLEMENTANDO LISTENERS
          AdaptadorCancion_ListenerFilaEntera adaptadorcancion = new AdaptadorCancion_ListenerFilaEntera(listaCanciones);
          adaptadorcancion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                // asi podemos acceder a conocer 
                // que elemento del RecyclerView ha sido selecccionado
                int pos = recyclerViewPrueba.getChildAdapterPosition(v);
                Cancion cancionelegida = listaCanciones.get(pos);
                Toast.makeText(getApplicationContext(), " elegida la cancion "+ cancionelegida.getTitulo(), Toast.LENGTH_SHORT).show();
             }
          });
       
        // 3.- //////////////// USAMOS EL ADAPTADOR CON LISTENERS INTERNOS PARA ALGUN VIEW DE CADA FILA
        //AdaptadorCancion_ListenerUnViewSolo adaptadorcancion = new AdaptadorCancion_ListenerUnViewSolo(listaCanciones);

        
        // asignamos el adaptador al RecyclerView
        recyclerViewPrueba.setAdapter(adaptadorcancion);

        // definimos como queremos ver el RecyclerView, en este caso, en lista vertical 
        // Es NECESARIO indicarlo
        recyclerViewPrueba.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.separador));
        recyclerViewPrueba.addItemDecoration(divider);
        
        
        
        
        // ---------- PARA OTRA ACTIVIDAD CON LISTVIEW
        findViewById(R.id.btlistview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioActivity.this, ListViewActivity.class));
            }
        });


        BottomNavigationView bnv = findViewById(R.id.bottomnavigationview);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.paso1){
                    
                }
                if(item.getItemId() == R.id.paso3){
                    
                }
                
                
                return false;
                
            }
        });
        
        
        
        
        
        
        
    }
}

