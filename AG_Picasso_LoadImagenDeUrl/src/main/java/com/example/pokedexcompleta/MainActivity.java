package com.example.pokedexcompleta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/*
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'org.jsoup:jsoup:1.14.3'
    implementation 'com.squareup.picasso:picasso:2.71828'
*/
public class MainActivity extends AppCompatActivity {

    //Jsoup ---> JSON.
    URL urlAConsumir;
    ArrayList<Pokemon> pokemons = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    static ArrayList<String> urlsImg = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //pre-ejecución
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listadoPkm);
        URL urlAConsumir = null;
        try {
            urlAConsumir = new URL("https://pokeapi.co/api/v2/pokemon/?limit=8");
            HttpURLConnection connection = (HttpURLConnection) urlAConsumir.openConnection();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream iS = connection.getInputStream();
                        InputStreamReader iSR = new InputStreamReader(iS);
                        BufferedReader bf = new BufferedReader(iSR);
                        String linea = "";
                        String resultado = "";
                        while ((linea = bf.readLine()) != null) {
                            resultado += linea;
                        }
                        Log.i("RESULTADO", resultado);
                        //Parsea todoel resultado a formato JSON.
                        JSONObject jsonResultado = new JSONObject(resultado);
                        JSONArray array = jsonResultado.getJSONArray("results");
                        //El for va de 0 a 897 (todos los pkm)
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject cadaPokemon = array.getJSONObject(i);
                            nombres.add(cadaPokemon.getString("name"));
                            pokemons.add(new Pokemon(nombres.get(i)));
                            System.out.println(nombres.get(i));
                            urlsImg.add("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (i + 1) + ".png");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    //post-ejecución
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomAdapter adapter = new CustomAdapter(pokemons, MainActivity.this);
                            listView.setAdapter(adapter);
                        }
                    });
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // //Jsoup ---> JSON.
    // ArrayList<Pokemon> pokemons = new ArrayList<>();
    // ArrayList<String> nombres = new ArrayList<>();
    // static ArrayList<String> urlsImg = new ArrayList<>();

    // ListView listView;

    // @Override
    // protected void onCreate(Bundle savedInstanceState) {
    //     super.onCreate(savedInstanceState);
    //     setContentView(R.layout.activity_main);
    //     listView = findViewById(R.id.listadoPkm);

    //     //---------PRE-EJECUCIÓN.
    //     new Thread(new Runnable() {
    //         @Override
    //         public void run() {
    //             try {
    //                 Document resCompleto = Jsoup.connect("https://www.pokemon.com/es/pokedex/").get();
    //                 nombres = (ArrayList<String>) resCompleto.select("[href^=/es/pokedex]").eachText();

    //                 nombres.remove(0);//En esta pagina el elemento 0 no nos vale

    //                 for (int i = 0; i < nombres.size(); i++) {
    //                     String numPkm = String.format("%03d", i + 1);
    //                     //Conformar lista de URLS.
    //                     urlsImg.add("https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + numPkm + ".png");
    //                     pokemons.add(new Pokemon(nombres.get(i)));//Conformar la lista de nombres.
    //                 }
    //             } catch (IOException e) {
    //                 e.printStackTrace();
    //             }
    //             //--------POST-EJECUCIÓN.
    //             runOnUiThread(new Runnable() {
    //                 @Override
    //                 public void run() {
    //                     CustomAdapter adapter = new CustomAdapter(pokemons, MainActivity.this);
    //                     listView.setAdapter(adapter);
    //                 }
    //             });
    //         }
    //     }).start();

    // }






}