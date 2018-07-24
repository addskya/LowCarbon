package com.mylowcarbon.app.register.code;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * <p>
 * mobile        手机号码
 * code          手机验证码
 * is_register   是否是注册
 * <p>
 * <p>
 * 1001    参数不正确（可能是参数没传，或者参数格式错误）
 * 1003    授权失败
 * 2002    该手机号已被注册(注册的时候)
 * 2004    验证码不正确或者已失效
 * 2005    该手机号码不存在（非注册的时候）
 */
class CodeVerifyRequest extends BaseRequest {
    private static final String TAG = "CodeVerifyRequest";

    Observable<Response<?>> verifyCode(@NonNull CharSequence mobile,
                                       @NonNull CharSequence code,
                                       boolean isRegister) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("code", valueOf(code));
        params.put("is_register", isRegister);
        return request("common/check-code", params);
    }
}
