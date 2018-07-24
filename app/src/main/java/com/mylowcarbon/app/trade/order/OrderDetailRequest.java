package com.mylowcarbon.app.trade.order;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.OrderDetail;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class OrderDetailRequest extends BaseRequest {


    /**
     * 更新订单状态
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<?>> updateOrderStatus(@NonNull  String order_sn,@NonNull int order_status){
        Map<String, Object> params = new HashMap<>(1);
        params.put("order_sn", order_sn);
        params.put("order_status", order_status);


        return request("coinorder/update-order-status", params);

    }
    /**
     * 添加评论
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<?>> comment(@NonNull  String order_sn,@NonNull int comment_type, @NonNull String remark,@NonNull  int role_type){
        Map<String, Object> params = new HashMap<>(1);
        params.put("order_sn", order_sn);
        params.put("comment_type", comment_type);
        params.put("remark", remark);
        params.put("role_type", role_type);


        return request("coinorder/comment", params);

    }
    /**
     * 获取订单详情
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     2010	该订单号不存在
     */
    Observable<Response<OrderDetail>> getDetailData(@NonNull  int order_id,@NonNull  int role_type ){
        Map<String, Object> params = new HashMap<>(1);
        params.put("order_id", order_id);
        params.put("role_type", role_type);


        return request("coinorder/get-detail-data", params,OrderDetail.class);

    }
}
