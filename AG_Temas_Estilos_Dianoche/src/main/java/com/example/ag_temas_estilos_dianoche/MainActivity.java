package com.example.ag_temas_estilos_dianoche;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int MODODIA = 234;
    public static final int MODONOCHE = 765;
    private int modoactual = MODODIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ponerTemaAlInicioDeActividad();

        setContentView(R.layout.activity_main);

        Button btDiaNoche = findViewById(R.id.btDiaNoche);

        if (modoactual == MODODIA) {
            btDiaNoche.setText("PASAR A MODO NOCHE");
        } else {
            btDiaNoche.setText("PASAR A MODO DIA");
        }

        btDiaNoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modoactual == MODODIA) {
                    cambiarTema(MODONOCHE);
                } else {
                    cambiarTema(MODODIA);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.opcion_mododia):
                cambiarTema(MODODIA);
                break;
            case (R.id.opcion_modonoche):
                cambiarTema(MODONOCHE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cambiarTema(int nuevotema) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tema", nuevotema);
        editor.apply();
        //relanzarEstaActividad();
        rePintarEstaActividad();
    }

    public void ponerTemaAlInicioDeActividad() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        modoactual = prefs.getInt("tema", 123456);
        // Si es la primera vez que se ejecuta la aplicacion, usamos el tema por defecto del movil, dark o light (noche o dia)
        if (modoactual == 123456) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                modoactual = MODONOCHE;
            } else {
                modoactual = MODODIA;
            }
        }

        // ahora ponemos el tema elegido o el del sistema si es la primera vez
        if (modoactual == MODODIA) {
            setTheme(R.style.JYOCTemaDia);
            modoactual = MODODIA;
        } else {
            setTheme(R.style.JYOCTemaNoche);
            modoactual = MODONOCHE;
        }
    }

    public void relanzarEstaActividad() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void rePintarEstaActividad() {
        setTheme(R.style.JYOCTemaDia);
        ViewGroup vg = findViewById (R.id.idDelContenedorPrincipalDeMainLayout);
        vg.invalidate();
    }

}
