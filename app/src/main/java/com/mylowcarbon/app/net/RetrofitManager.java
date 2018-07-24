package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.constant.AppConstants;

import retrofit2.Retrofit;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 * 网络的创建管理工具类
 */

class RetrofitManager {
    private static final String TAG = "RetrofitManager";

    private final Retrofit mRetrofit;

    // 偷个懒
    private static RetrofitManager sInstance = new RetrofitManager();

    private RetrofitManager() {
        mRetrofit = RetrofitConfig.getRetrofit(AppConstants.BASE_URL);
    }

    @NonNull
    static RetrofitManager getInstance() {
        return sInstance;
    }

    @NonNull
    Retrofit getRetrofit() {
        return mRetrofit;
    }

}
