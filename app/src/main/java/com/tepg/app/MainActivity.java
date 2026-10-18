package com.tepg.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true); // localStorage : conserve clients et devis

        // Impression / PDF des devis
        web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void print() {
                runOnUiThread(() -> {
                    PrintManager pm = (PrintManager) getSystemService(PRINT_SERVICE);
                    pm.print("Devis TEPG", web.createPrintDocumentAdapter("Devis_TEPG"),
                            new PrintAttributes.Builder().build());
                });
            }
        }, "Android");

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                Uri u = r.getUrl();
                if ("file".equals(u.getScheme())) return false;
                try { // tel:, WhatsApp, etc. s'ouvrent dans l'app adaptée
                    startActivity(new Intent(Intent.ACTION_VIEW, u));
                } catch (Exception ignored) { }
                return true;
            }

            @Override
            public void onPageFinished(WebView v, String url) {
                v.evaluateJavascript("window.print=function(){Android.print()}", null);
            }
        });

        web.loadUrl("file:///android_asset/index.html");
    }
}
