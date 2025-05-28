package com.jyoc.ag_fragmentsbasico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainConFragmentosEstaticosActivity extends AppCompatActivity {
    TextView etcabecera;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_con_fragmentos_estaticos);
        
        etcabecera= findViewById(R.id.etCabecera);
        
    }
}