package com.android.jyocguion_sensores;


import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Para obtener una direccion, se necesita ante nada que en el Manifest este incluido:
 *         <meta-data android:name="com.google.android.gms.version"
 *             android:value="@integer/google_play_services_version" />
 *         <service
 *             android:name=".AveriguarDireccionIntentService"
 *             android:exported="false"/>
 */
public class ObtenerDireccionIntentService extends IntentService {

    Location localizacionactual;
    
    
    
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ObtenerDireccionIntentService(String name, Location localizacionactual) {
        super(name);
        this.localizacionactual = localizacionactual;
    }

    @Override protected void onHandleIntent(Intent intent) {
        // locale.getdefault obtiene la zona geográfica y lingüística configurada por el usuario
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    localizacionactual.getLatitude(), localizacionactual.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
