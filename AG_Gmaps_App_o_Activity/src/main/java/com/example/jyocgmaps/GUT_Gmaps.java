package com.example.jyocgmaps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;

public class GUT_Gmaps {

    /**
     * hayPermisosDeLocalizacion
     * @param context objeto de la instancia de Contexto que invoca el método
     * @return boolean que indica si se tienen los permisos de localizacion
     */
    public static boolean hayPermisosDeLocalizacion(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No se dispone de los permisos de localizacion", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     *  obtenerUbicacionActual
     *  ESTE METODO RESPONDE ASINCRONAMENTE, DEBE COPIARSE EN LA CLASE DONDE SE USA Y 
     *  CONTROLAR LA RESPUESTA CON LA LOCALIZACION OBTENIDA
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     */
    private void obtenerUbicacionActual(Context context) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No se dispone de los permisos de localizacion", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // En ocasiones raras, puede ser null
                        if (location != null) {
                            // hacer aqui lo que se quiera con la location obtenida
                        }
                    }
                });

    }

    /**
     * obtenerLatlangDeDireccion
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param strAddress direccion de la cual se obtiene su latitud y longitud
     * @return objeto LatLng con la localizacion de la direccion indicada
     */
    public LatLng obtenerLatlangDeDireccion(Context context, String strAddress) {

        try {
            Geocoder coder = new Geocoder(context, Locale.getDefault());
            List<Address> address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLongitude();
            //p1 = new GeoPoint((double) (location.getLatitude() * 1E6),   (double) (location.getLongitude() * 1E6));
            LatLng p1 = new LatLng(location.getLatitude(), location.getLongitude());
            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * obtenerDireccionDesdeLatLng
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param pLatLng objeto latLng del se desea obtener informacion+
     * @return String array con la info obenida 
     */
    public static String[] obtenerDireccionDesdeLatLng(Context context, LatLng pLatLng) {

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            addresses = geocoder.getFromLocation(pLatLng.latitude, pLatLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            String[] datos = {address,city,state,country,postalCode,knownName};
            
            return datos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * agregarMarcadorPorLatLng
     *
     * @param pMap       objeto GoogleMap donde crear el marcador
     * @param pLatLng    objeto Latlng donde crear el marcador
     * @param tituloMarcador nombre que se ve al pinchar en el marcador
     * @param pSnippet  Segunad linea del nombre que se ver al pinchar el marcador. Puede ser null
     */
    public static void agregarMarcadorPorLatLng(GoogleMap pMap, LatLng pLatLng, String tituloMarcador, String pSnippet,  BitmapDescriptor bimapdescriptor  ) {
        pMap.addMarker(new MarkerOptions().position(pLatLng).title(tituloMarcador).snippet(pSnippet).icon(bimapdescriptor));
    }

    /**
     * moverMapaYZoom
     *
     * @param pMap
     * @param pLatlang
     * @param pZoom   es in int con el zoom aplicado, por ejemplo, 20 muestra pocas manzanas
     */
    public static void moverMapaYZoom(GoogleMap pMap, LatLng pLatlang, int pZoom) {
        
        CameraUpdate camara = CameraUpdateFactory.newLatLngZoom(pLatlang, pZoom);
        pMap.moveCamera(camara);
    }
    public static void moverMapaYZoom(Context context, GoogleMap pMap, String direccion, int pZoom) {
        LatLng latlang = new DireccionDeGmaps(context, direccion).getLatlang();
        CameraUpdate camara = CameraUpdateFactory.newLatLngZoom(latlang, pZoom);
        pMap.moveCamera(camara);
    }

    /**
     * crearRuta
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param pMap
     * @param paradas
     */
    public static void crearRuta(Context context, GoogleMap pMap, String[] paradas) {
        // todas las instrucciones referentes al builder vienen para que luego al mostrar el mapa 
        // se "viaje" por todos los puntos de la ruta.. no es necesario para pintar solo la ruta
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        PolylineOptions poly = new PolylineOptions();
        for (String cadaParada : paradas) {
            LatLng latlang = new DireccionDeGmaps(context, cadaParada).getLatlang();
            pMap.addMarker(new MarkerOptions().position(latlang).title(cadaParada).snippet("SEDE LOCAL"));
            builder.include(latlang);
            poly.add(latlang);
        }
        pMap.addPolyline(poly);
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        pMap.animateCamera(cu);
    }

    /**
     * agregarMarcadorPorDireccion
     *
     * @param context objeto de la instancia de Contexto que invoca el método
     * @param pMap       objeto GoogleMap donde crear el marcador
     * @param pDireccion direccion donde crear el marcador
     * @param tituloMarcador nombre que se ve al pinchar en el marcador
     * @param pSnippet  Segunad linea del nombre que se ver al pinchar el marcador. Puede ser null
     */
    public static void agregarMarcadorPorDireccion(Context context, GoogleMap pMap, String pDireccion, String tituloMarcador, String pSnippet) {
        LatLng latlang = new DireccionDeGmaps(context, pDireccion).getLatlang();
        if(latlang!=null){
            
        pMap.addMarker(new MarkerOptions().position(latlang).title(tituloMarcador).snippet(pSnippet));
        }else{
            Toast.makeText(context, "Direccion invalida", Toast.LENGTH_SHORT).show();
        }
    }

}


/**
 * DireccionDeGmaps
 * Clase con informacion variada de una direccion
 */
class DireccionDeGmaps {
    private LatLng latlang;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    private double longitud;
    private double latitud;

    public DireccionDeGmaps(Context context, String direccionparcial) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> direcciones = geocoder.getFromLocationName(direccionparcial, 1);
            if (direcciones == null || direcciones.isEmpty())
                return;
            Address dir = direcciones.get(0);
            //GeoPoint gp = new GeoPoint((double) (location.getLatitude() * 1E6),   (double) (location.getLongitude() * 1E6));
            latlang = new LatLng(dir.getLatitude(), dir.getLongitude());
            address = dir.getAddressLine(0);
            city = dir.getLocality();
            state = dir.getAdminArea();
            country = dir.getCountryName();
            postalCode = dir.getPostalCode();
            knownName = dir.getFeatureName();
            longitud = dir.getLongitude();
            latitud = dir.getLatitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DireccionDeGmaps(Context context, double longi, double lati) {
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> direcciones = geocoder.getFromLocation(lati, longi, 1);
            if (direcciones == null)
                return;
            Address dir = direcciones.get(0);
            //GeoPoint gp = new GeoPoint((double) (location.getLatitude() * 1E6),   (double) (location.getLongitude() * 1E6));
            latlang = new LatLng(dir.getLatitude(), dir.getLongitude());
            address = dir.getAddressLine(0);
            city = dir.getLocality();
            state = dir.getAdminArea();
            country = dir.getCountryName();
            postalCode = dir.getPostalCode();
            knownName = dir.getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public LatLng getLatlang() {
        return latlang;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }
}