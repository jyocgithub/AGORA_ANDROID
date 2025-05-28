package com.jyoc.firestoredesdecero.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.jyoc.firestoredesdecero.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btFirestore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UsandoFirestoreActivity.class));
            }
        });


        
    }

}




