package com.mylowcarbon.app;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

/**
 * Created by Orange on 18-5-1.
 * Email:addskya@163.com
 * <p>
 * 根据服务器反馈的错误码,映射对应的用户说明
 * <p>
 * 错误说明的定义必须是: error_code_ 开头
 * 比如: error_code_2020
 */
public class ErrorMapper {

    private static final String ERROR_PREFIX = "error_code_";

    public static int getErrorMessageId(@NonNull Context context,
                                        int errorCode) {
        Resources res = context.getResources();
        return res.getIdentifier(ERROR_PREFIX + errorCode,
                "string",
                context.getPackageName());
    }

    public static String getErrorMessage(@NonNull Context context,
                                         int errorCode) {
        int errorMessageId = getErrorMessageId(context, errorCode);
        if (errorMessageId == 0) {
            // 没有找到这个定义,
            return context.getString(R.string.error_code_unknown);
        }
        return context.getString(errorMessageId);
    }
}
