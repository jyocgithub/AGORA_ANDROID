package com.example.spinnerpropio;

import java.util.ArrayList;

public class IconosSpinner {

    private static ArrayList<IconosSpinner> arrayIconoSpinner = new ArrayList<>();
    private int id;
    private String nombre;
    private int[] idDeIconos = {R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4};

    public IconosSpinner(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static void iniciarArrayIconos() {
        if (arrayIconoSpinner.size() == 0) {
            IconosSpinner iconoSpinner1 = new IconosSpinner(0, "IconosSpinner 1");
            IconosSpinner iconoSpinner2 = new IconosSpinner(1, "IconosSpinner 2");
            IconosSpinner iconoSpinner3 = new IconosSpinner(2, "IconosSpinner 3");
            IconosSpinner iconoSpinner4 = new IconosSpinner(3, "IconosSpinner 4");
            arrayIconoSpinner.add(iconoSpinner1);
            arrayIconoSpinner.add(iconoSpinner2);
            arrayIconoSpinner.add(iconoSpinner3);
            arrayIconoSpinner.add(iconoSpinner4);
        }
    }

    public int getImagenIcono() {
        //switch (getId()) {
        //    case "0":
        //        return R.drawable.icon_1;
        //    case "1":
        //        return R.drawable.icon_2;
        //    case "2":
        //        return R.drawable.icon_3;
        //    case "3":
        //        return R.drawable.icon_4;
        //}
        //return R.drawable.icon_1;
        
        return idDeIconos[id];
    }

    public static ArrayList<IconosSpinner> getArrayIconosSpinner() {
        return arrayIconoSpinner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
