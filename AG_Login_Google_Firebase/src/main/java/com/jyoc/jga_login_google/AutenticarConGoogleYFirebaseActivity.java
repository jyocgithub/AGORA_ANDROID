package com.jyoc.jga_login_google;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AutenticarConGoogleYFirebaseActivity extends AppCompatActivity {

    // NO OLVIDAR MODIFICAR esta clave segun lo descargado de consola Firebase en AUTENTHICATION
    //private final String ID_GOOGLE = "492199816393-oibees4krbbfnld17p2t9qj1bu96kep9.apps.googleusercontent.com"; // GOOGLE CLIENT ID TRAS DAR EL SHA1
    private final String ID_GOOGLE = "538081064550-j8apjv8lf6l4h5gteihhrj87chjgsa2k.apps.googleusercontent.com"; // GOOGLE CLIENT ID TRAS DAR EL SHA1
    public static String USUARIO_GOOGLE, MAIL_GOOGLE;

    private static GoogleSignInOptions googleSignInOptions;
    private static GoogleSignInClient googleSignInClient;
    private static GoogleSignInAccount googleSignInAccount;
    private static final int RC_SIGN_IN = 29919;
    private static FirebaseAuth firebaseAuth;
    private static TextView tvCabecera;
    //private static TextView tvCargando;
    private static ImageView ivRegistrarse;
    private GoogleSignInAccount micuentaGoogleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticar_con_google);

        tvCabecera = findViewById(R.id.tvCabecera);
        ivRegistrarse = findViewById(R.id.ivRegistrarse);
        tvCabecera.setVisibility(View.INVISIBLE);
        ivRegistrarse.setVisibility(View.INVISIBLE);

        // PREPARAMOS PLANTILLA DE SOLICITUD DE DATOS
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ID_GOOGLE)
                .requestEmail()
                .build();
        // CREAMOS UN GoogleSignInClient CON LAS OPCIONES INCLIUDAS EN LA PLANTILLA ANTES CREADA
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Creamos la instancia del objeto de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        intentarObtenerCuentaGoogle();
    }

    protected void intentarObtenerCuentaGoogle() {
        // MIRAMOS SI EXISTE CUENTA EN GOOGLE DE ESTE CLIENTE Y SACAMOS LA CUENTA DEL CLIENTE DE GOOGLE,
        // SI NO ES NULL, ES QUE EXISTE PARA GOOGLE EN ESTA APLICACION
        GoogleSignInAccount micuentaGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (micuentaGoogleSignInAccount != null) {
            // SI YA ESTA AUTENTICADO EN GOOGLE, AUTENTICARSE EN FIREBASE
            //autenticarseEnFirebaseConGoogle(micuentaGoogleSignInAccount)

            MAIL_GOOGLE = micuentaGoogleSignInAccount.getEmail();
            USUARIO_GOOGLE = micuentaGoogleSignInAccount.getDisplayName();
            abrirActividadInicial();

        } else {
            // PREPARAR BOTON PARA REGISTRARSE EN GOOGLE
            Toast.makeText(this, "NO HAY CUENTA DE GOOGLE ASOCIADA", Toast.LENGTH_LONG).show();
            prepararGUIParaRegistrarseEnGoogle();
        }
    }

    protected void prepararGUIParaRegistrarseEnGoogle() {
        tvCabecera.setVisibility(View.VISIBLE);
        ivRegistrarse.setVisibility(View.VISIBLE);
        // Preparar boton de conectarse a google
        ivRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    // Preparamos la respuesta al intento de login con Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SI VENIMOS DE HACER UNA PETICION DE ACCESO A CUENTA DE GOOGLE...
        if (requestCode == RC_SIGN_IN) {
            trasIntentarRegistrarseEnGoogle(data);
        }
    }







    private void trasIntentarRegistrarseEnGoogle(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In con exito, autenticarse con firebase ahora
            GoogleSignInAccount account = task.getResult(ApiException.class);
            autenticarseEnFirebaseConGoogle(account);
        } catch (ApiException e) {
            // Google Sign In sin exito, mensaje a usuario
            Toast.makeText(this, "NECESITA ACCESO CON UNA CUENTA DE GOOGLE", Toast.LENGTH_LONG).show();
            prepararGUIParaRegistrarseEnGoogle();
        }
    }
    
    public void abrirActividadInicial() {
        Toast.makeText(getApplicationContext(), "Bienvenido " + USUARIO_GOOGLE, Toast.LENGTH_LONG).show();
        // hacer lo que sea con el nombre del usuario, seguir adelante, Y ABRIR LA ACTIVIDAD INICIAL
        //Uri uri;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("MICUENTAGOOGLE", micuentaGoogleSignInAccount);

        intent.putExtra("ID_GOOGLE", micuentaGoogleSignInAccount.getId());
        intent.putExtra("USUARIO_GOOGLE", micuentaGoogleSignInAccount.getDisplayName());
        intent.putExtra("MAIL_GOOGLE", micuentaGoogleSignInAccount.getEmail());

        // en el siguiente activity se recogen estos datos con estas instrucciones :
        //String idGoogle  = getIntent().getExtras().getString("ID_GOOGLE");
        //String usuarioGoogle  = getIntent().getExtras().getString("USUARIO_GOOGLE");
        //String mailGoogle  = getIntent().getExtras().getString("MAIL_GOOGLE");

        startActivity(intent);
        finish();
    }



    private void autenticarseEnFirebaseConGoogle(GoogleSignInAccount micuenta) {
        AuthCredential credential = GoogleAuthProvider.getCredential(micuenta.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in permitido, recogemos la identidad del usuario y continuamos
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    MAIL_GOOGLE = user.getEmail();
                    USUARIO_GOOGLE = user.getDisplayName();
                    abrirActividadInicial();
                } else {
                    // Sign in no permitido, mensaje y vuelta atras
                    Snackbar.make(tvCabecera, "FALLO EN EL ACCESO CON CUENTA DE FIREBASE", Snackbar.LENGTH_SHORT).show();
                    prepararGUIParaRegistrarseEnGoogle();
                }
            }
        });
    }


    // .........................................
    //      desconectar_usuario
    // .........................................
    public static void desconectar_usuario(Activity actividad) {
        googleSignInClient.revokeAccess()
                .addOnCompleteListener(actividad, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar.make(tvCabecera, "USUARIO DESCONECTADO DE SU CUENTA GOOGLE", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}