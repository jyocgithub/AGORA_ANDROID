package com.dam.rafaela_tareauf5networkingjson.retrofit;


import com.dam.rafaela_tareauf5networkingjson.model.Tiempo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRestService {

    /// CREAMOS LA INTERFACE

    /// donde metemos los metodos y constantes donde se aloja el web sercices donde queremos llegar
    /// LOCALIZACION DE LA PAGINA WEB
    public static final String BASE_URL = "https://api.darksky.net/forecast/";


    // LA MIA   //https://api.darksky.net/forecast/11ce4328111023379e0fdc9d28c24a02/42.5,-3.7?exclude=minutely,hourly,daily,alerts,flags&lang=es/}
    //https://api.darksky.net/forecast/11ce4328111023379e0fdc9d28c24a02/40.5,-3.7?exclude=minutely,hourly,daily,alerts,flags&lang=es

    //// ES LA LLAMADA AL WEB SERVICE....
     @GET("11ce4328111023379e0fdc9d28c24a02/40.5,-3.7?exclude=minutely,hourly,daily,alerts,flags&lang=es")
     Call<Tiempo> obtenerTiempo();

     @GET("11ce4328111023379e0fdc9d28c24a02/{parametros}?exclude=minutely,hourly,daily,alerts,flags&lang=es")
     Call<Tiempo> obtenerTiempoConParametros(@Path("parametros") String  parametros );

}
