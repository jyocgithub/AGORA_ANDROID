package com.jyoc.aux;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AUX_Hilos {
    
    // =========================================================================
    // ======= ELECCION DE UNA OPCION : REGLAS : ===============================
    //
    //  - SI NO SE NECESITA ACTUALIZAR LA UI DENTRO DEL HILO (PUEDE SER AL ACABARLO):
    //       USAR EXECUTOR  (PERMITE ADEMAS CONTROLAR EL FIN DEL HILO Y MODIFICAR LA UI ENTONCES) 
    //
    //  - SI SE NECESITA ACTUALIZAR LA UI DENTRO DEL HILO, Y NO SE CONTROLA EL FIN DEL HILO
    //       USAR RUNONUITHREAD     (SI ES UNA ACTIVIDAD O HAY REF DE UNA ACTIVIDAD)
    //       
    //  - SI SE NECESITA ACTUALIZAR LA UI DENTRO DEL HILO, SIN ESTAR EN UNA ACTIVIDAD
    //       USAR RUNNABLE+HANDLER  (PERO NO SE CONTROLA BIEN EL FIN DEL HILO)
    //
    // =========================================================================
    
    /**
     * USANDO EXECUTOR
     * - SE PERMITE AÑADIR CLARAMENTE CODIGO CUANDO PARA CUANDO HAYA TERMINADO EL HILO
     * - SOLO PERMITE MODIFICAR LA UI CUANDO EL HILO HA TERMINADO, NO DURANTE SU EJECUCION
     * @param objetoenviado
     */
    public void lanzarUnHiloConExecutor(Deportista objetoenviado){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Contenido del hilo : NO SE PUEDE modificar la UI en este area
                Deportista objetoRecibidoEnElHilo = objetoenviado;

            handler.post(() -> {
                // La ejecucion del hilo se ha terminado
                // Se puede modificar la UI aquí (equivalente a onPostExecute de asynctask) 

            });
        });

    }

    /**
     * USANDO RUNNABLE+HANDLER
     * - PERMITE MODIFICAR LA UI DESDE UN HILO SECUNDARIO, DURANTE LA EJECICION DEL HILO PRINCIPAL
     * - NO SE PERMITE MODIFICAR LA UI CUANDO EL HILO HA TERMINADO
     * - NO ESTA CLARO CUANDO TERMINA EL HILO
     * @param objetoenviado
     */
    public void lanzarUnHiloConHandler( Deportista objetoenviado){
        
        
        new Thread(new Runnable() {
            @Override
            public void run() {
            // Contenido del hilo : NO SE PUEDE modificar la UI en este area
                // ...
                Deportista objetoRecibidoEnElHilo = objetoenviado;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                // Publicar una tarea en el hilo principal para modificar la UI
                    @Override
                    public void run() {
                // Se puede modificar la UI aquí 
                        // ...
                    }
                });
                
                // La ejecucion del hilo se termina en esta zona... 
                // pero aqui no se puede modificar la UI
            }
        }).start();
    }
    
    /**
     * USANDO RUNONUITHREAD
     * - NO SE PUEDE USAR DIRECTAMENTE EN UN FRAGMENT O SERVICE, NECESITAS UNA REFERENCIA DE UNA ACTIVITY
     * - PERMITE MODIFICAR LA UI DESDE UN HILO SECUNDARIO, DURANTE LA EJECUCION DEL HILO PRINCIPAL
     * - NO SE PERMITE MODIFICAR LA UI CUANDO EL HILO HA TERMINADO
     * - NO ESTA CLARO CUANDO TERMINA EL HILO
     * @param objetoenviado
     */
    public void lanzarUnHiloConRunonuithread(Deportista objetoenviado, Activity refActivity){
        new Thread(new Runnable() {
            @Override
            public void run() {
            // Contenido del hilo : NO SE PUEDE modificar la UI en este area
                // ...
                Deportista objetoRecibidoEnElHilo = objetoenviado;

                refActivity.runOnUiThread(new Runnable() {
                // Publicar una tarea en el hilo principal para modificar la UI
                    @Override
                    public void run() {
                // Se puede modificar la UI aquí 
                        // ...
                    }
                });
                
                // La ejecucion del hilo se termina en esta zona...
                // pero aqui no se puede modificar la UI
            }
        }).start();
    }
    
    
    
    


    /**
     * USANDO ASYNCTASK
     * - PERMITE MODIFICAR LA UI DESDE UN HILO SECUNDARIO
     * - SE PERMITE AÑADIR CLARAMENTE CODIGO CUANDO PARA CUANDO HAYA TERMINADO EL HILO
     * - ESTA OBSOLETO (DEPRECATED)
     * @param objetoenviado
     */
    public void lanzarUnHiloConAsyncTask( Deportista objetoenviado){
        
        new AsyncTask<Deportista, Void, Void>() {

            @Override
            protected Void doInBackground(Deportista... misEntidades) {
                // Aqui se pueden añadir tareas que afecten a la UI, 
                // pues se permite su modificacion
                
                // Ademas se reciben los elementos enviados en el execute() en un array
                Deportista objetoRecibidoEnElHilo = misEntidades[0];
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // La ejecucion del hilo se ha terminado
                
            }
        }.execute(objetoenviado);
        
    }

}

class Deportista{}
