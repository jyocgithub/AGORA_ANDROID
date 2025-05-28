package com.jyoc.jga_retrofit;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRespuestasAsincronas<T> {

    void trasGetElemento(T elemento);    
    void trasGetTodos(List<T> elementos);    
    void trasSaveElemento(T elemento);    
    void trasDeleteElemento(int elemento);
    boolean isOnline();
}
