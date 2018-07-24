package com.mylowcarbon.app.trade.buy;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Buy;
import com.mylowcarbon.app.net.response.OrderDetail;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class BuyRequest extends BaseRequest {

    /**
     *买入详情数据
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
     Observable<Response<Buy>> getDetailData(@NonNull  int coin_id){

        Map<String, Object> params = new HashMap<>(1);
        params.put("coin_id",  coin_id );
        return request("trade/get-detail-data", params,Buy.class);

    }
    /**
     *买入下单
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
     Observable<Response<OrderDetail>> comfirm(@NonNull int coin_id,@NonNull int number){

        Map<String, Object> params = new HashMap<>(1);
        params.put("coin_id",  coin_id );
        params.put("number",  number );
        return request("coinorder/comfirm", params,OrderDetail.class);

    }

}
