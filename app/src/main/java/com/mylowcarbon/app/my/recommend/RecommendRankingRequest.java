package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyRecommend;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.net.response.RecommendRank;

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
class RecommendRankingRequest extends BaseRequest {
    private static final String TAG = "MyRecommendRequest";

    Observable<Response<RecommendRank>> getRecommendRank(@NonNull String start_time,
                                                         @NonNull String end_time,
                                                         @NonNull String last_id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("start_time", valueOf(start_time));
        params.put("end_time", valueOf(end_time));
        //首次不传
        if(!TextUtils.equals(last_id,"0")){
            params.put("last_rank", valueOf(last_id));
        }

        return request("user/recommend-rank", params, RecommendRank.class);
    }
}
