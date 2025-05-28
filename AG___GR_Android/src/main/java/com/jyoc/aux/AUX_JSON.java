package com.jyoc.aux;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AUX_JSON {



    public static String leerJSONDeAssets(Context c, String nombreFichero) {
        String json = null;
        try {
            InputStream is = c.getAssets().open(nombreFichero);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static ArrayList<Libro> creaArrayListDeUnJson(Context contexto, String nombreFichero) {
        String json = leerJSONDeAssets(contexto, nombreFichero);
        ArrayList<Libro> listafinal = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray couchList = jsonObject.getJSONArray("lista_libros");
            for (int i = 0; i < couchList.length(); i++) {
                JSONObject objeto = couchList.getJSONObject(i);
                String nombre = objeto.getString("nombre");
                String autor = objeto.getString("autor");
                String editorial = objeto.getString("editorial");
                int paginas = objeto.getInt("paginas");
                String imagen = objeto.getString("imagen");
                //Libro l = new Libro(nombre, autor, editorial, paginas,imagen);
                //listafinal.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listafinal;
    }


}


class Libro {

}