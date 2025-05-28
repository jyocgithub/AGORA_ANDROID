package com.jyoc.jga_countdown_timer;

import android.os.CountDownTimer;
import android.widget.TextView;

public class AUX_ClockDownTimerConPause {

    private TextView visorDelTiempo;
    private long milisAunPendientes;
    private CountDownTimer reloj;
    private int estado;
    private final int SINARRANCAR = 1;
    private final int CONTANDO = 2;
    private final int PAUSA = 3;

    public AUX_ClockDownTimerConPause(TextView visorDelTiempo) {
        this.visorDelTiempo = visorDelTiempo;
        estado = SINARRANCAR;
    }

    public void arrancar(long tiempoenmilisegundos) {
        if (estado == SINARRANCAR) {
            estado = CONTANDO;
            reloj = new CountDownTimer(tiempoenmilisegundos, 1000) {
                public void onTick(long millisUntilFinished) {

                    long min = (millisUntilFinished / (1000 * 60));
                    long seg = ((millisUntilFinished / 1000) - min * 60);
                    visorDelTiempo.setText(min + ":" + seg);
                    milisAunPendientes = millisUntilFinished;
                }

                public void onFinish() {
                    visorDelTiempo.setText("Acabo!");
                }
            }.start();
        }
    }

    public void pausa() {
        if (estado == CONTANDO) {
            estado = PAUSA;
            reloj.cancel();
        }
    }

    public void continuar() {
        if (estado == PAUSA) {
            estado = SINARRANCAR;
            arrancar(milisAunPendientes);
        }
    }

}