package com.mylowcarbon.app.trade.mytrade.childorder;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyChildOrder;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class ChildOrderRequest extends BaseRequest {


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
    Observable<Response<MyChildOrder>> getListData(@NonNull int coin_id , @NonNull int last_id){
        Map<String, Object> params = new HashMap<>(1);
        //首次不传
        if(last_id > 0 ){
            params.put("last_rank", valueOf(last_id));
        }
        params.put("coin_id", valueOf(coin_id));
        return request("coinorder/get-list-data", params,MyChildOrder.class);

    }


}
