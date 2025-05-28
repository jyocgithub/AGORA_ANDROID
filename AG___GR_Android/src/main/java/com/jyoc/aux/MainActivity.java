package com.jyoc.aux;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.jyoc.aux.databinding.ActivityMainBinding;
import com.jyoc.pojos_para_guiasrapidas.Gato;
import com.jyoc.pojos_para_guiasrapidas.Perro;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding activityMainBinding;
    

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        
        
        
        
        //pedirVariosPermisos();

        //if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
        //    setTheme(R.style.darkTheme); // si esta configurado por defecto el tema oscuro, usamos el nuestro tambien
        //} else {
        //    setTheme(R.style.AppTheme);  // si no esta configurado por defecto el tema oscuro, usamos el tema estandar 
        //}
        
        // --- probar agregar views programaticamente 
        activityMainBinding.btAgregarViews.setOnClickListener(v -> {
            startActivity(new Intent(this, AUX_AgregarViewsProgramaticamenteActivity.class));
        });
        
        // --- probar alert dialogs 
        activityMainBinding.btAlertDialogs.setOnClickListener(v -> {
             AUX_AlertDialogs.snackBarConBoton(activityMainBinding.btAlertDialogs,"mensaje del snack");
            
        });
        
        // --- probar   
        activityMainBinding.btComunicaciones.setOnClickListener(v -> {
            
        });


        final boolean[] hayprogress = {false};
       
        // --- progress bar prueba   
        activityMainBinding.btProgress.setOnClickListener(v -> {
            if(hayprogress[0]){
                
                AUX_ProgressBar.cancelarProgressBarProgramaticamente(this);
            }else{
                ConstraintLayout root_layout = findViewById(R.id.gut_root_layout);
                AUX_ProgressBar.crearProgressBarProgramaticamente(this,root_layout);
            }
            hayprogress[0] = !hayprogress[0];
            
        });
        
        
        // TEXTINPUTLAYOUT
        
        // --- validar el contenido del TextInputLayout segun se escribe en el
        activityMainBinding.etEjemplo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()<8){
                    activityMainBinding.etEjemplo.setError("la password aun es muy corta");
                }else{
                    activityMainBinding.etEjemplo.setError(null);
                    
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        
        // --- validar el contenido del TextInputLayout con un boton 
        activityMainBinding.btValidarTextInputLayout.setOnClickListener(v -> {
                String dato = activityMainBinding.etEjemplo.getText().toString().trim();
                if(dato.isEmpty()){
                    activityMainBinding.etEjemplo.setError("Campo Requerido");
                    activityMainBinding.etEjemplo.requestFocus();
                }else if(dato.length()<5){
                    activityMainBinding.etEjemplo.setError("Necesita mínimo 5 letras");
                    activityMainBinding.etEjemplo.requestFocus();
                } else{
                    Toast.makeText(this, "Texto válido", Toast.LENGTH_SHORT).show();
                    // hacer lo que haya que hacer.... 
                }
        });
        
        
  
        // PARCELABLE SERIALIZABLE ETC
        
        activityMainBinding.btImagen.setOnClickListener(v->{
            Perro p = new Perro(new ArrayList<>(Arrays.asList("gato", "polen")), "paco", 12);
            Gato g = new Gato(new ArrayList<>(Arrays.asList("perro", "polen")), "blas", 22);
            
            
            
            Intent i = new Intent(this, SegundaActivity.class);
            i.putExtra("can", p);
            i.putExtra("cat", g.convertirAJson());
            
            startActivity(i);
            
            
            
            
        });
        
  
    
    }


    //
    //
    //// +---------------------------------------------+
    //// |                                             |
    //// |                                             |
    //// |       PEDIR VARIOS PERMISOS                 |
    //// |                                             |
    //// |                                             |
    //// +---------------------------------------------+
    //
    //public void pedirVariosPermisos() {
    //    // NO OLVIDAR AÑADIR LOS PERMISOS EN EL MANIFEST, ANTES DE <application>, ALGO ASI:
    //    //  <uses-permission android:name="android.permission.READ_CONTACTS" />
    //    // lista completa de constantes java y de manifest en : https://developer.android.com/reference/android/Manifest.permission
    //    final String[] ARR_PERMISOS = {
    //            Manifest.permission.INTERNET,
    //            Manifest.permission.SEND_SMS,
    //            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //            Manifest.permission.READ_EXTERNAL_STORAGE,
    //            Manifest.permission.CALL_PHONE
    //    };
    //
    //    ActivityResultLauncher<String[]> miARL_pedirVariosPermisos =
    //            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
    //                @Override
    //                public void onActivityResult(Map<String, Boolean> isGranted) {
    //
    //                    Log.d("PedirPermisos", "-- respuesta del activity result MULTIPLE:" + isGranted.toString());
    //                    if (isGranted.containsValue(false)) {
    //                        Log.d("PedirPermisos", "NO SE han aceptado los permisos");
    //                        accionesSinPermisos();
    //                    } else {
    //                        // Nos han concedido los permisos..... seguimos adelante
    //                        Log.d("PedirPermisos", "SI SE han aceptado los permisos");
    //                        accionesConPermisos();
    //                    }
    //                }
    //            });
    //
    //    // lanzar la peticion (en un listener, si se quiere)
    //    if (!tieneYaEstosPermisos(ARR_PERMISOS)) {
    //        Log.d("PedirPermisos", "Pedimos permisos");
    //        miARL_pedirVariosPermisos.launch(ARR_PERMISOS);
    //    } else {
    //        Log.d("PedirPermisos", "Ya tenemos todos los permisos...");
    //        accionesConPermisos();
    //    }
    //}
    //
    //private boolean tieneYaEstosPermisos(String[] permisos) {
    //    if (permisos != null) {
    //        for (String cadaPermiso : permisos) {
    //            if (ActivityCompat.checkSelfPermission(this, cadaPermiso) != PackageManager.PERMISSION_GRANTED) {
    //                Log.d("PedirPermisos", "Este permiso no lo tenemos: " + cadaPermiso);
    //                return false;
    //            }
    //            Log.d("PedirPermisos", "Este permiso ya lo tenemos: " + cadaPermiso);
    //        }
    //        return true;
    //    }
    //    return false;
    //}
    //public void accionesConPermisos(){
    //}
    //public void accionesSinPermisos(){
    //}

}

