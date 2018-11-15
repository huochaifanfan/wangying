package com.wytianxiatuan.wytianxia.view.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.CookieUtil;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/15 0015.
 * h5页面
 */

public class MainWebView extends BaseActivity implements View.OnClickListener{
    private TextView tvTopText;
    private ImageView imageBack;
    private ProgressBar progressBar;
    private WebSettings settings;
    private WebView webView;
    private String name;
    private String url;
    private Map<String,String> headers;
    private String where;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview);
        init();
    }

    private void init() {
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        where = getIntent().getStringExtra("where");
        headers = new HashMap<>();
        headers.put("MYAPP",Constants.userAgent);
        tvTopText = (TextView) findViewById(R.id.tv_top_title);
        imageBack = (ImageView) findViewById(R.id.imageView_back);
        imageBack.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);
        tvTopText.setText(name);
        progressBar.setMax(100);
        settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        if (url != null && url.contains(Constants.cookieUrl)) {
            CookieUtil.getCookie(this , url);
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (name == null ||"".equals(name)){
                    tvTopText.setText(title);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.contains(Constants.cookieUrl)) {
                    CookieUtil.getCookie(MainWebView.this, url);
                }
                view.loadUrl(url,headers);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(url,headers);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back:
                if (webView != null && webView.canGoBack()){
                    webView.goBack();
                }else {
                    if ("main".equals(where)){
                        Intent intent = new Intent(this ,MainActivity.class);
                        startActivity(intent);
                    }
                    this.finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (webView!= null && webView.canGoBack()){
                webView.goBack();
                return true;
            }
            if ("main".equals(where)){
                Intent intent = new Intent(this ,MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
