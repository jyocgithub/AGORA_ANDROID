package com.example.spinnerpropio;

import android.widget.GridLayout;

public class BuscaARolo {

    private int nivel;
    private GridLayout miLayout;

    BuscaARolo(int nivel, GridLayout layoutPrincipiante) {
        this.nivel = nivel;
        this.miLayout = layoutPrincipiante;
    }

    public void comienzaNivel() {
        if (nivel == 1) {
            nivel1();
        } else if (nivel == 2) {
            nivel2();
        } else if (nivel == 3) {
            nivel3();
        }
    }

    private void nivel1() {

        for (int i = 0; i<8; i++){

        }
    }

    private void nivel2() {

    }

    private void nivel3() {

    }
}
