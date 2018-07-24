package com.mylowcarbon.app.home.trade;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Trade;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class TradeRequest extends BaseRequest {

    /**
     * 获取交易大厅数据
     * <p>
     * <p>
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
     Observable<Response<Trade>> getTradeData(@NonNull CharSequence mobile,
                                              @NonNull boolean is_register,
                                              @NonNull int last_id){

        Map<String, Object> params = new HashMap<>(1);
//        params.put("mobile", valueOf(mobile));
//        params.put("is_register", is_register);
         //首次不传
         if(last_id > 0 ){
             params.put("last_id", valueOf(last_id));
         }

        return request("trade/get-data", params,Trade.class);

    }

}
