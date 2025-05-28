package com.jyoc.ag_recyclerview.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyoc.ag_recyclerview.Cancion;
import com.jyoc.ag_recyclerview.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


class AdaptadorCancion extends RecyclerView.Adapter<AdaptadorCancion.ViewHolderCancion> {

    private ArrayList<Cancion> listacanciones;

    public AdaptadorCancion(ArrayList<Cancion> listacanciones) {
        this.listacanciones = listacanciones;
    }

    public static class ViewHolderCancion extends RecyclerView.ViewHolder {     // CLASE INTERNA - VIEWHOLDER A USAR
        private ImageView ivCancion;
        private TextView tvTitulo, tvAutor, tvDuracion;

        public ViewHolderCancion(View itemView) {
            super(itemView);
            ivCancion = itemView.findViewById(R.id.ivCancion);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvDuracion = itemView.findViewById(R.id.tvDuracion);
        }
    }

    @Override
    public ViewHolderCancion onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cadacancion_layout, viewGroup, false);
        ViewHolderCancion viewHolderCancion = new ViewHolderCancion(itemView);
        return viewHolderCancion;
    }

    @Override
    public void onBindViewHolder(ViewHolderCancion viewHolder, int pos) {
        Cancion c = listacanciones.get(pos);
        viewHolder.ivCancion.setImageBitmap(c.getImagen());
        viewHolder.tvTitulo.setText(c.getTitulo());
        viewHolder.tvAutor.setText(c.getAutor());
        viewHolder.tvDuracion.setText(c.getDuracion() + "");

    }

    @Override
    public int getItemCount() {
        return listacanciones.size();
    }
    @Override
    public long getItemId(int pos) {
        return  pos  ;
    }
    @Override
    public int getItemViewType(int pos) {
        return pos ;
    }
}