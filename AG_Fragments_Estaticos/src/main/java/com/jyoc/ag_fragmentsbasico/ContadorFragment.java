package com.jyoc.ag_fragmentsbasico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * create an instance of this fragment.
 *
 */
public class ContadorFragment extends Fragment {

    EditText et;
    Button btn;
    TextView tv;
    
    // Required empty public constructor 
    public ContadorFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contador, container, false);
        et = view.findViewById(R.id.etContador);
        btn = view.findViewById(R.id.btCalcular); 
        tv = view.findViewById(R.id.tvContador);

        MainConFragmentosEstaticosActivity main  = (MainConFragmentosEstaticosActivity) getActivity();

        btn.setOnClickListener( new View.OnClickListener() { public void onClick(View view){
            // ACTUALIZAMOS LOS VIEWS DEL PROPIO FRAGMENT
            tv.setText(et.getText().length()+"");
 
            // ACTUALIZAMOS LOS VIEWS DEL MAINACTIVITY
            main.etcabecera.setText(et.getText());

        } });
        
        
        
        
        return view;
        
        
    }
}