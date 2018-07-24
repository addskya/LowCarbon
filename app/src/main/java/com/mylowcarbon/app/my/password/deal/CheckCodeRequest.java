package com.mylowcarbon.app.my.password.deal;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 * 1002     服务器内部错误
 * 1003     认证失败
 * 1004     未登录
 */
class CheckCodeRequest extends BaseRequest {

    Observable<Response<PayEntry>> queryPayPwd() {
        Map<String, Object> params = new HashMap<>(1);
        return request("user/show-paypwd", params, PayEntry.class);
    }
}
