package com.example.ag_livedataviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//La clase abstracta LiveData representa una envoltura para datos que deseas observar dentro del ciclo de vida de un compnente.
// 
// Livedata es coherente con el ciclo de vida (LifecycleOwner) de un controlador (activity, fragment), esto es,
//el observer que contierne el ViewModwl solo notifica datos si el controlador-owner (activity, fragment, etc)
//está en estado activo (Lifecycle.State.STARTED y Lifecycle.State.RESUMED).
//        De esta forma, al usar LiveData podremos:
//
//        -Evitar leaks de memoria al dejar de recibir cambios de datos cuando el ciclo de vida se encuentra en inactividad
//        -Mantener la UI actualizada al cambiar los datos (la vista recibe actualizaciones de estado en vez de solicitarlas)
//        -Recibe los datos más nuevos incluso en cambios de configuración
//


public class LoginViewModel extends ViewModel {
    // se usa MutableLiveData cuando el valor que se almacena en el contenedor LiveData PUEDE MODIFICARSE
    // si el valor SOLO SERA DE CONSULTA, y no queremos que se modifique, podemos usar LiveData en vez de MutableLiveData
    private MutableLiveData<String> user;
    //private LiveData<String> user;

    // observar que si se hace static, y el correspondiente getter, 
    // pareceria que actua como un singleton, pero es que el LiveData es un singleton ya en si mismo,
    // el contenedor es comun y esta vivo para todas las actividades y respeta su ciclo de vida
    private MutableLiveData<String> password;

    public MutableLiveData<String> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
        }
        return password;
    }


}