package com.example.amila.autocare.Controllers.Website;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.amila.autocare.R;

public class website_details extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_details);
        webView = findViewById(R.id.webView);
        // Simplest usage: note that an exception will NOT be thrown
        // if there is an error loading this page (see below).
        webView.loadUrl("https://troppo-parachutes.000webhostapp.com/index.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // OR, you can also load from an HTML string:
        //String summary = "<html><body>You scored <b>192</b> points.</body></html>";
        //webView.loadData(summary, "text/html", null);
        // ... although note that there are restrictions on what this HTML can do.
        // See loadData(String, String, String) and loadDataWithBaseURL(String, String, String, String, String) for more info.
        // Also see loadData(String, String, String) for information on encoding special
        // characters.
    }
}
