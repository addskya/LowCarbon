package com.mylowcarbon.app.register.height;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 * 1001     参数不正确（可能是参数没传，或者参数格式错误）
 * 1002     服务器内部错误
 * 1003     认证失败
 * 1004     未登录
 * 1005     你的账号在另外的设备登录，请重新登录
 */
class ModifyHeightRequest extends BaseRequest {

    /**
     * 身高（单位:cm）
     *
     * @param heightInCm 身高（单位:cm）
     * @return Response
     */
    Observable<Response<?>> modifyHeight(int heightInCm) {
        Map<String, Object> params = newMap();
        params.put("height", heightInCm);
        return request("user/modify-height", params);
    }
}
