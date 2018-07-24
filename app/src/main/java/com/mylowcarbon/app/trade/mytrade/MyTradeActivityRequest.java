package com.mylowcarbon.app.trade.mytrade;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTrade;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class MyTradeActivityRequest extends BaseRequest {


    /**
     * 我的钱包信息
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<MyTrade>> getWalletData(){
        Map<String, Object> params = new HashMap<>(1);
        return request("trade/get-wallet-data", params,MyTrade.class);

    }

}
