package com.jyoc.pojos_para_guiasrapidas;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Gato {

    private List<String> alergias = new ArrayList<>();
    private String nombre;
    private int edad;


    public String convertirAJson() {
        Gson unGson = new Gson();
        String cadenaEnFormatoGSON = unGson.toJson(this);
        return cadenaEnFormatoGSON;
    }
    public static Gato recuperarDeJson(String cad) {
        Gson unGson = new Gson();
        Gato objeto = unGson.fromJson(cad, Gato.class);
        return objeto;
    }




    public Gato(List<String> alergias, String nombre, int edad) {
        this.alergias = alergias;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Gato() {
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
