package com.alvaroa.ag_drawfinger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DrawActivity extends AppCompatActivity {

    private Button button_save;
    private GestureOverlayView gesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        
        gesture =  findViewById(R.id.gestures);
        button_save =findViewById(R.id.save_button);

        button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    Bitmap gestureImg = gesture.getGesture().toBitmap(100, 100,
                            2, Color.BLACK);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    gestureImg.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bArray = bos.toByteArray();

                    Intent intent = new Intent(DrawActivity.this, ResultadoActivity.class);

                    intent.putExtra("draw", bArray);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DrawActivity.this, "No draw on the string",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}