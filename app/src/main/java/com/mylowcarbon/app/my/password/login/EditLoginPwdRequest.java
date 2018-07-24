package com.mylowcarbon.app.my.password.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 * <p>
 * 1001     参数不正确（可能是参数没传，或者参数格式错误）
 * 1002     服务器内部错误
 * 1003     认证失败
 * 1004     未登录
 * 1005     你的账号在另外的设备登录，请重新登录
 * 2009     两次密码不一致
 * 2017     原始密码不正确
 */
class EditLoginPwdRequest extends BaseRequest {

    public Observable<Response<?>> modifyLoginPassword(
            @NonNull CharSequence oldPassword,
            @NonNull CharSequence newPassword,
            @NonNull CharSequence confirmPassword) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("old_password", valueOf(oldPassword));
        params.put("password", valueOf(newPassword));
        params.put("confirm_password", valueOf(confirmPassword));
        return request("user/modify-password", params);
    }
}
