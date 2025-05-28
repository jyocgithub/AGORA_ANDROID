package com.android.jyocguion_sensores;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;

public class MiSensorLocalizacion implements LocationListener {
    MainActivity mainactivity;
    String providerElegido = "";
    Location localizacionActual;
    LocationManager miLocationManager;
    String direccionactual = "";

    public MiSensorLocalizacion(MainActivity mainactivity) {
        this.mainactivity = mainactivity;
        miLocationManager = (LocationManager) mainactivity.getSystemService(Context.LOCATION_SERVICE);
      
        // para la primera localizacion
        primeraLocalizacion();
        
        // para las sucesivas localizaciones segun nos movemos
        crearSensor();
    }

    //  +-------------------------------------------------+
    //  |   PARA LA PRIMERA LOCALIZACION                  |
    //  |                                                 |
    //  +-------------------------------------------------+

    public void primeraLocalizacion() {
        //  ********** ELEGIR AHORA Y AQUI UNA OPCION
        //  --- podemos hacer que elija android un proveedor o elegirlo nosotros;
        providerElegido = elegirNosotrosUnProveedorDeLocalizacion();
        //providerElegido = dejarQueAndroidElijaUnProveedorDeLocalizacion();
        if (providerElegido == null) {
            Toast.makeText(mainactivity, "No hay proveedores de localizacion diponibles", Toast.LENGTH_LONG).show();
            return;
        }
        //if (obtenerLocalizacion(providerElegido)) {
        mostrarInformacionDeLocalizacion();
        //}

    }

    public boolean obtenerLocalizacion(String providerPotencial) {
        if (ActivityCompat.checkSelfPermission(mainactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainactivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mainactivity, "No hay permisos suficientes", Toast.LENGTH_LONG).show();
            return false;
        }
        localizacionActual = miLocationManager.getLastKnownLocation(providerPotencial);
        if (localizacionActual == null) {
            return false;
        }
        return true;
    }

    public String elegirNosotrosUnProveedorDeLocalizacion() {
        Criteria criteria = definirRequisitosMinimos();
        //  --- ahora tambien podemos elegir que se busque entre los proveedores disponibles o que se use uno especifico
        //  --- para usa uno específico:
        // providerElegido = LocationManager.GPS_PROVIDER;
        // providerElegido = LocationManager.NETWORK_PROVIDER;

        //  --- para buscar entre los disponibles:
        List<String> providersElegidos = miLocationManager.getProviders(criteria, true);
        // public List<String> getAllProviders();  // Devuelve una lista de nombres de todos los proveedores conocidos.
        // public List<String> getProviders(boolean enabledOnly); // Devuelve todos los proveedores disponibles dependiendo de los permisos que tenga la aplicación y de las características del dispositivo móvil.
        providerElegido = null;
        for (String providerPotencial : providersElegidos) {
            if (miLocationManager.isProviderEnabled(providerPotencial)) {
                if (obtenerLocalizacion(providerPotencial)) {
                    providerElegido = providerPotencial;
                    break;
                }
            }
        }
        return providerElegido;

    }

    public String dejarQueAndroidElijaUnProveedorDeLocalizacion() {


        Criteria criteria = definirRequisitosMinimos();
        providerElegido = miLocationManager.getBestProvider(criteria, true);
        //  --- No hay proveedor disponible que elija android... puede seer que no haya o que no cumpla mis requisitos
        if (providerElegido == null || !miLocationManager.isProviderEnabled(providerElegido)) {
            return null;
        }
        //else {
        //    myLocationListener = new MyLocationListener();
        //    miLocationManager.requestLocationUpdates(
        //            provider,
        //            0,
        //            0,
        //            myLocationListener);
        //    // TODO - put a marker in my location       
        //    GeoPoint currentGeoPoint = MapUtils.getInstance().
        //            getCurrentLocation(myLocationManager, provider);
        //    // Center to the current location
        //    centerLocation(currentGeoPoint, MAX_ZOOM);
        //}
        if (!obtenerLocalizacion(providerElegido)) {
            providerElegido = null;
        }
        return providerElegido;
    }


    //  +-------------------------------------------------+
    //  |    PARA LA CUANDO CAMBIA LA LOCALIZACION        |
    //  |                                                 |
    //  +-------------------------------------------------+


    public void crearSensor() {
        if (ActivityCompat.checkSelfPermission(mainactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainactivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mainactivity, "No hay permisos suficientes", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            miLocationManager = (LocationManager) mainactivity.getSystemService(Context.LOCATION_SERVICE);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            localizacionActual = location;
            mostrarInformacionDeLocalizacion();
            Toast.makeText(mainactivity, "Localizaicon actualizada", Toast.LENGTH_LONG).show();

        } else {
            mainactivity.tvLocalizacionActual1.setText("No encuentro tu ubicación, ¿dónde estás?");
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(mainactivity, "Por favor active el GPS e Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }


    public void iniciarSensor() {
        if (providerElegido != null) {

            try {
                //                                                                     tiempo en ms, y distancia en metros
                //miLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
                miLocationManager.requestLocationUpdates(providerElegido,1000, 1, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    public void pararSensor() {
        miLocationManager.removeUpdates(this);
    }


    public void mostrarInformacionDeLocalizacion() {
        double latitud = localizacionActual.getLatitude();
        double longitud = localizacionActual.getLongitude();
        String lat = new DecimalFormat("##.000").format(latitud);
        String lon = new DecimalFormat("##.000").format(longitud);
        mainactivity.tvLocalizacionActual1.setText("Latitud : " + lat);
        mainactivity.tvLocalizacionActual2.setText("Longitud: " + lon);
        direccionactual = obtenerDireccionPostal(localizacionActual);
        mainactivity.tvLocalizacionActual3.setText("Direccion: " + direccionactual);
    }


    //  +-------------------------------------------------+
    //  |    PARA LA TODOS LOS CASOS                      |
    //  |                                                 |
    //  +-------------------------------------------------+

    public Criteria definirRequisitosMinimos() {
        //  --- podemos definir algunos requisitos (criteria)  para que los cumple un proveedor
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); // nivel de la precision a aplicar
        criteria.setAltitudeRequired(false); // necesidad altitud requerida
        criteria.setBearingRequired(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);// minimo de precision horizontal 
        criteria.setVerticalAccuracy(Criteria.ACCURACY_LOW); // minimo de precision vertical 
        criteria.setSpeedRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setCostAllowed(false);  // permitir que el proveedor nos cobre 
        return criteria;
    }

    private String obtenerDireccionPostal(Location localizacionActual) {
        //private String getCompleteAddressString(double LATITUDE, double LONGITUDE) { 
        String direccion = "";
        Geocoder geocoder = new Geocoder(mainactivity, Locale.getDefault());
        try {
            List<Address> listadirecciones = geocoder.getFromLocation(localizacionActual.getLatitude(), localizacionActual.getLongitude(), 1);
            if (listadirecciones != null) {
                Address primeradireccion = listadirecciones.get(0);
                direccion = "\n" + direccion + primeradireccion.getThoroughfare() + "," + primeradireccion.getSubThoroughfare() + "\n";
                direccion = direccion + primeradireccion.getLocality() + "\n";
                direccion = direccion + primeradireccion.getPostalCode() + "\n";
                direccion = direccion + primeradireccion.getAdminArea() + "\n";
                direccion = direccion + primeradireccion.getCountryName() + "\n";

                //for (int i = 0; i <= primeradireccion.getMaxAddressLineIndex(); i++) {
                //    direccion  = direccion + primeradireccion.getAddressLine(i) + "\n";
                //}
            } else {
                Log.d("==> SENSORES", "No hay direccion....");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("==> DIRECCION ", direccion);
        return direccion;
    }

    public Location getLocalizacionActual() {
        return localizacionActual;
    }
}
