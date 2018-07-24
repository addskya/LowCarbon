package com.mylowcarbon.app.sts;

import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * 获取sts临时凭证
 * <p>
 * <p>
 * 错误码	说明
 * 1001	参数不正确（可能是参数没传，或者参数格式错误）
 * 1002	服务器内部错误
 * 1003	认证失败
 */
class StsRequest extends BaseRequest {
    private static final String TAG = "StsRequest";

    Observable<Response<StsInfo>> getSts() {
        Map<String, Object> params = new HashMap<>(1);
        return request("common/get-sts", params, StsInfo.class);
    }
}
