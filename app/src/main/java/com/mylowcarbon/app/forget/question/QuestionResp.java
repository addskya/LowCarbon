package com.mylowcarbon.app.forget.question;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 * 验证密保 只服务器回应
 */

class QuestionResp {

    @SerializedName("access_token")
    private String mAccessToken;

    public String getAccessToken() {
        return mAccessToken;
    }

}
