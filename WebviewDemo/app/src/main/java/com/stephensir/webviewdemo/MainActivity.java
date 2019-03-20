package com.stephensir.webviewdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Properties
    private static final String TAG = "MainActivity===>";
    private WebView webView;
    private String url = "";
    ImageButton ibtn1,ibtn2, ibtn3, ibtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Screen manage
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Reference
        ibtn1 = findViewById(R.id.ibtn1);
        ibtn2 = findViewById(R.id.ibtn2);
        ibtn3 = findViewById(R.id.ibtn3);
        ibtn4 = findViewById(R.id.ibtn4);

        // Set Listener
        ibtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHome();
            }
        });
        ibtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBB();
            }
        });
        ibtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEmail();
            }
        });
        ibtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCustom();
            }
        });

        // Setup webview object
        // Reference
        webView = findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        // Support Zoom
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.invokeZoomPicker();
        // Initial zoom
        webView.setInitialScale(90);
        // enable JavaScript Support
        webView.getSettings().setJavaScriptEnabled(true);
        // Navigation
        webView.setWebViewClient(new WebViewClient());
        // display loading...
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setTitle("Loading...");
                setProgress(progress * 100);
                if (progress == 100)
                    setTitle(R.string.app_name);
            } //onProgressChanged()
        }); //webView.setWebChromeClient()
    } //onCreate()

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // onKeyDown()

    private void doHome(){
        Log.d(TAG,"doHome()");
        url = "https://stephenngsir.wordpress.com";
        webView.loadUrl(url);
    }
    private void doBB(){
        Log.d(TAG,"doBB()");
        url = "https://portal.hkct.edu.hk/";
        webView.loadUrl(url);
    }
    private void doEmail(){
        Log.d(TAG,"doEmail()");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, "StephenSir@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "WebviewDemo");
        intent.putExtra(Intent.EXTRA_TEXT, "This is demo email send from WebViewDemo");
        intent.setType("text/plain");
        //startActivity(Intent.createChooser(intent, "Choose Email Client"));
        startActivity(intent);
    }
    private void doCustom(){
        Log.d(TAG,"doCustom()");
        // Define HTML
        //String customHtml = "<html><body><h1>By Stephensir@gmail.com</h1></body></html>";
        // load HTML
        //webView.loadData(customHtml, "text/html", "UTF-8");

        // 指向asset資料夾內的HTML網頁例子
        // ~\WebViewDemo\app\src\main\assets\index.html
        webView.loadUrl("file:///android_asset/index.html");
    }

    // Interface for JavaScript call
    public class WebAppInterface {
        Context mContext;

        // Instantiate the interface and set the context
        WebAppInterface(Context c) {
            mContext = c;
        }

        // Show a toast from the web page
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
