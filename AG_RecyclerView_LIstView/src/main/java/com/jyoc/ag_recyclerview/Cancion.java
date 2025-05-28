package com.jyoc.ag_recyclerview;

import android.graphics.Bitmap;

public class Cancion {
    String titulo;
    String autor;
    double duracion;
    Bitmap imagen;

    public Cancion(String titulo,
                   String autor,
                   double duracion,
                   Bitmap imagen) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracion = duracion;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", duracion=" + duracion +
                ", imagen=" + imagen +
                '}';
    }
}
