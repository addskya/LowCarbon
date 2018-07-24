package com.mylowcarbon.app.trade.mytrade;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class MyTradeFragmentRequest extends BaseRequest {


    /**
     * 卖出列表
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<MyTradeDetail>> getSellData(@NonNull int last_time){
        Map<String, Object> params = new HashMap<>(1);
        //首次不传
        if(last_time > 0 ){
            params.put("last_rank", valueOf(last_time));
        }
        return request("trade/get-sale-data", params,MyTradeDetail.class);

    }
    /**
     * 买入列表
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<MyTradeDetail>> getBuyData(@NonNull int last_id){
        Map<String, Object> params = new HashMap<>(1);
        //首次不传
        if(last_id > 0 ){
            params.put("last_id", valueOf(last_id));
        }
        return request("trade/get-buy-data", params,MyTradeDetail.class);

    }
    /**
     * 已完成列表
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<MyTradeDetail>> getCompleteData(@NonNull int last_time){
        Map<String, Object> params = new HashMap<>(1);
        //首次不传
        if(last_time > 0 ){
            params.put("last_time", valueOf(last_time));
        }
        return request("trade/get-complete-data", params,MyTradeDetail.class);

    }
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
    Observable<Response<?>> cancelOrder(@NonNull  int coin_id ){
        Map<String, Object> params = new HashMap<>(1);
        params.put("coin_id", coin_id);

        return request("coinorder/cancel-order", params);

    }
}
