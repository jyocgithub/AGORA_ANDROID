package com.jyoc.ag_fragmentsbasico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TituloFragment extends Fragment {


    public TituloFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View  v = inflater.inflate(R.layout.fragment_titulo, container, false);
       
       
        return v;
    }
}