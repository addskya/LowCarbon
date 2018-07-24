package com.mylowcarbon.app.forget.account;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class AccountFindPasswordRequest extends BaseRequest {

    Observable<Response<?>> sendVerifyCode(@NonNull CharSequence mobile) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("is_register", false);
        return request("common/send-sms", params);
    }

}
