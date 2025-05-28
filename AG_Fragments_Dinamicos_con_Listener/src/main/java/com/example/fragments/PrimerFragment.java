package com.example.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PrimerFragment extends Fragment {

    EditText et;
    Button btn;

    public PrimerFragment() {        // Required empty public constructor
    }

    //Se va a inflar el layout correspondiente a este fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_primer, container, false);
        //String textoEnviado = getArguments().getString("eltexto");
        btn = view.findViewById(R.id.btFragmentPimero);
        et = (EditText) view.findViewById(R.id.tvTexto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarAlMain();
            }
        });

        return view;
    }

    /* =====================================================================================  */
    /* =========== METODOS Y ATRIBUTOS DEL LISTENER PARA HABLAR CON LA ACTIVIDAD  ==========  */
    /* =====================================================================================  */
    public interface OnComunicarConActivityListener {
        public void mensajeAlMainActivity(String texto);
        public String mensajeDelMainActivity();
    }

    private OnComunicarConActivityListener miObjetoDeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnComunicarConActivityListener) {
            miObjetoDeActivity = (OnComunicarConActivityListener) context;

        } else {
            throw new RuntimeException(context.toString() + " must implement OnComunicarConActivityListener");
        }

    }

    @Override
    public void onDetach() {
        if (miObjetoDeActivity!=null){
            miObjetoDeActivity=null;
        }
        super.onDetach();
    }

    public void enviarAlMain() {
        miObjetoDeActivity.mensajeAlMainActivity(et.getText().toString());
    }

    public void recibirDeMain() {
        String loquehayenelmain = miObjetoDeActivity.mensajeDelMainActivity();
        et.setText(loquehayenelmain);
    }

}
