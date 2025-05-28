package com.jyoc.firestoredesdecero.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.jyoc.firestoredesdecero.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import androidx.annotation.RequiresApi;

public class AUXAndroidImagenes {


    public static Bitmap getBitmapFromDrawable(Context context, int R_rawable_id) {
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), R_rawable_id);
        return imagenEnBmp;
    }

    public static Bitmap getBitmapFromImageView(Context context, ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap imagenEnBmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return imagenEnBmp;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e("JYOA-->", "ByteArrayOutputStream error");
                }
            }
        }
    }

    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getStringBase64FromBitmap(Context context) {
        // LIMITACION: La imagen no debe superar 1MB (es algo mas pero asi aseguramos)
        // LIMITACION: Solo vale con un MIN API 26 
        Bitmap imagenEnBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        byte[] byteArray = AUXAndroidImagenes.getByteArrayFromBitmap(imagenEnBmp);
        String imagenCodificadaComoBase64 = Base64.getEncoder().encodeToString(byteArray);
        // este String se mete en un pojo que tenga solo el string como atributp y 
        // se sube como un registro normal a firestore
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Bitmap getBitmapFromStringBase64(String imagenCodificadaComoBase64) {
        byte[] imagenEnByteArray = Base64.getDecoder().decode(imagenCodificadaComoBase64);
        Bitmap bmp = getBitmapFromByteArray(imagenEnByteArray);
        return bmp;
    }
    

}
