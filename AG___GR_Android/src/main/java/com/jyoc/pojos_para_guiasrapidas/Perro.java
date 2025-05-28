package com.jyoc.pojos_para_guiasrapidas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Perro implements Serializable {

    private List<String> alergias = new ArrayList<>();
    private String nombre;
    private int edad;

    public Perro(List<String> alergias, String nombre, int edad) {
        this.alergias = alergias;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Perro() {
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
