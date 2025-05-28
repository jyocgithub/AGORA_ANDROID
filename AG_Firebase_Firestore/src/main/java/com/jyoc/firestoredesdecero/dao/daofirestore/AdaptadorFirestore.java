package com.jyoc.firestoredesdecero.dao.daofirestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.jyoc.firestoredesdecero.pojo.Barco;
import com.jyoc.firestoredesdecero.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFirestore extends FirestoreRecyclerAdapter<Barco, AdaptadorFirestore.MiViewHolder> implements View.OnClickListener {
    View.OnClickListener listener;
    static Context context;

    public AdaptadorFirestore(Context context, FirestoreRecyclerOptions<Barco> firebaseoptions) {
        super(firebaseoptions);
        this.context = context;
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // ------------------- vvvvvvvv
        View layoutDeCadaFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cadafila, parent, false);
        // ------------------- ^^^^^^^^
        layoutDeCadaFila.setOnClickListener(this);
        return new MiViewHolder(layoutDeCadaFila);
    }

    @Override
    protected void onBindViewHolder(@NonNull MiViewHolder miViewholder, int posicion, @NonNull Barco elemento) {
        miViewholder.dibujarfila(elemento, posicion);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    //  clase estatica interna con el ViewHolder
    public static class MiViewHolder extends RecyclerView.ViewHolder {
        // ------------------- vvvvvvvv
        View miView;
        TextView tvfecha, tvLogo, tvnombre, tvimporte, tvSaldo, tvconcepto;

        public MiViewHolder(View itemView) {
            super(itemView);
            miView = itemView;
            tvfecha = (TextView) miView.findViewById(R.id.tvFecha);
            tvLogo = (TextView) miView.findViewById(R.id.tvLogo);
            tvimporte = (TextView) miView.findViewById(R.id.tvimporte);
            tvSaldo = (TextView) miView.findViewById(R.id.tvSaldo);
            tvconcepto = (TextView) miView.findViewById(R.id.tvconcepto);
        }

        public void dibujarfila(Barco elemento, int posicion) {
            tvfecha.setText(elemento.isVelamen() + "");
            tvimporte.setText(elemento.getEslora() + "");
            tvconcepto.setText(elemento.getTipo()+"");
            tvSaldo.setText(elemento.getManga()+"");
            tvLogo.setText(elemento.getCapitan().getNombre());
        }
        // ------------------- ^^^^^^^^
    }// ---------------------------------------------------- FIN class MiViewHolder
}
