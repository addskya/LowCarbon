package com.mylowcarbon.app.my.verify;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 * <p>
 * 1001 	参数不正确（可能是参数没传，或者参数格式错误）
 * 1002 	服务器内部错误
 * 1003 	认证失败
 * 1004 	未登录
 * 1005 	你的账号在另外的设备登录，请重新登录
 * 2008 	密保答案不正确
 */
class VerifyIdentityRequest extends BaseRequest {

    Observable<Response<List<Problem>>> loadProblem() {
        Map<String, Object> params = newMap();
        return request("common/get-problem", params, new TypeToken<List<Problem>>() {
        }.getType());
    }

    Observable<Response<?>> checkProblem(int problemId,
                                         @NonNull CharSequence answer) {
        Map<String, Object> params = newMap();
        params.put("problem_id", problemId);
        params.put("answer", valueOf(answer));
        return request("user/check-problem", params);
    }
}
