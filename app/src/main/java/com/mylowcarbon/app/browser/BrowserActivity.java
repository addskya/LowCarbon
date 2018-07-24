package com.mylowcarbon.app.browser;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
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

import com.mylowcarbon.app.BaseActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityBrowserBinding;
import com.mylowcarbon.app.javascript.JavaScriptApi;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-13.
 * Email:addskya@163.com
 */

public class BrowserActivity extends BaseActivity {

    private static final String TAG = "BrowserActivity";
    private WebView mWebView;
    private ActivityBrowserBinding mBinding;

    public static void intentTo(@NonNull Context context,
                                @Nullable String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_browser);
        mWebView = mBinding.webView;
        initWebView();
        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)){
            url = "file:///android_asset/html/test-wt.html";
        }
         mWebView.loadUrl(url);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
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


        //开启localStorage可以生效
        webSettings.setDomStorageEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

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
        //String call = "javascript:alertMessage(\"Hello Orange\")";
        //String call = "javascript:toastMessage(\"Hello Orange\")";
//        String call = "javascript:sumToJava(10,99)";
//        mWebView.loadUrl(call);
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
