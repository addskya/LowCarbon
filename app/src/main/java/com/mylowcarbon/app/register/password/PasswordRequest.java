package com.mylowcarbon.app.register.password;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mylowcarbon.app.login.DeviceParameters;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * <p>
 * 1001         参数不正确（可能是参数没传，或者参数格式错误）
 * 1002         服务器内部错误
 * 1003         授权失败
 * 2002         该手机号已被注册
 * 2020         该设备已经被注册了
 */

class PasswordRequest extends BaseRequest {

    private Gson mGson;

    PasswordRequest() {
        mGson = new Gson();
    }

    Observable<Response<RegisterInfo>> commit(
            @NonNull CharSequence mobile,
            @NonNull CharSequence loginPassword,
            @NonNull CharSequence dealPassword,
            @NonNull CharSequence walletAddress,
            @NonNull CharSequence keystore,
            @Nullable CharSequence recommendCode,
            @NonNull DeviceParameters deviceParameters) {
        Map<String, Object> para = newMap();
        para.put("mobile", valueOf(mobile));
        para.put("login_pwd", valueOf(loginPassword));
        para.put("pay_pwd", valueOf(dealPassword));
        para.put("wallet_address", valueOf(walletAddress));
        para.put("keystore", valueOf(keystore));
        para.put("invitation_code", valueOf(recommendCode));
        para.put("device_params", mGson.toJsonTree(deviceParameters));
        return request("passport/register", para, RegisterInfo.class);
    }
}
