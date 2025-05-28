package com.alvaroa.ag_drawfinger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.ByteArrayInputStream;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);


        ImageView image = (ImageView) findViewById(R.id.image_saved);

        ByteArrayInputStream imageStreamClient = new ByteArrayInputStream(
                getIntent().getExtras().getByteArray("draw"));
        image.setImageBitmap(BitmapFactory.decodeStream(imageStreamClient));
    }
}