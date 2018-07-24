package com.mylowcarbon.app.browser;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.BaseActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityBrowserBinding;
import com.mylowcarbon.app.databinding.ActivityMybrowserBinding;
import com.mylowcarbon.app.javascript.JavaScriptApi;
import com.mylowcarbon.app.utils.LogUtil;

/**
 *
 */

public class MyBarBrowserActivity extends ActionBarActivity {

    private static final String TAG = "BrowserActivity";
    private WebView mWebView;
    private ActivityMybrowserBinding mBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mybrowser);

        initWebView();
        String url = getIntent().getStringExtra(AppConstants.URL);
        if (TextUtils.isEmpty(url)){
            url = "file:///android_asset/html/test-wt.html";
        }
         mWebView.loadUrl(url);
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_browser;
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView = mBinding.webView;

        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setTextZoom(100);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        webSettings.setGeolocationEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);


        // Debug Code
        mWebView.addJavascriptInterface(new JavaScriptApi(),"handler");

        webSettings.setUserAgentString(webSettings.getUserAgentString());

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url,
                                        String userAgent,
                                        String contentDisposition,
                                        String mimetype,
                                        long contentLength) {
                startActivitySafely(getBrowsableIntent(Uri.parse(url)));
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    setUiTitle(title);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e(TAG, "url:" + url);
                if (!handleUrl(url)) {
                    view.loadUrl(url);
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 实现对特殊协议的处理:
     * 如: lcf://action=abc
     * 如果是以 lcf的schema,则可以自己实现中转
     *
     * @param url the openable url
     * @return true if the url is openable
     */
    private boolean handleUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e(TAG, "gotoAnyWhere with Empty Url");
            return false;
        }

        if (url.toLowerCase().startsWith("http")) { //startsWith http or https
            return false;
        }

        final Uri uri = Uri.parse(url);

        if (uri == null) {
            return false;
        }

        Intent viewIntent = getBrowsableIntent(uri);
        if (!startActivitySafely(viewIntent)) {
            LogUtil.e(TAG, "Error when handleUrl:" + url);
        }

        return true;
    }

    private Intent getBrowsableIntent(@NonNull Uri uri) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        viewIntent.addCategory(Intent.CATEGORY_DEFAULT);

        viewIntent.setData(uri);
        return viewIntent;
    }

    private boolean startActivitySafely(@NonNull Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            return false;
        }
        return true;
    }
}
