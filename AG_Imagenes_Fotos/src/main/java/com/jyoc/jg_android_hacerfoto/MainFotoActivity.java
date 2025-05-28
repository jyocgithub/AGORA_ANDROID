package com.jyoc.jg_android_hacerfoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jyoc.jg_android_hacerfoto.copiadeaux.AUX_Fotos_SIN_ARL;
import com.jyoc.jg_android_hacerfoto.copiadeaux.AUX_Imagenes;

import java.io.IOException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// +---------------------------------------------+
// |                                             |
// |  ****************************************   |
// |  ****************************************   |
// |  SE ESTAN USANDO CLASES DE OTROS MODULOS    |
// |  ****************************************   |
// |  ****************************************   |
// |                                             |
// +---------------------------------------------+

//  podemos importar clases de un módulo en otro módulo, 
//  siempre que se añada en el gradle
//     dependencies {
//         implementation project(':AG__GUT_Android')


public class MainFotoActivity extends AppCompatActivity {

    private Bundle savedInstanceState;
    AUX_Fotos_SIN_ARL hacerFotoClass;
    ImageView ivFoto;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_hacerfoto);
        ivFoto = findViewById(R.id.ivFoto);
    }

    // ============================   hacar fotot thumbnail
    public void onClickHacerFotoThumbnail(View view) throws IOException {
        AUX_Fotos_SIN_ARL.hacerFotoThumbnail(MainFotoActivity.this);
    }

    // ============================   hacar foto real (fichero)
    public void onClickHacerFotoFichero(View view) throws IOException {
        AUX_Fotos_SIN_ARL.hacerFotoCompleta(this, "nombrefoto", ".png");
    }

    // ============================   hacar screenshot
    public void onClickHacerScreenShot(View view) throws IOException {
        Bitmap bitmap = AUX_Fotos_SIN_ARL.tomarScreenShot(this);
        ivFoto.setImageBitmap(bitmap);
    }

    // ============================   sacar una imagen de un drawable por id 
    public void onClickCogerDrawablePorId(View view) throws IOException {
        Bitmap bitmap = AUX_Imagenes.bitmapFromDrawableConId(this, R.drawable.barbas);
        ivFoto.setImageBitmap(bitmap);
    }
    // ============================   sacar una imagen de un drawable por nombre 
    public void onClickCogerDrawablePorNombre(View view) throws IOException {
        String nombreimagen = "ppmonedero";
        Bitmap bitmap = AUX_Imagenes.bitmapFromDrawableConNombre(this, nombreimagen);
        ivFoto.setImageBitmap(bitmap);
    }
    // ============================   sacar una imagen de un asset por nombre 
    public void onClickCogerAssetPorNombre(View view) throws IOException {
        String nombreimagen = "pirata.png";
        Bitmap bitmap = AUX_Imagenes.bitmapFromAsset(this, nombreimagen);
        ivFoto.setImageBitmap(bitmap);
    }
    // ============================   sacar una imagen de internet 
    public void onClickCogerImagenDeInternet(View view) throws IOException {
        String urlDeLaFoto = "https://bit.ly/2uUlAzw";
        AUX_Imagenes.bajarFotoDeInternet(urlDeLaFoto, ivFoto);
       
    }

    //============================================================
    // ============================   RECOGER ACVITITY RESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = AUX_Fotos_SIN_ARL.onActivityResultDeHacerFoto(this,requestCode,resultCode,data);
        
        // Ejemplo de poner la foto en un imageview
        // CAMBIAR EL TAMAÑO DE UNA FOTO
        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 900, false);
        ivFoto.setImageBitmap(bitmap);
    }
    
    
 
    
}
  