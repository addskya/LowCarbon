
package com.mylowcarbon.app.my.nickname;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 */
class EditNicknameRequest extends BaseRequest {

    Observable<Response<?>> modifyNickname(@NonNull CharSequence nickname) {
        Map<String, Object> params = newMap();
        params.put("nickname", valueOf(nickname));
        return request("user/modify-nickname", params);
    }
}
