package com.jyoc.jga_retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAutor {

    IRespuestasAsincronas<Autor> activity;
    private static Retrofit retrofit;
    IServiciosRestAutor serviciosAutor;


    public RetrofitAutor(IRespuestasAsincronas activity) {
        this.activity = activity;
        crearRetrofit();
    }

    private void crearRetrofit() {
        if (activity.isOnline()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(IServiciosRestAutor.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            serviciosAutor = retrofit.create(IServiciosRestAutor.class);
        } else {
            Log.d("LOGJyoc *********> ERROR ", "No hay conectividad de red");
        }
    }
    
    public void getListaAutores() {

        Call<List<Autor>> call = serviciosAutor.getAutores();

        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    List<Autor> listaAutores = response.body();
                    activity.trasGetTodos(listaAutores);
                } else {
                    Log.d("LOGJyoc *********> ERROR ", "Respuesta no obtenida, codigo http recibido "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                Log.d("LOGJyoc *********> ERROR ", t.toString());
            }
        });
    }
    
    
    public void saveAutor(Autor autor) {

        Call<Void> call = serviciosAutor.saveAutor(autor);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Log.d("LOGJyoc *********> OK ", "Autor guardado");
                    activity.trasSaveElemento(autor);
                } else {
                    Log.d("LOGJyoc *********> ERROR ", "Respuesta no obtenida, codigo http recibido "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("LOGJyoc *********> ERROR ", t.toString());
            }
        });
    }
    
    
    
    public void deleteAutor(int id) {
        
        
        Call<Void> call = serviciosAutor.deleteAutor(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Log.d("LOGJyoc *********> OK ", "Autor ELIMINADO");
                    activity.trasDeleteElemento(id);
                } else {
                    Log.d("LOGJyoc *********> ERROR ", "Respuesta no obtenida, codigo http recibido "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("LOGJyoc *********> ERROR ", t.toString());
            }
        });
    }
    public void getAutor(int id) {
        
        
        Call<Autor> call = serviciosAutor.getAutor(id);

        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {

                if (response.isSuccessful()) {
                    Autor autor = response.body();
                    Log.d("LOGJyoc *********> OK ", "Autor recuperado");
                    Log.d("LOGJyoc *********> OK ", autor.getEmail());
                    activity.trasGetElemento(autor);
                    
                } else {
                    Log.d("LOGJyoc *********> ERROR ", "Respuesta no obtenida, codigo http recibido "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                Log.d("LOGJyoc *********> ERROR ", t.toString());
            }
        });
    }


    
    public void getListaAutoresSincrona() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IServiciosRestAutor postService = retrofit.create(IServiciosRestAutor.class);
                    Call<List<Autor>> call = postService.getAutores();
                    List<Autor> listaAutores = call.execute().body();
                    activity.trasGetTodos(listaAutores);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }




}
