package com.example.jyocgmaps;
// AIzaSyCUZftUnNTRMvWbuzA7hJ6TM2iPsu1VY74

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnPoiClickListener {

    private GoogleMap miGoogleMap;
    String direccionActual;
    SupportMapFragment mapFragment;

    String[] direcciones;
    String[] snippets;
    String[] paradasDeRuta;

    boolean primeraCarga = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        direccionActual = getIntent().getStringExtra("DIRECCION");

        direcciones = new String[]{"Calle Avenida El Ferrol, 7, Madrid",
                "Calle Avenida El Ferrol, 31, Madrid",
                "Calle Ginzo de Limia 20, Madrid"};

        // los snippts son una segunda cabecera de cada marcador de un mapa
        snippets = new String[]{"SEDE CENTRAL",
                "SEDE 1",
                "SEDE 2"};

        // los snippts son una segunda cabecera de cada marcador de un mapa
        paradasDeRuta = new String[]{"Calle Melchor Fernandez Almagro 32, Madrid",
                "Calle Villa de Marin 3, Madrid",
                "Calle Finisterre 1, Madrid",
                "Calle Finisterre 12, Madrid",
                "Avenida de Monforte de Lemos 32, Madrid"};

        paradasDeRuta = new String[]{"C. Sor Dositea Andrés, 5, Zamora",
                "C. Rúa los Francos, 29-23, Zamora",
                "C. de Ramos Carrión, Zamora",
                "Pl. Sta. María Nueva, 3, Zamora",
                "C. de Carniceros, 13-7, Zamora"};
        direccionActual ="C. Sor Dositea Andrés, 5, Zamora";

        LocationManager gestorLoc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No tiene los suficientes permisos", Toast.LENGTH_SHORT).show();
        }
        gestorLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmento_map);

        // con esto se fuerza a que se llame a onMapReady, donde elegimos una nueva posicion.
        // CUIDADO que esta llamada no funciona si se hace ademas un onLocationChanged,
        // pues la llamada a este ultimo se hace despues de onReadyMap
        mapFragment.getMapAsync(this);

        ((Button) findViewById(R.id.btActualizar)).setOnClickListener(c -> {
            direccionActual = ((EditText) findViewById(R.id.etNuevaDireccion)).getText().toString();
            GUT_Gmaps.moverMapaYZoom(this, miGoogleMap, direccionActual, 20);
            mapFragment.getMapAsync(this);
        });

        ((Button) findViewById(R.id.btAgregarMarcador)).setOnClickListener(c -> {
            String direccionNueva = ((EditText) findViewById(R.id.etNuevaDireccion)).getText().toString();
            String tituloMarcador = direccionNueva;
            String snippet = direccionNueva;
            GUT_Gmaps.agregarMarcadorPorDireccion(this, miGoogleMap, direccionActual, tituloMarcador, snippet);
            mapFragment.getMapAsync(this);
        });






    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. 
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        miGoogleMap = googleMap;
        UiSettings settings = this.miGoogleMap.getUiSettings();

        if (primeraCarga) {
            // activa los botones de zoom
            settings.setZoomControlsEnabled(true);

            //settings.setMapToolbarEnabled(true);  // puede dar error si el theme es antiguo

            // con esto se centra en la pantalla nuestra direccion pedida
            settings.setMyLocationButtonEnabled(true);  // por defecto es true, 

            // Añadimos un MARCADOR con la propia posicion inicial, para que aparezca la direccion somo seleccionada en el mapa, 
            // y no solo el mapa localizado en la zon con la propia posicion
            // Haciendo click en el marcador se ve la descripcion
            GUT_Gmaps.agregarMarcadorPorDireccion(this, miGoogleMap, direccionActual, direccionActual, null);

            // Y añadimos MAS MARCADORES
            for (int i = 0; i < direcciones.length; i++) {
                GUT_Gmaps.agregarMarcadorPorDireccion(this, miGoogleMap, direcciones[i], direcciones[i], snippets[i]);
            }

            miGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    String title = marker.getTitle();
                    Toast.makeText(MapsActivity.this, "PINCHAO EN MI MARCA\n" + title, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            // ahora hacemos que el mapa se "mueva" a la zona donde esta nuestra direccion pedida
            LatLng latlangDeLaSedeCentral = new DireccionDeGmaps(this, direcciones[0]).getLatlang();
            GUT_Gmaps.moverMapaYZoom(miGoogleMap, latlangDeLaSedeCentral, 20);

            // y creamos una RUTA PINTADA EN EL MAPA
            GUT_Gmaps.crearRuta(this, miGoogleMap, paradasDeRuta);

            // permitimos que se haga click en el mapa.
            // La informacion que se muestre se define mas abajo en el método onPoiClick()
            googleMap.setOnPoiClickListener(this);
            primeraCarga = false;

            miGoogleMap.setOnMapClickListener(pLatLng -> {


                        // vemos si esta activo el swith de edicion...
                        Switch swEdicion = findViewById(R.id.swModoEdicion);
                        if (!swEdicion.isChecked()) {
                            return;
                        }

                        LatLng l = pLatLng;
                        String[] res = GUT_Gmaps.obtenerDireccionDesdeLatLng(MapsActivity.this, pLatLng);
                        Toast.makeText(MapsActivity.this, "PINCHO EN " + res[0], Toast.LENGTH_SHORT).show();


                        // =========== PREGUNTAMOS SI QUIERE GUARDAR EL MARCADOR 
                        // ======================================================
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setMessage("Direccion elegida: \n" + res[0] + "\n¿Desea añadirlo como nuevo marcador?")
                                .setCancelable(false)
                                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON ACEPTAR
                                        // ........... 


                                        //=========== PEDIMOS NOMBRE PARA EL MARCADOR 
                                        //======================================================
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                                        builder.setTitle("Titulo");
                                        builder.setMessage("Escriba un nombre para el marcador");
                                        final EditText input = new EditText(MapsActivity.this);
                                        // input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD); // si es como un password, usar este 
                                        input.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL); // para texto normal
                                        builder.setView(input);
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON ACEPTAR
                                                String xx = input.getText().toString();
                                                BitmapDescriptor bimapdescriptor = BitmapDescriptorFactory.fromResource(R.drawable.iconcara);

                                                GUT_Gmaps.agregarMarcadorPorLatLng(miGoogleMap, pLatLng, xx, xx, bimapdescriptor);

                                                // cerramos el segundo dialogo
                                                dialog.cancel();
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON CANCELAR
                                                Toast.makeText(MapsActivity.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                                                // cerramos el segundo dialogo
                                                dialog.cancel();
                                            }
                                        });
                                        builder.show();
                                        // cerramos el primer dialogo
                                        dialog.cancel();

                                    }
                                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // AQUI VIENEN LAS ACCIONES A REALIZAR CON EL BOTON NO
                                        // ........... 
                                        // NORMALMENTE ACABAN CON EL CANCEL DEL DIALOGO
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert2 = builder.create();
                        alert2.show();

                    }
            );


            miGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.estilo_vacio));


        } else {

        }
    }

    // sirve para ver la informacion de un POI donde se haya hecho click
    @Override
    public void onPoiClick(@NonNull PointOfInterest poi) {
        String a = "PINCHAO EN POI\n" + poi.name;
        //+   poi.latLng.latitude + " / " + poi.latLng.longitude;
        Toast.makeText(this, a, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*
        // ESTO SE USA PARA OBTENER LA LOCALIZACION ACTUAL DEL MOVIL
        // NO ESTA activo pues no pretendemos consultar continuamente nuestra localicion en este caso
        
        //Creamos un LatLng a partir de la posición
        LatLng posicion = new LatLng (location.getLatitude(), location.getLongitude ());
        Toast.makeText(this, posicion.toString(), Toast.LENGTH_LONG).show();
        moverMapaYZoom(miGoogleMap, posicion, 20);
         */
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    //public void posicionActual() {
    //    List<Place.Field> placeFieldsID, placeFieldsName, placeFieldsAddress;
    //    PlacesClient placesClient;
    //    FindCurrentPlaceRequest requestID, requestName, requestAddress;
    //
    //    // Construct a PlacesClient
    //    Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
    //    placesClient = Places.createClient(this);
    //
    //    placeFieldsID = Collections.singletonList(Place.Field.ID);
    //    placeFieldsName = Collections.singletonList(Place.Field.NAME);
    //    placeFieldsAddress = Collections.singletonList(Place.Field.ADDRESS);
    //
    //    requestID = FindCurrentPlaceRequest.newInstance(placeFieldsID);
    //    requestName = FindCurrentPlaceRequest.newInstance(placeFieldsName);
    //    requestAddress = FindCurrentPlaceRequest.newInstance(placeFieldsAddress);
    //
    //    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    //        // TODO: Consider calling
    //        //    ActivityCompat#requestPermissions
    //        // here to request the missing permissions, and then overriding
    //        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //        //                                          int[] grantResults)
    //        // to handle the case where the user grants the permission. See the documentation
    //        // for ActivityCompat#requestPermissions for more details.
    //        return;
    //    }
    //    Task<FindCurrentPlaceResponse> placeResponseID = placesClient.findCurrentPlace(requestID);
    //    placeResponseID.addOnCompleteListener(task -> {
    //        if (task.isSuccessful()) {
    //            FindCurrentPlaceResponse response = task.getResult();
    //            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
    //                String id = placeLikelihood.getPlace().getId();
    //                Log.d("JY", "onCreate: id: " + id);
    //
    //            }
    //            Task<FindCurrentPlaceResponse> placeResponseName = placesClient.findCurrentPlace(requestName);
    //            placeResponseName.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
    //                @Override
    //                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
    //                    FindCurrentPlaceResponse response = task.getResult();
    //
    //                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
    //                        String name = placeLikelihood.getPlace().getName();
    //                        Log.d("JY", "onCreate: name: " + name);
    //
    //
    //                    }
    //                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    //                        // TODO: Consider calling
    //                        //    ActivityCompat#requestPermissions
    //                        // here to request the missing permissions, and then overriding
    //                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                        //                                          int[] grantResults)
    //                        // to handle the case where the user grants the permission. See the documentation
    //                        // for ActivityCompat#requestPermissions for more details.
    //                        return;
    //                    }
    //                    Task<FindCurrentPlaceResponse> placeResponseAddress = placesClient.findCurrentPlace(requestAddress);
    //                    placeResponseAddress.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
    //                        @Override
    //                        public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
    //                            FindCurrentPlaceResponse response = task.getResult();
    //
    //                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
    //                                String address = placeLikelihood.getPlace().getAddress();
    //                                Log.d("JY", "onCreate: address: "+address);
    //
    //                            }
    //
    //                        }
    //                    });
    //
    //                }
    //            });
    //        }
    //    });
    //}
    //public void posicionActual2(){
    //
    //    // Construct a PlacesClient
    //    Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
    //    PlacesClient placesClient = Places.createClient(this);
    //
    //    // Use fields to define the data types to return.
    //    List<Place.Field> placeFields = Collections.singletonList(Place.Field.NAME);
    //
    //    // Use the builder to create a FindCurrentPlaceRequest.
    //    FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
    //
    //    // Call findCurrentPlace and handle the response (first check that the user has granted permission).
    //    if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
    //        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
    //        placeResponse.addOnCompleteListener(task -> {
    //            if (task.isSuccessful()){
    //                FindCurrentPlaceResponse response = task.getResult();
    //                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
    //                    Log.i("JY", String.format("Place '%s' has likelihood: %f",
    //                            placeLikelihood.getPlace().getName(),
    //                            placeLikelihood.getLikelihood()));
    //                }
    //            } else {
    //                Exception exception = task.getException();
    //                if (exception instanceof ApiException) {
    //                    ApiException apiException = (ApiException) exception;
    //                    Log.e("JY", "Place not found: " + apiException.getStatusCode());
    //                }
    //            }
    //        });
    //    } else {
    //        // A local method to request required permissions;
    //        // See https://developer.android.com/training/permissions/requesting
    //        //getLocationPermission();
    //    }
    //
    //
    //}


}
