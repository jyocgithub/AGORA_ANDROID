package com.jyoc.jga_agora_consumir_apis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consumirApi();
        
    }
    
    
    
    public void consumirApi(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://free-nba.p.rapidapi.com/players?page=0&per_page=25");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("x-rapidapi-host", "free-nba.p.rapidapi.com");
                    connection.setRequestProperty("x-rapidapi-key", "7083388694msh64add70d5deb7bfp1c9cfajsn116b33f64e71");
                    InputStream iS = connection.getInputStream();
                    InputStreamReader iSR = new InputStreamReader(iS);
                    BufferedReader bf = new BufferedReader(iSR);
                    String linea = "";
                    String resultado = "";
                    while ((linea = bf.readLine()) != null) {
                        resultado += linea;
                    }
                    JSONObject JSONResultado = new JSONObject(resultado);
                    JSONArray array = JSONResultado.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println(array.getJSONObject(i).getString("first_name"));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //public void consumirApi(){
    //    String total = "";
    //    new Thread(new Runnable()
    //    {
    //        @Override
    //        public void run() //backEnd
    //        {
    //            try {
    //                URL url = new URL("https://www.listchallenges.com/100-places-to-visit");
    //                URLConnection connection = url.openConnection();
    //                InputStream iS = connection.getInputStream();
    //                InputStreamReader isR = new InputStreamReader(iS);
    //                BufferedReader bf = new BufferedReader(isR);
    //                String linea = "";
    //                while ( (linea = bf.readLine() ) != null )
    //                {
    //                    total += linea + "\n";
    //                }
    //            }
    //            catch (IOException e)
    //            {
    //                e.printStackTrace();
    //            }
    //            runOnUiThread(new Runnable() //frontEnd
    //            {
    //                @Override
    //                public void run()
    //                {
    //                    Intent i = new Intent(MainActivity.this, MainActivity.class);
    //                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0, i, 0);
    //                    TextoHtml.setText(total);
    //                    dialog.dismiss(); //Dejar de mostrar el dialogo
    //                    builder = new NotificationCompat.Builder(MainActivity.this,"0")
    //                            .setSmallIcon(android.R.drawable.stat_sys_download_done)
    //                            .setContentTitle("Descarga de datos completada")
    //                            .setContentText("Haz click para ver resultado")
    //                            .setAutoCancel(true)
    //                            .setContentIntent(pendingIntent);
    //
    //                    manager.notify(0,builder.build());
    //                }
    //            });
    //        }
    //    }).start();
    //
    //}
}
