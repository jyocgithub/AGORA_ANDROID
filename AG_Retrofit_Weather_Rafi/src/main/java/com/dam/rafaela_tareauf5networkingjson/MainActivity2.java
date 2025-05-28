package com.dam.rafaela_tareauf5networkingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.rafaela_tareauf5networkingjson.model.DatosTiempo;
import com.dam.rafaela_tareauf5networkingjson.model.Tiempo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    TextView tvCiudad;
    ImageView ivImagen;
    TextView tvHora;
    TextView tvGrados;
    TextView tvHumedad;
    TextView tvNumHumedad;
    TextView tvNumLluvia;
    TextView tvCielo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        tvCiudad= findViewById(R.id.tvCiudad);
        ivImagen= findViewById(R.id.ivImagen);
        tvHora= findViewById(R.id.tvHora);
        tvGrados= findViewById(R.id.tvGrados);
        tvHumedad= findViewById(R.id.tvHumedad);
        tvNumHumedad= findViewById(R.id.tvNumHumedad);
        tvNumLluvia= findViewById(R.id.tvNumLluvia);
        tvCielo= findViewById(R.id.tvCielo);


        Intent intent = getIntent();
        DatosTiempo objetoTiempoObtenido = intent.getParcelableExtra("objetoTiempoObtenido");
        String nombreciudad = intent.getStringExtra("nombreciudad");


            /// aqui para el cambio de los grados
        float gfahr = objetoTiempoObtenido.getTemperature();
        float gcel = farenheitToCentigrades(gfahr);
        DecimalFormat df = new DecimalFormat("0");
        String celsius = df.format(gcel);


        tvCiudad.setText(nombreciudad);
     //   tvgrados.setText(objetoTiempoObtenido.getTemperature()+"º");
        tvGrados.setText(celsius+"º");
        tvNumHumedad.setText(objetoTiempoObtenido.getHumidity()+"%");
        tvNumLluvia.setText(objetoTiempoObtenido.getPrecipProbability()+"%");
        tvCielo.setText(objetoTiempoObtenido.getSummary());

        int horamilisegundos = objetoTiempoObtenido.getTime();
        Date fecha = new Date(horamilisegundos);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");
        String lahora = formatter.format(fecha );

        tvHora.setText(lahora);


       /////ivImage todo es ------ ESTO ES PARA METER LA IMAGEN CUANDO METEMOS LA LATITUD Y LONGUITUD
        String nombredelaimahen = objetoTiempoObtenido.getIcon()+".png"  ;
        nombredelaimahen = nombredelaimahen.replace("-day", "");
        nombredelaimahen = nombredelaimahen.replace("-", "_");

        int iddelaimagen = getResources().getIdentifier( nombredelaimahen  , "drawable",getPackageName());
     //   Bitmap bitmapdelaimagen = BitmapFactory.decodeResource(getResources(),iddelaimagen);
        Bitmap bitmapdelaimagen = BitmapFactory.decodeResource(getResources(),R.drawable.partly_cloudy);

        ivImagen.setImageBitmap(bitmapdelaimagen);


    }

    public static float farenheitToCentigrades(float degrees){
        float result=0;
        result=degrees-32;
        result*=5;
        result/=9;
        return result;
    }

}