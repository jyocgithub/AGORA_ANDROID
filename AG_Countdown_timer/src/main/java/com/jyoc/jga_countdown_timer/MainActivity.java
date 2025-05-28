package com.jyoc.jga_countdown_timer;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    int contador = 100;
    Handler handler;
    Runnable miHiloRunnable;
    TextView txtContador;
    Button btArrrancar, btPausa, btContinuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtContador = findViewById(R.id.tvTiempo);
        handler = new Handler();

        btArrrancar = findViewById(R.id.btArrancar);
        btContinuar = findViewById(R.id.btContinuar);
        btPausa = findViewById(R.id.btPause);
        
        
        // PODEMOS LANZAR DISTINTOS METODOS PARA PROBAR LAS DIFERENTES VERSIONES DE UN COUNTDOWN
        // ======================================================================================

        //metodoArrancarContadorConThread();  // arranca el hilo Thread, no el countdown NO RECOMENDABLE pues toca la UI fuera del hilo UI
        //metodoArrancarContadorConThreadYrunOnUiThread();  // arranca el hilo Thread, no el countdown NO RECOMENDABLE pues toca la UI fuera del hilo UI
        //metodoArrancarContadorConRunnableYExecutor();  // arranca el hilo Thread, no el countdown NO RECOMENDABLE pues toca la UI fuera del hilo UI
        //metodoArrancarContadorConRunnableYHandler();  // arranca el hilo runnable, no el countdown
        //metodoArrancaCountDown(); // arranca el countdown, no el hilo
        metodoArrancaCountDownBasicoVersionJYOC(); // arranca el countdown de la clase creada por JYOC, con pause y resume
    }


    // Crea una tarea que modifica el textview,como un hilo normnal, con un Thread
    // NO ES ACONSEJABLE QUE SE MODIFIQUE LA UI EN OTRO HILO QUE NO SEA EL PROPIO DE LA UI
    // ASI QUE NO ES ACONSEJABLE USAR HILOS ASI, SI VAN A MODIFDICAR LA UI
    // Si no se modifica la UI, se puede usar el hilo con Thread....

    public void metodoArrancarContadorConThread() {
        Thread miHiloThread = new Thread() {
            @Override
            public void run() {
                txtContador.setText("MODIFICADO");
            }
        };
        //Ejecuto el hilo por primera Y UNICA vez
        miHiloThread.start();
    }


    // Crea una tarea que modifica el textview,como un hilo normnal, con un Thread PERO 
    // LANZADO CON runOnUiThread()
    // En este caso el hilo se lanza internamente con un handler
    // que se cuida de que se lance en el hilo principal de UI
    public void metodoArrancarContadorConThreadYrunOnUiThread() {
        Thread miHiloThread = new Thread() {
            @Override
            public void run() {
                txtContador.setText("MODIFICADO");
            }
        };
        //Ejecuto el hilo por primera Y UNICA vez
        runOnUiThread(miHiloThread);
    }

    // Crea una tarea que modifica el textview, no como un hilo normnal, sino como un Runnable
    // El Runnable se puede luego ejecutar con la ayuda de un Handler
    // Con un Runnable consume menos recursos que con un Thread
    // ADemas con un handler puedo lanzar el hilo hasta de forma retardada
    // Ademas se asegura que se modifica la UI en el hilo principal
    void metodoArrancarContadorConRunnableYHandler() {
        miHiloRunnable = new Runnable() {
            @Override
            public void run() {
                contador++;
                txtContador.setText(String.valueOf(contador));
                // esto hace que se haga una llamada al hilo dentro de 1000 miliseg
                // como esta dentro del hilo, provoca realmente una llamada CADA 1000 miliseg
                handler.postDelayed(miHiloRunnable, 1000);

            }
        };
        //Ejecuto el hilo por primera vez
        handler.post(miHiloRunnable);
    }


    void metodoArrancarContadorConRunnableYExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        // con clase anónima interna          
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Las actividades en segundo plano van aquí...
                String mensaje = "NUEVO MENSAJE";
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Las actividades que afectan a la UI van aquí...
                        txtContador.setText(mensaje);
                    }
                });
            }
        });
    }


    public void metodoArrancaCountDownBasicoAndroid() {


        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                contador--;
                txtContador.setText(Long.toString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "La cuenta ha terminado", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();






    }

    public void metodoArrancaCountDownBasicoVersionJYOC() {
        AUX_ClockDownTimerConPause relojmio = new AUX_ClockDownTimerConPause(txtContador);

        btArrrancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relojmio.arrancar(60000);
            }
        });

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relojmio.continuar();
            }
        });

        btPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relojmio.pausa();
            }
        });




    }
}
