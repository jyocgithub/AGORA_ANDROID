package com.jyoc.jyoc_browser_propio;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView miWebView;
    EditText etURL;
    String urlSolicitada, urlBaseSolicitada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miWebView = findViewById(R.id.wvWebView);
        etURL = findViewById(R.id.etURL);
        
        urlSolicitada = "https://www.marca.com";
        urlBaseSolicitada = "www.marca.com";

        // parametrizamos algo el navegador, por ejemplo, permitimos javascript
        WebSettings webSettings = miWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setDomStorageEnabled(true);
        
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        
        // ---------- USAMOS EL WEBVIEW CON OPCIONES ESTANDAR
        // -- Entre ellas, forzamos a que se abran los enlaces internos dentro del propio webview
        // -- (Si no, se abren un browser en el movil) 
        //miWebView.setWebViewClient(new WebViewClient());  
        //miWebView.loadUrl(urlBaseSolicitada);

        // ---------- USAMOS EL WEBVIEW CON COMPORTAMIENTO PERSONALIZADO
        // -- Exige crear una clase propia que indique cómo se comporta
        miWebView.setWebViewClient(new MiWebViewClient());   
        miWebView.loadUrl(urlSolicitada);
    }


    // ESta es una clase propia que personaliza el comportamiento de nuestro webview
    private class MiWebViewClient extends WebViewClient {
        
        // -- Este método decide que hacer cuando se hace una llamada desde denrto del webview a otra pagina
        // -- Nuestro ejemplo dice que si es de la misma url base, que siga en nuestro webview, 
        // -- y si no, que abra una app-navegador en el movil
        // -- Evidentemente, se puede hace cualquier otra cosa...        
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            
            String urlNuevoEnlace = (String.valueOf(request.getUrl()));

            // .getHost() no incluye http://, por eso comparamos con urlBaseSolicitada
            if (urlBaseSolicitada.equals(Uri.parse(urlNuevoEnlace).getHost())) {
                // Parece que estamos dentro del mismo website, asi que no sobrescribimor nada, 
                // nuestro propio webView cargara la pagina
                return false;
            }
            
            // El enlace no parece que sea del mismo website, asi que 
            // lanzamos una acitivida extandara para abrir el enlace en otra app-navegador
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlNuevoEnlace));
            startActivity(intent);
            return true;
        }
    }

    public void enviarPulsado(View view) {
        urlSolicitada = etURL.getText().toString();
        // Url que carga la app (webview)
        if (urlSolicitada.startsWith("www") || urlSolicitada.startsWith("WWW")) {
            urlSolicitada = "http://" + urlSolicitada;
        }
        miWebView.loadUrl(urlSolicitada);
        
        //Sincronizamos la barra de progreso de la web
        final ProgressBar progressBar = findViewById(R.id.pbprogressBarWeb);
        miWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);
                progressBar.incrementProgressBy(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void atrasPulsado(View view) {
        if (miWebView.canGoBack()) {
            // existe igualmente canGoForward() y goForward()
            miWebView.goBack();
        }
    }
}

