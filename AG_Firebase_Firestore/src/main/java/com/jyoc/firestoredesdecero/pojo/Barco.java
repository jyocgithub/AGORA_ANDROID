package com.jyoc.firestoredesdecero.pojo;

public class Barco {
    String id;
    String tipo;
    int eslora;
    int manga;
    boolean velamen;
    Capitan capitan;

    public Barco() {

    }

    public Barco(String id, String tipo, int eslora, int manga, boolean velamen, Capitan capitan) {
        this.id = id;
        this.tipo = tipo;
        this.eslora = eslora;
        this.manga = manga;
        this.velamen = velamen;
        this.capitan = capitan;
        
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEslora() {
        return eslora;
    }

    public void setEslora(int eslora) {
        this.eslora = eslora;
    }

    public int getManga() {
        return manga;
    }

    public void setManga(int manga) {
        this.manga = manga;
    }

    public boolean isVelamen() {
        return velamen;
    }

    public void setVelamen(boolean velamen) {
        this.velamen = velamen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Capitan getCapitan() {
        return capitan;
    }

    public void setCapitan(Capitan capitan) {
        this.capitan = capitan;
    }

public static class Capitan{
   public  String nombre;
   public  int edad;

    public Capitan(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public Capitan() {
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
}

