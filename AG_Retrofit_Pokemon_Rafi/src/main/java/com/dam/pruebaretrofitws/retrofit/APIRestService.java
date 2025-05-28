package com.dam.pruebaretrofitws.retrofit;

import com.dam.pruebaretrofitws.model.PokemonRes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRestService {

    /// CREAMOS LA INTERFACE

    /// donde metemos los metodos y constantes donde se aloja el web sercices donde queremos llegar
    /// LOCALIZACION DE LA PAGINA WEB
    public static final String BASE_URL = "https://pokeapi.co/api/v2/";

    //// ES LA LLAMADA AL WEB SERVICE....
    @GET("pokemon/")
    Call<PokemonRes> obtenerPokemon();


}
