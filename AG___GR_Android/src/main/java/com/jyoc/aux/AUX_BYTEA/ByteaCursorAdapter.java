package com.jyoc.elnotanemo23.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyoc.elnotanemo23.R;
import com.jyoc.elnotanemo23.actividades.MainActivity;
import com.jyoc.elnotanemo23.dao.GestorBD;
import com.jyoc.elnotanemo23.utilidades.JYMUtils;

import androidx.core.content.ContextCompat;

public class ByteaCursorAdapter extends CursorAdapter {

     //int[] colores = {Color.RED, Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.CYAN};

    public ByteaCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it, 
    // you don't bind any data to the view at this point. 
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cadafila, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView. 
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView fondo = view.findViewById(R.id.fondo);
        ImageView ivImagen = view.findViewById(R.id.ivFilaImagen);
        TextView tvtitulo = view.findViewById(R.id.tvFilaTitulo);
        TextView tvfecha = view.findViewById(R.id.tvFilaFecha);
        TextView tvContenido = view.findViewById(R.id.tvFilaContenido);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(GestorBD._ID));
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow(GestorBD._TITULO));
        //String FFF = cursor.getString(cursor.getColumnIndexOrThrow(GestorBD.FECHA));
        long fechamillis = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(GestorBD._FECHA)));
        String fecha = JYMUtils.de_MILISECONDS_a_EUROPEANSTRING(fechamillis);
        String categoriaactual = cursor.getString(cursor.getColumnIndexOrThrow(GestorBD._CATEGORIA));
        String contenido = cursor.getString(cursor.getColumnIndexOrThrow(GestorBD._CONTENIDO));
        tvtitulo.setText(titulo);
        tvfecha.setText(fecha);
        if (contenido.contains("\n")) {
            contenido = contenido.substring(0, contenido.indexOf("\n"));
        }
        if (contenido.length() < 35) {
            tvContenido.setText(contenido);
        } else {
            tvContenido.setText(contenido.substring(0, 35) + "...");
        }
        
        int indicecategoria = Integer.parseInt(categoriaactual);
        Bitmap bitmap;
        //Bitmap bitmap = JYMUtils.createLetterBitmap(Color.DKGRAY,
        //        MainActivity.categorias_usuario[Integer.parseInt(categoriaactual)],
        //        Color.WHITE,
        //        30);

        //fondo.setBackgroundColor(COLORESCATEGORIAS[indicecategoria]);
        switch (categoriaactual) {
            case "0":
                bitmap = MainActivity.ICONOS_CATEGORIA[0];
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoazul));
                break;
            case "1":
                bitmap = MainActivity.ICONOS_CATEGORIA[1];
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoverde));
                break;
            case "2":
                bitmap = MainActivity.ICONOS_CATEGORIA[2];
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadorosa));
                break;
            case "3":
                bitmap = MainActivity.ICONOS_CATEGORIA[3];
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadomorado));
                break;
            case "4":
                bitmap = MainActivity.ICONOS_CATEGORIA[4];
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadorojo));
                break;
            default:
                    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadomarron));
                bitmap = MainActivity.ICONOS_CATEGORIA[5];
                break;
        }

        ivImagen.setImageBitmap(bitmap);

        //if (MainActivity.ICONO_letra) {
        //    switch (categoria.toLowerCase()) {
        //        case "privado":
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoazulmuyoscuro));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_p_azuloscuro3));
        //            break;
        //        case "familia":
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoverde));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_f2));
        //            break;
        //        case "amigos":
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadorosa));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_a2));
        //            break;
        //        case "trabajo":
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadomorado));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_t2));
        //            break;
        //        case "hecho":
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadorojo));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_h2));
        //            break;
        //        default:
        //            fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoazul));
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.letra_o2));
        //            break;
        //    }
        //} else {
        //    fondo.setBackground(ContextCompat.getDrawable(context, R.drawable.fondoredondeadoazul));
        //    switch (categoria.toLowerCase()) {
        //        case "privado":
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_p));
        //            break;
        //        case "familia":
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_f));
        //            break;
        //        case "amigos":
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_a));
        //            break;
        //        case "trabajo":
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_t));
        //            break;
        //        case "hobby":
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_h));
        //            break;
        //        default:
        //            ivImagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mix_o));
        //            break;
        //    }
        //}

    }


}
