package com.jyoc.jyocpruebaviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import androidx.viewpager.widget.ViewPager;

public class MiPageAdapter extends PagerAdapter {
    private final int NUM_PAGINAS = 3;
    private Context mContext;
    ConstraintLayout mPantalla1,mPantalla2,mPantalla3;
    
    public MiPageAdapter(Context context) {
        this.mContext = (MainActivity)context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View pantallaCreada = null;
        switch (position) {
            case 0:
                if (mPantalla1 == null) {
                    mPantalla1 = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.pantalla1, null);
                }
                pantallaCreada = mPantalla1;
                break;
            case 1:
                if (mPantalla2 == null) {
                    mPantalla2 = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.pantalla2, null);
                }
                pantallaCreada = mPantalla2;
                break;
            case 2:
                if (mPantalla3 == null) {
                    mPantalla3 = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.pantalla3, null);
                }
                pantallaCreada = mPantalla3;
                break;
        }
        ViewPager vp = (ViewPager) collection;
        vp.addView(pantallaCreada, 0);
        return pantallaCreada;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View collection, int position, Object view)
    {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public int getCount() {
        return NUM_PAGINAS;
    }


}
