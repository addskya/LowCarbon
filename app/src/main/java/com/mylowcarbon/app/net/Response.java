package com.mylowcarbon.app.net;

import android.support.annotation.Nullable;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 * 服务请求响应,
 * 2018,03,21 the response like:
 * {"code":2005,"msg":"\u8be5\u624b\u673a\u53f7\u7801\u4e0d\u5b58\u5728","value":null}
 */

public class Response<T> {
    private static final int SUCCESS_CODE = 200;
    public static final int INVALID_CODE = -1;

    private int code = INVALID_CODE;
    private String msg;
    private T value;

    public Response() {
    }

    public Response(int code, String message, T value) {
        this.code = code;
        this.msg = message;
        this.value = value;
    }

    /**
     * Whether or NOT the response is SUCCESS
     *
     * @return true if response code is 200
     */
    public boolean isSuccess() {
        return getCode() == SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", value=" + value +
                '}';
    }

    /**
     * get the response code
     *
     * @param response the response from Web server
     * @return the response code or {@code INVALID_CODE}
     */
    public static int getResponseCode(@Nullable Response response) {
        return response != null ? response.getCode() : INVALID_CODE;
    }
}
