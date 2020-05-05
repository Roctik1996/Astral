package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goroscop.astral.R;
import com.goroscop.astral.model.Pay;
import com.goroscop.astral.presenter.PayPresenter;
import com.goroscop.astral.view.ViewGetPayUrl;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class PayActivity extends MvpAppCompatActivity implements ViewGetPayUrl {

    private WebView webView;
    private ProgressBar progressBar;
    private FrameLayout frame;

    @InjectPresenter
    PayPresenter payPresenter;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.frame);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebChromeClient(new WebChromeClient() {
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("PAGE_URL", url);
                if (url.contains("7astral.ru/?Order_ID")) {
                    onBackPressed();
                }
            }
        });

        if (mSettings.contains(APP_PREFERENCES_TOKEN) && !mSettings.getString(APP_PREFERENCES_TOKEN, "").equals("")) {
            payPresenter.getPayUrl("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
        }
    }

    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void getPayUrl(Pay url) {
        webView.loadUrl(url.getPreauthPaymentLink());
    }

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        frame.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
