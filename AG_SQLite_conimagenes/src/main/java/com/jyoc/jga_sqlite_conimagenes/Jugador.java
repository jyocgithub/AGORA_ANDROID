package com.jyoc.jga_sqlite_conimagenes;

import android.graphics.Bitmap;

public class Jugador {

    private int idJugador;
    private String nombre;
    private int puntos;
    private Bitmap foto;

    public Jugador(int idJugador, String nombre, int puntos, Bitmap foto) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.puntos = puntos;
        this.foto = foto;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}
