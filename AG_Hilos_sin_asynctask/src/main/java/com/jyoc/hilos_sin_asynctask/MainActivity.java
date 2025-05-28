package com.jyoc.hilos_sin_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.jyoc.jga_hilos_sin_asynctask.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    MainActivity mainActivity;
    int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        siHayTrabajoEnLaUI();
    }


    public void siHayTrabajoEnLaUI() {

        // ==========================================
        //          CUANDO SI HAY TRABAJO EN LA UI
        // ==========================================

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // -------------- OPCION 1 : SIN LAMBDA
        executor.execute(() -> {
            // AQUI VAN LAS ACCIONES DEL HILO EN SEGUNDO PLANO
            // ===============================================
            try {
                while (true) {
                    Thread.sleep(2000);

                    // AQUI VAN UNA LLAMADA A UNA ACCION EN LA UI
                    // ==========================================
                    // Mejor llamar a un método donde SI se pueda acceder a 
                    // todas las variables de la clase principal
                    mainActivity.runOnUiThread(() -> {
                        accionesUIDelHilo();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // -------------- OPCION 2 : SIN LAMBDA
        // lo mismo sin lambdas
        //executor.execute(new Runnable() {
        //    @Override
        //    public void run() {
        //        try {
        //            while (true) {
        //                Thread.sleep(2000);
        //                mainActivity.runOnUiThread(() -> {
        //                        //mejor llamar a un método donde SI se pueda acceder a todas las variables de la clase principal
        //                        accionesUIDelHilo();
        //                });
        //            }
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});

    }

    public void accionesUIDelHilo() {
        Toast.makeText(getApplicationContext(), "Contador : " + contador, Toast.LENGTH_SHORT).show();
        contador++;
    }


    // ==========================================
    //           CUANDO NO HAY TRABAJO EN LA UI
    // ==========================================

    public void siNoHayTrabajoEnLaUI() {

        // -------------- OPCION 1 : SIN LAMBDA
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        Runnable hilo1 = new Runnable() {
            @Override
            public void run() {
                accionesDelHilo();
            }
        };
        executor1.execute(hilo1);
        //-- y para cerrarlo
        executor1.shutdown();


        // -------------- OPCION 2 : CON LAMBDA
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(() -> accionesDelHilo());
        //-- y para cerrarlo
        executor2.shutdown();


        // -------------- OPCION 3 : podemos añadir un pool de hilos
        ExecutorService poolexecutors = Executors.newFixedThreadPool(3); // ESTO CREA UN POOL DE 3 HILOS
        poolexecutors.execute(() -> accionesDelHilo());
        poolexecutors.execute(() -> accionesDelHilo());
        poolexecutors.execute(() -> accionesDelHilo());

    }

    public void accionesDelHilo() {

    }

}
