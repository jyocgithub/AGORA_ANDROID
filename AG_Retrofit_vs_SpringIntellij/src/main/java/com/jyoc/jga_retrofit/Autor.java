package com.jyoc.jga_retrofit;

import java.io.Serializable;

public class Autor implements Serializable {

    private static final long serialVersionUID = -4381618719188668228L;

    private Integer idautor;

    private String nombre;

    private String email;

    private Integer generacion;

    public Autor(Integer idautor, String nombre, String email, Integer generacion) {
        this.idautor = idautor;
        this.nombre = nombre;
        this.email = email;
        this.generacion = generacion;
    }

    public Autor() {

    }
    
    // Getters y setters 
    
    public Integer getIdautor() {
        return idautor;
    }

    public void setIdautor(Integer idautor) {
        this.idautor = idautor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getGeneracion() {
        return generacion;
    }

    public void setGeneracion(Integer generacion) {
        this.generacion = generacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}