package com.mylowcarbon.app.my.authentication;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * 身份认证
 * <p>
 * <p>
 * 错误码	说明
 * 1001	参数不正确（可能是参数没传，或者参数格式错误）
 1002	服务器内部错误
 1003	认证失败
 1004	未登录
 1005	你的账号在另外的设备登录，请重新登录
 */
class AuthenticationRequest extends BaseRequest {
    private static final String TAG = "AuthenticationRequest";

    Observable<Response<?>> identityAuth(@NonNull final CharSequence user_name,
                                               @NonNull CharSequence id_num,
                                               @NonNull CharSequence id_num_imgs) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("user_name", valueOf(user_name));
        params.put("id_num", valueOf(id_num));
        params.put("id_num_imgs", valueOf(id_num_imgs));

        return request("user/identity-auth", params);
    }
}
