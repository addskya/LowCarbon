package com.mylowcarbon.app.register.phone;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 * mobile            手机号码
 * is_register       是否是注册
 * <p>
 * <p>
 * 1001         参数不正确（可能是参数没传，或者参数格式错误）
 * 1003         授权失败
 * 2001         手机号码格式不正确
 * 2002         该手机号已被注册(注册的时候)
 * 2003         短信发送出现异常
 * 2005         该手机号码不存在（非注册的时候）
 */
class PhoneVerifyRequest extends BaseRequest {

    Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("is_register", true);
        return request("common/send-sms", params);
    }
}
