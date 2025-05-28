package com.jyoc.ag_recyclerview.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyoc.ag_recyclerview.Cancion;
import com.jyoc.ag_recyclerview.R;
import com.jyoc.ag_recyclerview.recyclerview.MainActivity;

import java.util.ArrayList;


class AdaptadorCancionBaseAdapter extends BaseAdapter {

    private ArrayList<Cancion> listacanciones;
    Activity context;
    private ImageView ivCancion;
    private TextView tvTitulo, tvAutor, tvDuracion;
    LayoutInflater inflater;
    View cadaFila;

    public AdaptadorCancionBaseAdapter(Activity context,ArrayList<Cancion> listacanciones) {
        this.listacanciones = listacanciones;
        this.context = context;
        //Creamos un objeto Inflater, que sera la maquina de “inflar” layouts a cualquier view
        inflater = context.getLayoutInflater();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // creamos un objeto view, uno genérico, que será el que se construya y devuelve por este método
        // y le inflames el layout que queremos usar en cada elemento de la lista
        cadaFila = inflater.inflate(R.layout.cadacancion_layout, null);
        // Crear objetos para cada view del layout-lista, y los asociamos AL VIEW CON FINDVIEWBYID
        ivCancion = cadaFila.findViewById(R.id.ivCancion);
        tvTitulo = cadaFila.findViewById(R.id.tvTitulo);
        tvAutor = cadaFila.findViewById(R.id.tvAutor);
        tvDuracion = cadaFila.findViewById(R.id.tvDuracion);
        // Ponemos el valor del texto sacándolo del array de datos, sabiendo su posicion por position

        Cancion c = listacanciones.get(position);
        ivCancion.setImageBitmap(c.getImagen());
        tvTitulo.setText(c.getTitulo());
        tvAutor.setText(c.getAutor());
        tvDuracion.setText(c.getDuracion() + "");
        return cadaFila;
    }


    @Override
    public int getCount() {
        return listacanciones.size();
    }

    @Override
    public Cancion getItem(int position) {
        return listacanciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
