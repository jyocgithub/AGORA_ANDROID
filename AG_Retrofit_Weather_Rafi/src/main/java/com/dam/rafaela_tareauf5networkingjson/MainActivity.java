package com.dam.rafaela_tareauf5networkingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dam.rafaela_tareauf5networkingjson.model.Tiempo;
import com.dam.rafaela_tareauf5networkingjson.retrofit.APIRestService;
import com.dam.rafaela_tareauf5networkingjson.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvLatidud;
    TextView tvLongitud;
    EditText etLatitud;
    EditText etLongitud;
    Button btnConsultar;

    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatidud = findViewById(R.id.tvLatidud);
        tvLongitud = findViewById(R.id.tvLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);

        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(this);
        pb = findViewById(R.id.progressBar);


        pb.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

        pb.setVisibility(View.VISIBLE);
        String longi = etLongitud.getText().toString();
        String lati = etLatitud.getText().toString();

        double lon = Double.parseDouble(longi);
        double lat = Double.parseDouble(lati);

        String parametros = lati + "," + longi;
        Log.i("RAFI", "Parametros :" + parametros + ":");


        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);
        Call<Tiempo> call = ars.obtenerTiempoConParametros(parametros);
        //  Call<Tiempo> call = ars.obtenerTiempo();


        call.enqueue(new Callback<Tiempo>() {
            @Override
            public void onResponse(Call<Tiempo> call, Response<Tiempo> response) {

                pb.setVisibility(View.GONE); // lo escondemos


                if (!response.isSuccessful()) {
                    Log.i("RAFI", "Error en la respuesta:" + response.code());
                } else {
                    //// SI PONGO EL RECYCLE LO PASO EN VEZ DE STRING CON ARRAYLIST
                    Tiempo objetoTiempoObtenido = response.body();

                    Log.e("RAFI", "temperatura recibido :" + objetoTiempoObtenido.getCurrently().getTemperature());
                    Log.e("RAFI", "humedad recibido :" + objetoTiempoObtenido.getCurrently().getHumidity());

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("objetoTiempoObtenido", objetoTiempoObtenido.getCurrently());
                    intent.putExtra("nombreciudad", objetoTiempoObtenido.getTimezone());

                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<Tiempo> call, Throwable t) {
                pb.setVisibility(View.GONE); // lo escondemos
                Log.e("RAFI", "Error en la conexion :" + t.toString());


            }
        });

    }
}