package com.mylowcarbon.app.bracelet.own;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;


class DevicesRequest extends BaseRequest {

    /**
     * 获取用户设备列表及数据
     * <p>
     * <p>
     * 1002	服务器内部错误
     * 1003	认证失败
     */
    Observable<Response<List<Device>>> getDevice(@NonNull CharSequence condition) {

        Map<String, Object> params = newMap();
        params.put("condition", valueOf(condition));

        return request("mining/mydevice", params, new TypeToken<List<Device>>(){
        }.getType());
    }

}
