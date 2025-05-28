package com.jyoc.elnotanemo23.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyoc.elnotanemo23.R;
import com.jyoc.elnotanemo23.actividades.MainActivity;

/*
// === Ejemplo de uso ======================================================= 
// ========================================================================== 

   // Crear y configurar el adaptador CON IMAGENES
   ByteaSpinnerAdapter adapter = new ByteaSpinnerAdapter(
             this, 
             items, 
             MainActivity.ICONOS_CATEGORIA);
   spinnerejemplo.setAdapter(adapter);
   
   // Crear y configurar el adaptador SIN IMAGENES
   ByteaSpinnerAdapter adapter = new ByteaSpinnerAdapter(
             this, 
             items, 
             null);
   spinnerejemplo.setAdapter(adapter);

*/

public class ByteaSpinnerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] items;
    private final Bitmap[] bitmaps;
    private final int PADDING = 8; // Padding en dp
    private final int PADDING_INTERIOR = 8; // Padding en dp
    private final int DIVIDER_HEIGHT = 1; // Altura del divisor en dp
    private final String bordercolor = "#E91E63"; // color de marco y divisores

    public ByteaSpinnerAdapter(Context context, String[] items, Bitmap[] bitmaps) {
        super(context, R.layout.spinner_item, items);
        this.context = context;
        this.items = items;
        this.bitmaps = bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getCustomView(position, convertView, parent);

        // Aplicar borde al elemento seleccionado
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.BLACK);
        shape.setStroke(dpToPx(2), Color.parseColor(bordercolor));
        shape.setCornerRadius(dpToPx(8));
        view.setBackground(shape);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Crear contenedor para el elemento y su divisor
        LinearLayout itemContainer = new LinearLayout(context);
        itemContainer.setOrientation(LinearLayout.VERTICAL);
        itemContainer.setBackgroundColor(Color.BLACK);

        long anchototal = parent.getWidth();
        double paddinlateral = 50;


        // crear divisor superior e inferior
        View divider1 = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dpToPx(DIVIDER_HEIGHT)
        );
        params.width = (int) (parent.getWidth() * 0.8f); // 70% del ancho del padre
        params.setMargins(dpToPx(PADDING), 0, dpToPx(PADDING), 0);
        divider1.setLayoutParams(params);
        divider1.setBackgroundColor(Color.parseColor(bordercolor));

        // crear divisor intermedio
        View divider2 = new View(context);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dpToPx(DIVIDER_HEIGHT)
        );
        params2.width = (int) (parent.getWidth() * 0.8f); // 70% del ancho del padre
        //params2.setMargins(dpToPx(PADDING), 0, dpToPx(PADDING), 0);
        params2.setMargins(dpToPx(PADDING), 0, dpToPx(PADDING), 0);
        //params2.setMargins(dpToPx((int) paddinlateral), 0, dpToPx((int) paddinlateral), 0);
        divider2.setLayoutParams(params2);
        divider2.setBackgroundColor(Color.parseColor(bordercolor));


        // Añadir divisor si es el primer elemento
        if (position == 0) {
            itemContainer.addView(divider1);
        }

        // Añadir la vista del VALOR del elemento
        View itemView = getCustomView(position, convertView, parent);
        itemContainer.addView(itemView);

        // Añadir divisor el resto de elementos menos al ultimo
        if (position < items.length - 1) {
            itemContainer.addView(divider2);
        }

        // Añadir divisor al ultimo elemento
        if (position == items.length - 1) {
            itemContainer.addView(divider1);
        }

        return itemContainer;
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, parent, false);

        // Configurar el contenedor principal
        LinearLayout container = row.findViewById(R.id.container);
        container.setBackgroundColor(Color.BLACK);
        container.setPadding(dpToPx(PADDING_INTERIOR), dpToPx(PADDING_INTERIOR), dpToPx(PADDING_INTERIOR), dpToPx(PADDING_INTERIOR));

        // Configurar la imagen
        if (bitmaps != null) {

            ImageView imageView = row.findViewById(R.id.spinnerImage);
            imageView.setImageBitmap(bitmaps[position]);
        }

        // Configurar el texto
        TextView textView = row.findViewById(R.id.spinnerText);
        textView.setText(items[position]);
        textView.setTextColor(Color.GREEN);

        return row;
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
//
//public class ByteaSpinnerAdapter extends ArrayAdapter<String> {
//    private final Context context;
//    private final String[] items;
//    private final Bitmap[] bitmaps;
//    private final int ITEM_HEIGHT = 120; // Altura de cada elemento en dp
//    private final int DIVIDER_HEIGHT = 1; // Altura del divisor en dp
//    private final int PADDING = 16; // Padding en dp
//
//    public ByteaSpinnerAdapter(Context context, String[] items, Bitmap[] bitmaps) {
//        super(context, R.layout.spinner_item, items);
//        this.context = context;
//        this.items = items;
//        this.bitmaps = bitmaps;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return getCustomView(position, convertView, parent, false);
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        // Crear un contenedor para el elemento y su divisor
//        LinearLayout itemContainer = new LinearLayout(context);
//        itemContainer.setOrientation(LinearLayout.VERTICAL);
//        itemContainer.setBackgroundColor(Color.BLACK);
//
//        // Añadir la vista del elemento
//        View itemView = getCustomView(position, convertView, parent, true);
//        itemContainer.addView(itemView);
//
//        // Añadir divisor si no es el último elemento
//        if (position < items.length - 1) {
//            View divider = new View(context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    dpToPx(DIVIDER_HEIGHT)
//            );
//            params.setMargins(dpToPx(PADDING * 2), dpToPx(4), dpToPx(PADDING * 2), dpToPx(4));
//            divider.setLayoutParams(params);
//            divider.setBackgroundColor(Color.YELLOW);
//            itemContainer.addView(divider);
//        }
//
//        // Si es el primer elemento, aplicar el borde superior redondeado
//        if (position == 0) {
//            GradientDrawable topShape = new GradientDrawable();
//            topShape.setShape(GradientDrawable.RECTANGLE);
//            topShape.setColor(Color.BLACK);
//            topShape.setStroke(dpToPx(2), Color.YELLOW);
//            topShape.setCornerRadii(new float[]{
//                    dpToPx(8), dpToPx(8),  // Top-left corner
//                    dpToPx(8), dpToPx(8),  // Top-right corner
//                    0, 0,                  // Bottom-right corner
//                    0, 0                   // Bottom-left corner
//            });
//            itemContainer.setBackground(topShape);
//        }
//        // Si es el último elemento, aplicar el borde inferior redondeado
//        else if (position == items.length - 1) {
//            GradientDrawable bottomShape = new GradientDrawable();
//            bottomShape.setShape(GradientDrawable.RECTANGLE);
//            bottomShape.setColor(Color.BLACK);
//            bottomShape.setStroke(dpToPx(2), Color.YELLOW);
//            bottomShape.setCornerRadii(new float[]{
//                    0, 0,                  // Top-left corner
//                    0, 0,                  // Top-right corner
//                    dpToPx(8), dpToPx(8),  // Bottom-right corner
//                    dpToPx(8), dpToPx(8)   // Bottom-left corner
//            });
//            itemContainer.setBackground(bottomShape);
//        }
//        // Para los elementos intermedios, solo borde lateral
//        else {
//            GradientDrawable middleShape = new GradientDrawable();
//            middleShape.setShape(GradientDrawable.RECTANGLE);
//            middleShape.setColor(Color.BLACK);
//            middleShape.setStroke(dpToPx(2), Color.YELLOW);
//            //itemContainer.setBackground(middleShape);
//        }
//
//        return itemContainer;
//    }
//
//    private View getCustomView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.spinner_item, parent, false);
//
//        // Configurar el contenedor principal
//        LinearLayout container = row.findViewById(R.id.container);
//
//        // Solo aplicar el borde si no es un elemento del dropdown
//        if (!isDropDown) {
//            GradientDrawable shape = new GradientDrawable();
//            shape.setShape(GradientDrawable.RECTANGLE);
//            shape.setColor(Color.BLACK);
//            shape.setStroke(dpToPx(2), Color.YELLOW);
//            shape.setCornerRadius(dpToPx(8));
//            container.setBackground(shape);
//        }
//
//        container.setPadding(dpToPx(PADDING), dpToPx(PADDING), dpToPx(PADDING), dpToPx(PADDING));
//
//        // Configurar la imagen
//        ImageView imageView = row.findViewById(R.id.spinnerImage);
//        imageView.setImageBitmap(bitmaps[position]);
//
//        // Configurar el texto
//        TextView textView = row.findViewById(R.id.spinnerText);
//        textView.setText(items[position]);
//        textView.setTextColor(Color.GREEN);
//
//        return row;
//    }
//
//    private int dpToPx(int dp) {
//        float density = context.getResources().getDisplayMetrics().density;
//        return Math.round(dp * density);
//    }
//}