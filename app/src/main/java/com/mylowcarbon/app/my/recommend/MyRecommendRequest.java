package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyRecommend;
import com.mylowcarbon.app.net.response.MyWallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * 获取sts临时凭证
 * <p>
 * 参数名	必选	类型	说明
 *
 * last_id	否	int	最后一条id
 * <p>
 * 1001	参数不正确（可能是参数没传，或者参数格式错误）
 * 1002	服务器内部错误
 * 1003	授权失败
 * 1004	未登录
 * 1005	你的账号在另外的设备登录，请重新登录
 */
class MyRecommendRequest extends BaseRequest {
    private static final String TAG = "MyRecommendRequest";

    Observable<Response<MyRecommend>> getMyRecommend(
                                               @NonNull String last_id) {
        Map<String, Object> params = new HashMap<>(1);
        //首次不传
        if(!TextUtils.equals(last_id,"0")){
            params.put("last_id", valueOf(last_id));
        }

        return request("user/my-recommend", params, MyRecommend.class);
    }
}
