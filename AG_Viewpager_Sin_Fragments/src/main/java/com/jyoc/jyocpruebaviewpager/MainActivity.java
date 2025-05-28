package com.jyoc.jyocpruebaviewpager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    ViewPager elViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elViewPager = findViewById(R.id.vpPrincipal);
        
        MiPageAdapter miAdaptador  = new MiPageAdapter(this);
       
        elViewPager.setAdapter(miAdaptador);
        
        elViewPager.setCurrentItem(1);
    }

    public void onSiguiente(View v) {
        int numeroDePantallasDelAdapter = elViewPager.getAdapter().getCount();
        if (elViewPager.getCurrentItem() == numeroDePantallasDelAdapter - 1) {
            elViewPager.setCurrentItem(0);
        } else {
            elViewPager.setCurrentItem(elViewPager.getCurrentItem() + 1);
        }
    }
}