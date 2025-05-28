package com.jyoc.firestoredesdecero.pojo;

public class Usuario {
    String id;
    String nombre;
    String contraseña;
    String foto;

    public Usuario() {

    }

    public Usuario(String id, String nombre, String contraseña, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

