package com.mylowcarbon.app.my.email;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 * 1001     参数不正确（可能是参数没传，或者参数格式错误）
 * 1002     服务器内部错误
 * 1003     认证失败
 * 1004     未登录
 * 1005     你的账号在另外的设备登录，请重新登录
 * 2004     验证码不正确或者已失效
 */
class BindEmailRequest extends BaseRequest {

    Observable<Response<?>> sendVerifyCode(@NonNull CharSequence email) {
        Map<String, Object> params = newMap();
        params.put("email", valueOf(email));
        return request("common/send-mail", params);
    }

    Observable<Response<?>> modifyEmail(@NonNull CharSequence email,
                                        @NonNull CharSequence verifyCode) {
        Map<String, Object> params = newMap();
        params.put("email", valueOf(email));
        params.put("code", valueOf(verifyCode));
        return request("user/modify-email", params);
    }
}
