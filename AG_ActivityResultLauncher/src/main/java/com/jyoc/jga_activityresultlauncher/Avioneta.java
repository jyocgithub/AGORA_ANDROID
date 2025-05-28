package com.jyoc.jga_activityresultlauncher;

import android.os.Parcel;
import android.os.Parcelable;

public class Avioneta implements Parcelable {

    String nombre;
    String matricula;
    int autonomia;

    public Avioneta(String nombre, String matricula, int autonomia) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.autonomia = autonomia;
    }

    protected Avioneta(Parcel in) {
        nombre = in.readString();
        matricula = in.readString();
        autonomia = in.readInt();
    }

    public static final Creator<Avioneta> CREATOR = new Creator<Avioneta>() {
        @Override
        public Avioneta createFromParcel(Parcel in) {
            return new Avioneta(in);
        }

        @Override
        public Avioneta[] newArray(int size) {
            return new Avioneta[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(matricula);
        dest.writeInt(autonomia);
    }
    @Override
    public String toString() {
        return "Avioneta{" +
                "nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                ", autonomia=" + autonomia +
                '}';
    }
}