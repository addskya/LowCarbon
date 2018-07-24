package com.mylowcarbon.app.forget.password;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class ResetPasswordRequest extends BaseRequest {

    Observable<Response<?>> commit(@NonNull CharSequence mobile,
                                   @NonNull CharSequence password,
                                   @NonNull CharSequence confirmPassword) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("password", valueOf(password));
        params.put("confirm_password", valueOf(confirmPassword));
        return request("common/modify-password", params);
    }
}
