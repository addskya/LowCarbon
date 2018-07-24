package com.mylowcarbon.app.my.complaints;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.About;
import com.mylowcarbon.app.net.response.Complain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 *
 * <p>
 * <p>
 * 错误码	说明
 * 1001	参数不正确（可能是参数没传，或者参数格式错误）
 1002	服务器内部错误
 1003	认证失败
 1004	未登录
 1005	你的账号在另外的设备登录，请重新登录
 */
class ComplaintsRequest extends BaseRequest {
    private static final String TAG = "ComplaintsRequest";

    Observable<Response<?>> addComplain(@NonNull final CharSequence user_name,
                                               @NonNull CharSequence id_num,
                                               @NonNull CharSequence id_num_imgs) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("complain_object", valueOf(user_name));
        params.put("complain_id", valueOf(id_num));
        params.put("content", valueOf(id_num_imgs));

        return request("common/add-complain", params);
    }



    Observable<Response<Complain>> getComplainData() {
        Map<String, Object> params = new HashMap<>(1);

        return request("common/get-complain-data", params, new TypeToken<Complain>() {
        }.getType());
    }
}
