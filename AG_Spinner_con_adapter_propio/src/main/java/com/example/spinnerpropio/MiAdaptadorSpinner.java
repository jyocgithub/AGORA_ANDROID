package com.example.spinnerpropio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MiAdaptadorSpinner extends ArrayAdapter<IconosSpinner> {

    LayoutInflater layoutInflater;

    public MiAdaptadorSpinner(@NonNull Context context, int resource, @NonNull List<IconosSpinner> iconoSpinner) {
        super(context, resource, iconoSpinner);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View filaView = layoutInflater.inflate(R.layout.img_spinner, null, true);

        IconosSpinner iconoSpinner = getItem(position);

        TextView textView =  filaView.findViewById(R.id.nombreTextView);
        ImageView imagenView = filaView.findViewById(R.id.imgRolito);

        textView.setText(iconoSpinner.getNombre());
        imagenView.setImageResource(iconoSpinner.getImagenIcono());

        return filaView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.img_spinner, parent, false);
        }
      
        IconosSpinner iconoSpinner = getItem(position);
        
        TextView textView = convertView.findViewById(R.id.nombreTextView);
        ImageView imagenView = convertView.findViewById(R.id.imgRolito);
        
        textView.setText(iconoSpinner.getNombre());
        imagenView.setImageResource(iconoSpinner.getImagenIcono());
        
        return convertView;

    }
    

    
    
}
