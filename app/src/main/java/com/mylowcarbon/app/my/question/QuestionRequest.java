package com.mylowcarbon.app.my.question;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 * 1001    参数不正确（可能是参数没传，或者参数格式错误）
 * 1002    服务器内部错误
 * 1003    认证失败
 * 1004    未登录
 * 1005    你的账号在另外的设备登录，请重新登录
 */

class QuestionRequest extends BaseRequest {

    Observable<Response<List<QuestionAnswer>>> verifyIdentity() {
        Map<String, Object> params = newMap();
        return request("user/get-probleminfo", params,
                new TypeToken<List<QuestionAnswer>>() {
                }.getType());
    }

    Observable<Response<List<Question>>> getQuestions() {
        Map<String, Object> params = newMap();
        return request("common/get-problem", params,
                new TypeToken<List<Question>>() {
                }.getType());
    }

    Observable<Response<?>> modifyAnswer(@NonNull JSONObject object) {
        Map<String, Object> params = newMap();
        Gson gson = new Gson();
        Object obj = gson.fromJson(object.toString(), Object.class);
        params.put("params", gson.toJsonTree(obj));
        // params.put("params", object);

        return request("user/set-problem", params);
    }
}
