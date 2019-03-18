package com.stephensir.webviewdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    // ==== 屬性/全域變數 ====
    private static final String TAG = "MainActivity===>";
    private WebView webView;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ==== 螢幕管理 ====
        // 開啟螢幕方向偵測器(Orientation Sensor)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 設定螢幕長期開啟
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 全螢幕
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ==== 設定webView物件 ====
        // 取得螢幕上id為webView的物件
        webView = findViewById(R.id.webView);
        // 設定webView物件支援縮放功能
        webView.getSettings().setSupportZoom(true);
        // 設定webView物件開啟內置的Zoom控制功能
        webView.getSettings().setBuiltInZoomControls(true);
        // 設定webView物件開啟內置的Zoom功具(invokeZoomPicker)
        webView.invokeZoomPicker();
        // 設定webView物件支援JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // 設定webView物件強制使用webView物件作為超連結開啟時的瀏覽器，放棄Android預設
        webView.setWebViewClient(new WebViewClient());
        // 設定以90%的縮放顯示
        webView.setInitialScale(90);
        // 設定webView內容載入進度
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setTitle("Loading...");
                // 當progress進度完成時還原標題為R.string.app_name
                setProgress(progress * 100);
                if (progress == 100)
                    setTitle(R.string.app_name);
            } //onProgressChanged()
        }); //webView.setWebChromeClient()


    }//onCreate


    // ==== 回應硬體『back』鍵 ====
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // webView退回
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // onKeyDown()

    // ==== onClick回應 ====
    public void ibtn1Click(View v) {
        Log.d(TAG,"ibtn1Click");
        url = "http://svt.hkct.edu.hk/";
        webView.loadUrl(url);
    } // imgBtnHomeClick()

    public void ibtn2Click(View v) {
        Log.d(TAG,"ibtn2Click");
        url = "http://blackboard.hkct.edu.hk/";
        webView.loadUrl(url);
    } // imgBtnBBClick()

    public void ibtn3Click(View v) {
        Log.d(TAG,"ibtn3Click");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, "StephenSir@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "CT299ESxxx");
        intent.putExtra(Intent.EXTRA_TEXT, "This email send by CT299ESxxx WebViewDemo");
        intent.setType("text/plain");
        //startActivity(Intent.createChooser(intent, "Choose Email Client"));
        startActivity(intent);
    } // 回應imgBtnEmailClick的事件()

    public void ibtn4Click(View v) {
        Log.d(TAG,"ibtn4Click");
        // 設定將要顯示的HTML內容
        //String customHtml = "<html><body><h1>By Stephensir@gmail.com</h1></body></html>";
        // 使用.loadData()方法載入HTML內容
        //webView.loadData(customHtml, "text/html", "UTF-8");

        // 指向asset資料夾內的HTML網頁例子
        // D:\CT299ES013\projects\WebViewDemo\app\src\main\assets\index.html
        webView.loadUrl("file:///android_asset/index.html");

    } // customBtnClick()


}//MainActivity
