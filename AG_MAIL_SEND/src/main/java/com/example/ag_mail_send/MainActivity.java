package com.example.ag_mail_send;

import javax.mail.*;
import javax.mail.internet.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.mail.Authenticator;

import java.util.Properties;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    
    // NO FUNCIONA PUES GOOGLE HA ELIMINADO EL ACCESO A APPS NO SEGURAS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ((Button) findViewById(R.id.btenviar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    // NO FUNCIONA PUES GOOGLE HA ELIMINADO EL ACCESO A APPS NO SEGURAS
                SendEmailTask sendEmailTask = new SendEmailTask();
                sendEmailTask.execute();
                
            }
        });


    }

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            sendEmail("imartinsacristan@gmail.com", "PRUEBA DE JYOC", "cuerpo del mensaje");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Update UI or show a notification after the email is sent
            //Toast.makeText(MainActivity.this, "FIN DEL HILO DE ENVIO", Toast.LENGTH_SHORT).show();
        }
    }

    // ... (rest of your code)

    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConfig.SMTP_HOST);
        props.put("mail.smtp.port", EmailConfig.SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EmailConfig.SMTP_AUTH_USER, EmailConfig.SMTP_AUTH_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.SMTP_AUTH_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            //Toast.makeText(MainActivity.this, "Email enviado...", Toast.LENGTH_SHORT).show();
            Log.d("Email", "Email enviado....");
        } catch (MessagingException e) {
            //Toast.makeText(MainActivity.this, "ERROR EN ENVIO :"+ e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Email", "Error enviando el correo:" + e.getMessage());
        }
    }
}


class EmailConfig {
        public static final String SMTP_HOST = "smtp.gmail.com";
        public static final String SMTP_PORT = "587";
        public static final String SMTP_AUTH_USER = "javayotrascosas@gmail.com";
        public static final String SMTP_AUTH_PASSWORD = "Metento.413";
}