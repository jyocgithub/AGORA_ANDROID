package com.jyoc.aux;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


/*
     PROGRESS DIALOG ESTA DEPRECATED - USAR PROGRESSBAR 
 */
public class AUX_ProgressBar {

    static ProgressBar progressBar;

    public static ProgressBar crearProgressBarProgramaticamente(AppCompatActivity context, ConstraintLayout root_layout) {

        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);

        // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500, 600);
        // params.addRule(RelativeLayout.CENTER_IN_PARENT);

        // centrando el progressbar en el centro de la ventana
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;

        // añadir el progressbar a la ventana
        root_layout.addView(progressBar, layoutParams);

        // hacer visible el progressbar
        progressBar.setVisibility(View.VISIBLE);

        // color de la barra de progreso
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        //progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.MULTIPLY);
        //progressBar.getIndeterminateDrawable().setColorFilter(0xFF000000, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        // CUIDADO !!!  Asi se evita la interaccion del usuario con TODA LA VENTANA
        //context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        return progressBar;
    }

    public static void cancelarProgressBarProgramaticamente(AppCompatActivity context) {
        // -- se oculta asi
        progressBar.setVisibility(View.GONE);

        // -- volvemos a hacer la pantalla activa (si esytaba inactiva)
        //context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
