package com.mylowcarbon.app.bracelet.link;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-25.
 * Email:addskya@163.com
 * <p>
 * 1001    参数不正确（可能是参数没传，或者参数格式错误）
 * 1002    服务器内部错误
 * 1003    认证失败
 */

class UploadRequest extends BaseRequest {

    private Gson mGson;

    UploadRequest() {
        mGson = new Gson();
    }

    Observable<Response<?>> upload(@NonNull CharSequence imei,
                                   @Nullable List<SportInfo> data) {
        Map<String, Object> params = newMap();
        params.put("imei", valueOf(imei));
        params.put("data", mGson.toJsonTree(data));
        return request("mining/upload", params);
    }
}
