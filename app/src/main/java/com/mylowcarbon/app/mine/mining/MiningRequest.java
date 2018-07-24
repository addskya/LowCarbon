package com.mylowcarbon.app.mine.mining;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 * 1001    参数不正确（可能是参数没传，或者参数格式错误）
 * 1002    服务器内部错误
 * 1003    认证失败
 */

class MiningRequest extends BaseRequest {

    private Gson mGson;

    MiningRequest() {
        mGson = new Gson();
    }

    Observable<Response<Mining>> getTodayMining(
            @NonNull CharSequence imei,
            @NonNull Sport data) {
        Map<String, Object> params = newMap();
        params.put("imei", valueOf(imei));
        params.put("data", mGson.toJsonTree(data));
        return request("mining/today", params, Mining.class);
    }
}
