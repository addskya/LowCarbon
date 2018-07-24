package com.mylowcarbon.app.splash;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

class SplashRequest extends BaseRequest {
    private static final String TAG = "MyWalletRequest";

    Observable<Response<Logo>> getLogo() {
        Map<String, Object> params = newMap();
        return request("common/get-start-img", params, Logo.class);
    }
}
