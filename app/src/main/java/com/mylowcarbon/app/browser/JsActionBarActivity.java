package com.mylowcarbon.app.browser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.javascript.JavaScriptApi;
import com.mylowcarbon.app.javascript.JsCallback;
import com.mylowcarbon.app.ui.LoadingDialog;
import com.mylowcarbon.app.ui.StatusDialog;

/**
 * Created by Orange on 18-4-18.
 * Email:addskya@163.com
 */
public abstract class JsActionBarActivity extends ActionBarActivity {
    private static final String TAG = "JsBaseActivity";
    private WebView mWebView;
    private BaseDialog mDialog;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        ViewParent parent = mWebView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mWebView);
        }
        mHandler = null;
    }

    private void buildWebView() {
        Log.d(TAG, "buildWebView");
        mWebView = new WebView(this);
        initWebView(mWebView);
        mWebView.setVisibility(View.VISIBLE);
        String url = "file:///android_asset/api/api.html";
        mWebView.loadUrl(url);
        int width = 100;
        int height = 100;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        getWindow().addContentView(mWebView, params);
        Log.e(TAG, "addWebView");
    }

    @SuppressWarnings("JavascriptInterface")
    private void initWebView(@NonNull WebView webView) {
        final WebSettings webSettings = webView.getSettings();

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setTextZoom(100);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);

        // Debug Code
        mWebView.addJavascriptInterface(new JavaScriptApi(getJsCallback()), "handler");

        webSettings.setUserAgentString(webSettings.getUserAgentString());

        mWebView.setWebChromeClient(new WebChromeClient() {
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished:" + url);
                initWallet();
            }
        });
    }

    protected JsCallback getJsCallback() {
        return null;
    }

    protected void sendCommand(@NonNull Runnable action) {
        mHandler.postDelayed(action, 200);
    }

    private void initWallet() {
        String call = "javascript:initWallet()";
        Log.d(TAG, "initWallet execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.e(TAG, "initWallet onReceiveValue value:" + value);
                //note 因为 Web3js 是异步回调机制，当函数返回为 true 时，并不代表已经成功 建立连接。此时应该进一步通过回调函数获取详细的连接建立信息。
                //onWalletInitCompleted();
            }
        });
    }

    /**
     * 钱包初始化完毕,可以执行其他任务
     */
    protected void onWalletInitCompleted() {
        Log.d(TAG, "onWalletInitCompleted");
    }

    /**
     * find Wallet
     *
     * @param walletName the wallet name
     * @param address   the wallet address
     * @param callback   the callback after create wallet
     */
    protected void findWallet(@NonNull CharSequence walletName,
                                @NonNull CharSequence address,
                                @Nullable ValueCallback<String> callback) {
        String call = "javascript:findWallet(\"" + walletName + "\",\"" + address + "\")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * create wallet
     *
     * @param walletName the wallet name
     * @param password   the wallet password
     * @param callback   the callback after create wallet
     */
    protected void createWallet(@NonNull CharSequence walletName,
                                @NonNull CharSequence password,
                                @Nullable ValueCallback<String> callback) {
        String call = "javascript:createWallet(\"" + walletName + "\",\"" + password + "\")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    @SuppressWarnings("nouse")
    protected void loadWallet(@NonNull CharSequence walletName,
                              @NonNull CharSequence password,
                              @NonNull CharSequence address,
                              @Nullable ValueCallback<String> callback) {
        String call = "javascript:loadWallet(\"" + walletName + "\",\"" + password + "\"," + address + ")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * export the Wallet
     *
     * @param walletName the wallet name
     * @param password   the wallet password
     * @param callback   the callback after export wallet
     */
    protected void exportWallet(@NonNull CharSequence walletName,
                                @NonNull CharSequence password,
                                @Nullable ValueCallback<String> callback) {
        String call = "javascript:exportWallet(\"" + walletName + "\",\"" + password + "\")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * import wallet
     *
     * @param walletName the wallet name
     * @param keystore   the wallet keystore
     * @param password   the wallet password
     * @param callback   the callback after import wallet
     */
    protected void importWallet(@NonNull CharSequence walletName,
                                @NonNull CharSequence keystore,
                                @NonNull CharSequence password,
                                @Nullable ValueCallback<String> callback) {
        String call = "javascript:importWallet(\"" + walletName + "\"," + keystore + ",\"" + password + "\")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    protected void getWalletList(@Nullable ValueCallback<String> callback) {
        String call = "javascript:getWalletList()";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    protected void getWalletInfo(@Nullable ValueCallback<String> callback) {
        String call = "javascript:getWalletInfo()";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * query the current rate for transfer
     *
     * @param callback the callback
     */
    protected void getRate(@Nullable ValueCallback<String> callback) {
        String call = "javascript:getRate()";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * query the balance of the wallet
     *
     * @param address the wallet address
     */
    protected void balanceOf(@NonNull CharSequence address,@Nullable ValueCallback<String> callback) {
        address = "\"" + address + "\"";
        String call = "javascript:balanceOf(" + address + ")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }

    /**
     * transaction to other wallet
     *
     * @param walletName     the execute wallet name
     * @param password       the execute wallet password
     * @param executeAccount the execute account
     * @param toAccount      to which account
     * @param amount         the transfer amount
     */
    protected void transferByFee(@NonNull CharSequence walletName,
                                 @NonNull CharSequence password,
                                 @NonNull CharSequence executeAccount,
                                 @NonNull CharSequence toAccount,
                                 @NonNull float amount,
                                 @Nullable final ValueCallback<String> callback) {
        executeAccount = "" + executeAccount + "";
        toAccount = "" + toAccount + "";

        final String call = "javascript:transferByFee("
                + "\"" + walletName + "\","
                + "\"" + password + "\","
                + "\"" + executeAccount +"\","
                + "\"" + toAccount + "\","
                + amount + ")";
        Log.d(TAG, "execute:" + call);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
            }
        });

    }

    /**
     * 查询账户所有交易记录
     *
     * @param target 目标账户
     */
    protected void showHistoryTransaction(String target) {
        target = "\"0x" + target + "\"";
        String call = "javascript:showHistoryTransaction(" + target + ")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, null);
    }
    /**
     * 请求奖励
     * 请求系统对执行账户进行Token奖励，系统奖励数量一次不得超过100Token，小于1Token
     * @param walletName     the execute wallet name
     * @param password       the execute wallet password
     * @param executeAccount the execute account
     * @param bonus         the transfer amount
     */
    protected void exchangeToken(@NonNull CharSequence walletName,
                                 @NonNull CharSequence password,
                                 @NonNull CharSequence executeAccount,
                                 @NonNull float bonus,
                                 @Nullable ValueCallback<String> callback) {
        executeAccount = "" + executeAccount + "";

        String call = "javascript:exchangeToken("
                + "\"" + walletName + "\","
                + "\"" + password + "\","
                + "\"" + executeAccount +"\","
                + bonus + ")";
         Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, null);
    }

    /**
     * query the current rate for transfer
     *
     * @param user
     */
    protected void checkUser(@NonNull CharSequence user,@Nullable ValueCallback<String> callback) {
        user = "\"" + user + "\"";
        String call = "javascript:checkUser(" + user + ")";
        Log.d(TAG, "execute:" + call);
        mWebView.evaluateJavascript(call, new ValueCallbackWrapper<>(callback));
    }


    /**
     * 删除钱包
     * @param walletName     the execute wallet name
     * @param password       the execute wallet password
     * @param accountAddress
     */
    protected void removeWallet(@NonNull CharSequence walletName,
                                 @NonNull CharSequence password,
                                 @NonNull CharSequence accountAddress,
                                 @Nullable ValueCallback<String> callback) {
        accountAddress = "" + accountAddress + "";

        final String call = "javascript:removeWallet("
                + "\"" + walletName + "\","
                + "\"" + password + "\","
                + "\"" + accountAddress +"\")";
        Log.d(TAG, "execute:" + call);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                mWebView.evaluateJavascript(call, null);
            }
        });

    }


    private final class ValueCallbackWrapper<T> implements ValueCallback<T> {

        private ValueCallback<T> mBaseCallback;

        ValueCallbackWrapper(@Nullable ValueCallback<T> base) {
            mBaseCallback = base;
        }

        @Override
        public void onReceiveValue(T value) {
            Log.i(TAG, "onReceiveValue:" + value);
            if (mBaseCallback != null) {
                mBaseCallback.onReceiveValue(value);
            }
        }
    }
}
