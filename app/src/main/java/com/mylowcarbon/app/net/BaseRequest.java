package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mylowcarbon.app.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 * 抽象网络请求,
 * 后续所有自己的请求请写在各自模块内部
 * <p>
 * 特别注意:在HashMap中如果要传String类型,
 * 千万不要用 CharSequence
 * 而是写成:valueOf(CharSequence)
 */

public abstract class BaseRequest {

    private static final String TAG = "BaseRequest";

    private Api mApi;
    private final Gson mGson;

    protected BaseRequest() {
        mGson = new Gson();

        RetrofitManager manager = RetrofitManager.getInstance();
        mApi = manager.getRetrofit().create(Api.class);
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api          get_version
     * @param responseType 返回数据的协议Entry类
     * @return Observable包装的响应
     */
    protected <T> Observable<Response<T>> get(@NonNull final String api,
                                              @NonNull final Type responseType) {
        LogUtil.d(TAG, "call api : " + api);
        return mApi.get(api)
                .map(new Func1<String, Response<T>>() {
                    @Override
                    public Response<T> call(String resp) {
                        int code = getResponseCode(resp);
                        String msg = getResponseMsg(resp);
                        T value = getResponseValue(resp, responseType);
                        return new Response<>(code, msg, value);
                    }
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: http://xx.com/passport/send-sms
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api  send-sms
     * @param para 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    protected Observable<Response<?>> request(@NonNull final String api,
                                              @Nullable final Map<String, Object> para) {
        LogUtil.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.request(api, para)
                .map(new Func1<String, Response<?>>() {
                    @Override
                    public Response<?> call(String resp) {
                        int code = getResponseCode(resp);
                        String msg = getResponseMsg(resp);
                        return new Response<>(code, msg, null);
                    }
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api          get_version
     * @param para         请求指定接口时,需要的参数
     * @param responseType 返回数据的协议Entry类
     * @return Observable包装的响应
     */
    protected <T> Observable<Response<T>> request(@NonNull final String api,
                                                  @Nullable final Map<String, Object> para,
                                                  @NonNull final Type responseType) {
        LogUtil.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.request(api, para)
                .map(new Func1<String, Response<T>>() {
                    @Override
                    public Response<T> call(String resp) {
                        int code = getResponseCode(resp);
                        String msg = getResponseMsg(resp);
                        T value = getResponseValue(resp, responseType);
                        return new Response<>(code, msg, value);
                    }
                });
    }

    protected <T> Observable<Response<T>> upload(@NonNull final String api,
                                                 @NonNull final Map<String, RequestBody> para) {
        LogUtil.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.upload(api, para)
                .map(new Func1<String, Response<T>>() {
                    @Override
                    public Response<T> call(String resp) {
                        int code = getResponseCode(resp);
                        String msg = getResponseMsg(resp);
                        T value = getResponseValue(resp, String.class);
                        return new Response<>(code, msg, value);
                    }
                });
    }

    /**
     * 解析服务器返回的Code
     *
     * @param responseJson 服务器返回的json数据
     * @return the code.
     */
    protected static int getResponseCode(@NonNull String responseJson) {
        final int invalidCode = Response.INVALID_CODE;
        if (TextUtils.isEmpty(responseJson)) {
            return invalidCode;
        }
        final String KEY = "code";
        JSONObject json = toJsonObject(responseJson);
        int code = json != null ? json.optInt(KEY, invalidCode) : invalidCode;
        json = null;
        return code;
    }

    /**
     * 解析服务器返回的msg
     *
     * @param responseJson 服务器返回的json数据
     * @return the meg
     */
    private static String getResponseMsg(@NonNull String responseJson) {
        if (TextUtils.isEmpty(responseJson)) {
            return null;
        }

        final String KEY = "msg";
        JSONObject json = toJsonObject(responseJson);
        String msg = json != null ? json.optString(KEY, null) : null;
        json = null;
        return msg;

    }

    /**
     * 解析服务器返回的value
     *
     * @param responseJson 服务器返回的json数据
     * @param responseType 需要转换的类型
     * @param <T>          需要转换的类型
     * @return 转换后的数据
     */
    private <T> T getResponseValue(@NonNull String responseJson,
                                   @NonNull Type responseType) {
        if (TextUtils.isEmpty(responseJson)) {
            return null;
        }

        final String KEY = "value";

        JSONObject json = toJsonObject(responseJson);
        String value = json != null ? json.optString(KEY, null) : null;
        json = null;
        // msg maybe null or "null"

        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) {
            return null;
        }

        return mGson.fromJson(value, responseType);
    }

    /**
     * 将字符串转换为JsonObject对象
     *
     * @param jsonString 字符串
     * @return JsonObject对象
     */
    private static JSONObject toJsonObject(@NonNull String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将参数转换成字符串数据
     *
     * @param value params
     * @return the String params
     */
    protected static String valueOf(@Nullable Object value) {
        return String.valueOf(value);
    }

    /**
     * 产生一个新的Map<String,Object> 对象
     *
     * @return Map<String.Object> 对象
     */
    protected static Map<String, Object> newMap() {
        return new HashMap<>(1);
    }
}
