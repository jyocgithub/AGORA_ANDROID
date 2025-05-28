package com.example.pokedexcompleta;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



/*
PICASSO
gradle:
implementation 'com.squareup.picasso:picasso:(insert latest version)'

maven:
<dependency>
  <groupId>com.squareup.picasso3</groupId>
  <artifactId>picasso</artifactId>
  <version>(insert latest version)</version>
</dependency>


 */


public class CustomAdapter extends BaseAdapter {
    ArrayList<Pokemon> pokemons;
    Activity ctx;

    public CustomAdapter(ArrayList<Pokemon> pokemons, Activity ctx) {
        this.pokemons = pokemons;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {

        return pokemons.size();
    }

    @Override
    public Pokemon getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Genera un view nuevo a partir del layout de "item_pkm" generado.
        // Se debe crear un nuevo view para cada fila, asi que hay que crearlo aqui
        View viewInflado = LayoutInflater.from(ctx).inflate(R.layout.item_pkm, null);
        TextView txtNombre = viewInflado.findViewById(R.id.nombrePkm);
        ImageView imgPkm = viewInflado.findViewById(R.id.imgPkm);
        txtNombre.setText(pokemons.get(position).getNombre());

        // ---------------------------------------- CARGAR IMAGEN DE URL -------------- USANDO HILO PROPIO
        bajarImagenAsincronamente(ctx, position,imgPkm);
        

        // ---------------------------------------- CARGAR IMAGEN DE URL -------------- USANDO PICASSO

        // --------- Picasso: uso básico:
        //Picasso.get().load(MainActivity.urlsImg.get(position)).into(imgPkm);

        // --------- Picasso: usos habituales:
        // Picasso.get().load(R.drawable.miimagenendrawable).into(imageView1);
        // Picasso.get().load("file:///urlbase/imagenenelanube.png").into(imageView2);
        // Picasso.get().load(new File(...)).into(imageView3);

        // --------- Podemos redimensionar y colocar la imagen usada
        // Picasso.get()
        //        .load(url)
        //        .resize(50, 50)
        //        .centerCrop()
        //        .into(imageView)

        // --------- Podemos usar placeholders antes de cargar imagen, o imagenes de error
        // Picasso.get()
        //        .load(url)
        //        .placeholder(R.drawable.imagentemporal)   // imagen temporal mientras hay carga
        //        .error(R.drawable.imagensihayerror)       // imagen a usar si hay error
        //        .into(imageView);


        return viewInflado;
    }

    public void bajarImagenAsincronamente(Activity activity,int position, ImageView imageView) {

        // UNA LLAMADA A UN RECURSO DE INTERNET NO DEBE HACERSE EN EL HILO PRINCIPAL
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(MainActivity.urlsImg.get(position));
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    // AHORA, EN CAMBIO, UNA LLAMADA A MODIFICAR LA UI DEBE HACERSE SIEMPRE EN EL HILO PRINCIPAL, SI NO DA EL ERROR:
                    //CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bmp);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }




}
