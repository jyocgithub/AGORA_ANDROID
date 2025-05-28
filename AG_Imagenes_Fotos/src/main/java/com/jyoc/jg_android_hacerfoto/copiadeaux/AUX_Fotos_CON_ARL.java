package com.jyoc.jg_android_hacerfoto.copiadeaux;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class AUX_Fotos_CON_ARL {
    static final int TOMAR_FOTO_COMPLETA = 24351;
    static final int TOMAR_FOTO_THUMBNAIL = 6642;


    // +------------------------------------------------------------------+
    // |                                                                  |
    // |   EJEMPLO DE ABRIR UNA PETICION DE UN IMAGEN DE LA GALERIA       |
    // |                                                                  |
    // +------------------------------------------------------------------+
    /**
     * Objeto ARL que realiza la peticion y que luego recoge su respuesta
     */
    ActivityResultLauncher<String> activityResultLauncher_buscarImagenes;

    /**
     * obtenerRespuestaTrasPedirFotoDeGaleria
     * 
     * Metodo que se debe lanzar antes de realizar una peticion ARL, pero que se ejecutará 
     * realmente cuando se termine la peticion, con la respuesta de dicha peticion
     * Este método NO se puede lanzar en un listener, ha de lanzarse incondicionalmente,
     * bien en el onCreate de una actividad o en el onAttach de un fragment 
     * @param context  objeto de la instancia de AppCompatActivity que invoca el método
     */
    public void obtenerRespuestaTrasPedirFotoDeGaleria(AppCompatActivity context) {
        activityResultLauncher_buscarImagenes = context.registerForActivityResult(
                new ActivityResultContracts.GetContent(),  // GetContent
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try {
                            Bitmap bitmapobtenido = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            //MainActivity mainActivity = (MainActivity) context;
                            //context.ivFoto.setImageBitmap(bitmapobtenido);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * lanzarPedirFotoDeGaleria
     * 
     * Método que lanza la peticion
     * Se puede ejecutar desde un listener o de modo asincrono
     * Se puede ejecutar el método o coger simplemente la unica linea que lo compone y usarla
     */
    public void lanzarPedirFotoDeGaleria() {
        activityResultLauncher_buscarImagenes.launch("image/*");
    }



    // +------------------------------------------------------------------+
    // |                                                                  |
    // |   EJEMPLO DE ABRIR CAMARA Y HACER FOTO                           |
    // |                                                                  |
    // +------------------------------------------------------------------+
    /**
     * Objeto ARL que realiza la peticion y que luego recoge su respuesta
     */
    ActivityResultLauncher<Uri> activityResultLauncher_hacerfoto;

    /**
     * obtenerRespuestaTrasHacerFoto
     *
     * Metodo que se debe lanzar antes de realizar una peticion ARL, pero que se ejecutará 
     * realmente cuando se termine la peticion, con la respuesta de dicha peticion
     * Este método NO se puede lanzar en un listener, ha de lanzarse incondicionalmente,
     * bien en el onCreate de una actividad o en el onAttach de un fragment 
     * @param context  objeto de la instancia de AppCompatActivity que invoca el método
     */
    public void obtenerRespuestaTrasHacerFoto(AppCompatActivity context) {
        activityResultLauncher_hacerfoto = context.registerForActivityResult(
                new ActivityResultContracts.TakePicture(),  // TakePicture
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {

                        // Hacer foto (EXIGE QUE EXISTA EL FILEPROVIDER; AQUI NO SE HA HECHO, AL IGUAL QUE PROBABLEMENTE PEDIR PERMISOS DE CAMARA)
                        File file = new File(context.getFilesDir(), "fotoHecha");
                        uriDeFicheroResultante = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                        try {
                            Bitmap bitmapobtenido = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriDeFicheroResultante);
                            //MainActivity mainActivity = (MainActivity) context;
                            //context.ivFoto.setImageBitmap(bitmapobtenido);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * lanzarHacerFoto
     *
     * Método que lanza la peticion
     * Se puede ejecutar desde un listener o de modo asincrono
     * Se puede ejecutar el método o coger simplemente la unica linea que lo compone y usarla
     */
    Uri uriDeFicheroResultante;
    public void lanzarHacerFoto() {
        activityResultLauncher_hacerfoto.launch(uriDeFicheroResultante);
    }
    



    //// +---------------------------------------------+
    //// |                                             |
    //// |  Hacer foto THUMBNAIL                       |
    //// |                                             |
    //// +---------------------------------------------+
    //public static void hacerFotoThumbnail(Activity activity) {
    //    //if (takePictureIntent.resolveActivity(actividad.getPackageManager()) != null) {  // Confirma que haya una app que pueda hacer una foto
    //    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //    activity.startActivityForResult(cameraIntent, TOMAR_FOTO_THUMBNAIL);
    //}
    //
    //public static Bitmap onActivityResultDeHacerFoto(Activity activity, int requestCode, int resultCode, Intent data) {
    //    Uri URIdeFoto = data.getData();
    //    Bitmap imagenResultado = null;
    //    
    //    // ------- ASI RECOGEMOS Y DEVOLVEMOS LA FOTO COMPLETA EN UN BITMAP
    //    if (requestCode == TOMAR_FOTO_COMPLETA && resultCode == activity.RESULT_OK) {
    //        try {
    //            imagenResultado = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), URIdeFoto);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    // ------- ASI RECOGEMOS Y DEVOLVEMOS LA FOTO THUMBNAIL EN UN BITMAP
    //    if (requestCode == TOMAR_FOTO_THUMBNAIL && resultCode == activity.RESULT_OK) {
    //        Bundle extras = data.getExtras();
    //        imagenResultado = (Bitmap) extras.get("data");
    //
    //    }
    //    return imagenResultado;
    //}
    //
    //// +---------------------------------------------+
    //// |                                             |
    //// |  Hacer foto COMPLETA                        |
    //// |                                             |
    //// +---------------------------------------------+
    //
    //public static void hacerFotoCompleta(Activity activity,String nombrefoto, String extension) {
    //    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //    //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {  // Confirma que haya una app que pueda hacer una foto
    //    try {
    //
    //        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM);
    //        File file = File.createTempFile(nombrefoto, extension, dir);
    //        if (file != null) {
    //
    //            // obtenemos una uri del fichero
    //            
    //            //   para obtener el nombre del paquete de la app se puedr usar Context.getPackageName() (lo extrae a nivel de Unix File System)
    //            //   o bien BuildConfig.LIBRARY_PACKAGE_NAME (lo extrae de Gradle)
    //            //String fileauthorities = Context.getPackageName() + ".fileprovider";
    //            String fileauthorities = BuildConfig.LIBRARY_PACKAGE_NAME + ".fileprovider";
    //            String currentPhotoPath = file.getAbsolutePath(); // si quisieramos usar el path completo en algun sitio
    //            Uri URIdeFichero = FileProvider.getUriForFile(activity, fileauthorities, file);
    //
    //            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URIdeFichero);
    //            activity.startActivityForResult(takePictureIntent, TOMAR_FOTO_COMPLETA);
    //        }
    //    } catch (IOException e) {
    //        Toast.makeText(activity, "Error al crear la foto", Toast.LENGTH_SHORT).show();
    //    }
    //
    //    // ---------- LA FOTO SE RECOGE EN EL METODO ONACTIVIRYRESULTLAUNCHER (VER MAS ARRIBA)
    //}
    //
    //// +---------------------------------------------+
    //// |                                             |
    //// |  Hacer SCREENSHOT                           |
    //// |                                             |
    //// +---------------------------------------------+
    //public static Bitmap tomarScreenShot(Activity actividad) {
    //    View rootView = actividad.getWindow().getDecorView().findViewById(android.R.id.content);
    //    View screenView = rootView.getRootView();
    //    Bitmap bitmap = Bitmap.createBitmap(screenView.getWidth(), screenView.getHeight(), Bitmap.Config.ARGB_8888);
    //    Canvas canvas = new Canvas(bitmap);
    //    Drawable bgDrawable = screenView.getBackground();
    //    if (bgDrawable != null) {
    //        bgDrawable.draw(canvas);
    //    } else {
    //        canvas.drawColor(Color.WHITE);
    //    }
    //    screenView.draw(canvas);
    //    return bitmap;
    //}
    //
    //
    //
    //// =====================================================
    //// ====     elegirImagenDeSistemaDeArchivos       ======
    //// =====================================================
    //public static final int ELEGIR_IMAGEN_DE_SISTEMA_DE_ARCHIVOS = 234;
    //public static void elegirImagenDeSistemaDeArchivos(Activity actividad) {
    //    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //    actividad.startActivityForResult(intent, ELEGIR_IMAGEN_DE_SISTEMA_DE_ARCHIVOS);
    //}
    //
    //
    //// =====================================================
    //// ====     elegirImagenDeCualquierAplicacion     ======
    //// =====================================================
    //public static final int ELEGIR_IMAGEN_DE_CUALQUIER_APP = 7345;
    //public static void elegirImagenDeCualquierAplicacion(Activity actividad) {
    //    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
    //    getIntent.setType("image/*");
    //
    //    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //    pickIntent.setType("image/*");
    //
    //    Intent chooserIntent = Intent.createChooser(getIntent, "Seleccionar Aplicacion");
    //    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
    //
    //    actividad.startActivityForResult(chooserIntent, ELEGIR_IMAGEN_DE_CUALQUIER_APP);
    //}
    //
    //
    //
    //





}
