package com.jyoc.jga_animaciones;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    TextView texto;
    TranslateAnimation transAnim;
    RotateAnimation rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = findViewById(R.id.tvTexto);
        texto.setText("VAMOS A VOLAR");
        
        ejecutarTranslacion();
        
    }
    
    
    void ejecutarTranslacion()
    {
        //Definir anim.Traslacion
        transAnim = new TranslateAnimation(5000,0,0,0);
        transAnim.setDuration(3000); //Definir duracion animacion
        texto.setAnimation(transAnim);//Relacionar la animacion para el texto
        transAnim.start();//Lanzar la animacion
        transAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                ejecutarRotacionYEscala();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    void ejecutarRotacionYEscala()
    {
        AnimationSet set = new AnimationSet(false);

        ScaleAnimation scaleAnim = new ScaleAnimation(1,2,1,2,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnim = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        //Incluyo en el set ambas animaciones para que se ejecuten a la par
        set.addAnimation(rotateAnim);
        set.addAnimation(scaleAnim);

        //Establezco la duracion del set
        set.setDuration(3000);

        //Por lo tanto al texto le pongo como animacion el "set" al completo
        texto.setAnimation(set);
        set.start();

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                ejecutarAlpha();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    
    
    void ejecutarAlpha()
    {
        //Animacion alpha ---> transparencia.
        AlphaAnimation alphaAnim = new AlphaAnimation(1,0);
        alphaAnim.setDuration(3000);
        texto.setAnimation(alphaAnim);
        alphaAnim.start();
    }
}