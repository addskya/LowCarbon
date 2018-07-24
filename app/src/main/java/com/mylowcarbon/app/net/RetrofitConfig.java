package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 */

class RetrofitConfig {

    private static final String TAG = "RetrofitConfig";
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final int TIME_OUT = 20;
    private static final int TIME_OUT_CONNECT = TIME_OUT;
    private static final int TIME_OUT_READ = TIME_OUT;
    private static final int TIME_OUT_WRITE = TIME_OUT;

    RetrofitConfig() {

    }

    static Retrofit getRetrofit(@NonNull String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new LogInterceptor())
                .build();
    }
}
