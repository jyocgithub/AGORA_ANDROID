package com.jyoc.jga_retrofit;

import android.graphics.Color;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServiciosRestAutor {
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    //DocumentBuilder builder = builderFactory.newDocumentBuilder();

   

    // PARA ACCEDER A LA RED DEL ORDENADOR, NO PODEMOS USAR LOCALHOST, Porque el emulador de Android 
    // se ejecuta en una máquina virtual, y 127.0.0.1 o localhost será la propia dirección del emulador.
    // Si queremos acceder al LOCALHOSRT del ORDENADOR desde el emulador de Android, 
    // se debe usar http://10.0.2.2:8080/
    //String URL_BASE = "http://localhost:8080/jyoclibreria/";  // NO VALE , CLARO....
    String URL_BASE = "http://10.0.2.2:8080/jyoclibreria/";

    //String URL_BASE = "http://192.168.1.100:8080/jyocreponseactivity/";
    
    
    
    //------------------------ GET's
    @GET("todos/")
    @Headers("Accept: application/json")
    Call<List<Autor>> getAutores();

    @GET("{id}")
    @Headers("Accept: application/json")
    Call<Autor> getAutor(@Path("id") int itemId);

    //------------------------ POST
    @POST("alta/")
    @Headers("Accept: application/json")
    Call<Void> saveAutor(@Body Autor autor) ;
    
    //------------------------ DELETE
    @DELETE("borrar/{id}")
    @Headers("Accept: application/json")
    Call<Void> deleteAutor(@Path("id") int itemId);
    
}
