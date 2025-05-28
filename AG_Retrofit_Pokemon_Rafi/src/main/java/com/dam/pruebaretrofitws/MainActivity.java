package com.dam.pruebaretrofitws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dam.pruebaretrofitws.model.Pokemon;
import com.dam.pruebaretrofitws.model.PokemonRes;
import com.dam.pruebaretrofitws.retrofit.APIRestService;
import com.dam.pruebaretrofitws.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PruebaRetrofitWS";
    Button btnC;
    TextView tvR;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnC= findViewById(R.id.btnConsultar);
        tvR= findViewById(R.id.tvResultado);
        pb= findViewById(R.id.pb);
        /// ESTO ES PARA QUE NO SE VEA
        pb.setVisibility(View.GONE);

        btnC.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);
        Call<PokemonRes> call = ars.obtenerPokemon();

// hacemos visible el progress bar que habremos inicializado en el
// onCreate poniéndolo como oculto
        pb.setVisibility(View.VISIBLE);

        /// sacarlo en un hilo para que no se colapse
        /// ponemos el callaback
        call.enqueue(new Callback<PokemonRes>() {
            @Override
            public void onResponse(Call<PokemonRes> call, Response<PokemonRes> response) {

                pb.setVisibility(View.GONE); // lo escondemos
                String resultado="";

                if (!response.isSuccessful()) {
                    Log.i(TAG, "Error :" + response.code());
                } else {
                    //// SI PONGO EL RECYCLE LO PASO EN VEZ DE STRING CON ARRAYLIST
                    PokemonRes pokeRes = response.body();
                    for (Pokemon poke: pokeRes.getResults()) {
                        resultado += poke.toString()+"\n";
                    }
                    tvR.setText(resultado);
                }



            }

            @Override
            public void onFailure(Call<PokemonRes> call, Throwable t) {
                pb.setVisibility(View.GONE); // lo escondemos
                Log.e(TAG,"Error FAILURE :"+ t.toString());


            }
        });
    }
}

