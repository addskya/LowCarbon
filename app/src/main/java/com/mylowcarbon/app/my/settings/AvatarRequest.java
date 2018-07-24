package com.mylowcarbon.app.my.settings;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-29.
 * Email:addskya@163.com
 */
class AvatarRequest extends BaseRequest {

    Observable<Response<?>> modifyAvatar(@NonNull CharSequence url) {
        Map<String, Object> params = newMap();
        params.put("avatar", valueOf(url));
        return request("user/modify-avatar", params);
    }
}
