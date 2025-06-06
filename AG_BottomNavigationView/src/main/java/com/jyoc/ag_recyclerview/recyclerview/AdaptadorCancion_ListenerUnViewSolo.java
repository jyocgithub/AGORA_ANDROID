package com.jyoc.ag_recyclerview.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ag_bottomnavigation_cardview.R;
import com.jyoc.ag_recyclerview.Cancion;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


class AdaptadorCancion_ListenerUnViewSolo extends RecyclerView.Adapter<AdaptadorCancion_ListenerUnViewSolo.ViewHolderCancion> {

    private ArrayList<Cancion> listacanciones;

    public AdaptadorCancion_ListenerUnViewSolo(ArrayList<Cancion> listacanciones) {
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
        
        viewHolder.ivCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Elegida la imagen de la cancion "+ c.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });

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