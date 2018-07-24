package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Orange on 18-3-8.
 * Email:addskya@163.com
 * 普通请求头信息
 */

class HeaderInterceptor extends BaseInterceptor {

    HeaderInterceptor() {
    }

    @Override
    void addHeaders(@NonNull Map<String, String> headers) {
        headers.put("app-key", "pZzHfOq3fhlv57FsH2Hk5h2LKNS33s35");
        //测试需要 暂时写死
//        headers.put("access-token", "c44094b0-3e00-11e8-95f3-d36f969eec45");
    }
}
