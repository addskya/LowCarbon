package com.mylowcarbon.app.exchange;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
class ExchangesRequest extends BaseRequest {

    Observable<Response<List<Device>>> getDevices(
            @NonNull CharSequence condition) {
        Map<String, Object> params = newMap();
        params.put("condition", valueOf(condition));

        return request("mining/mydevice", params,
                new TypeToken<List<Device>>() {
                }.getType());
    }
}
