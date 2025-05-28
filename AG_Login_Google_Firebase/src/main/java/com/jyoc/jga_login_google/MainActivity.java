package com.jyoc.jga_login_google;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // en el MainActivity se recogen estos datos con estas instrucciones :
        String idGoogle  = getIntent().getExtras().getString("ID_GOOGLE");
        String usuarioGoogle  = getIntent().getExtras().getString("USUARIO_GOOGLE");
        String mailGoogle  = getIntent().getExtras().getString("MAIL_GOOGLE");
    }
}