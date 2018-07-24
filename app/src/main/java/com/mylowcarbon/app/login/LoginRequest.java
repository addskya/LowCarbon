package com.mylowcarbon.app.login;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 * <p>
 * <p>
 * 1001    参数不正确（可能是参数没传，或者参数格式错误）
 * 1002    服务器内部错误
 * 1003    授权失败
 * 2005    该手机号码不存在
 * 2006    密码不正确
 * 2007    密码输入次数过多，30分钟后再重试
 */
class LoginRequest extends BaseRequest {

    private Gson mGson;

    LoginRequest() {
        mGson = new Gson();
    }

    Observable<Response<UserInfo>> login(@NonNull CharSequence mobile,
                                         @NonNull CharSequence password,
                                         @NonNull DeviceParameters deviceParams) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("password", valueOf(password));
        params.put("device_params", mGson.toJsonTree(deviceParams));
        return request("passport/login", params, UserInfo.class);
    }

}
