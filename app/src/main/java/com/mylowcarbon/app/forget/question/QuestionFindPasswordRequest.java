package com.mylowcarbon.app.forget.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * <p>
 * 1001          参数不正确（可能是参数没传，或者参数格式错误）
 * 1002          服务器内部错误
 * 1003          认证失败
 * 2005          该手机号码不存在
 * 2008          密保答案不正确
 */

class QuestionFindPasswordRequest extends BaseRequest {

    Observable<Response<List<Question>>> loadQuestions(@NonNull CharSequence mobile) {
        Map<String, Object> params = newMap();
        return request("common/get-problem", params, new TypeToken<List<Question>>() {
        }.getType());
    }

    Observable<Response<QuestionResp>> verifyQuestion(@NonNull CharSequence mobile,
                                           int questionId,
                                           @Nullable CharSequence answer) {
        Map<String, Object> params = newMap();
        params.put("mobile", valueOf(mobile));
        params.put("problem_id", questionId);
        params.put("answer", valueOf(answer));
        return request("passport/check-problem", params, QuestionResp.class);
    }
}
