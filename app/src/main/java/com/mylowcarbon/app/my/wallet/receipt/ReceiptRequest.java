package com.mylowcarbon.app.my.wallet.receipt;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Receipt;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class ReceiptRequest extends BaseRequest {

    /**
     * 获取收款账号信息
     * <p>
     * <p>
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     2014	该钱包地址不存在
     */
     Observable<Response<Receipt>> getReceiptInfo( ){

        Map<String, Object> params = new HashMap<>(1);


        return request("wallet/get-accountinfo", params,Receipt.class);

    }
    /**
     * 修改收款账号信息
     * <p>
     * <p>
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	认证失败
     1004	未登录
     */
    Observable<Response<?>> modifyPayInfo(@NonNull  CharSequence alipay_account,
                                     @NonNull CharSequence wechat_code,
                                     @NonNull int card_id,@NonNull int pay_type,@NonNull int show_account) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("alipay_account", valueOf(alipay_account));
        params.put("wechat_code", valueOf(wechat_code));
        params.put("card_id",card_id);
        params.put("pay_type",pay_type);
        params.put("show_account",show_account);
        return request("wallet/modify-payinfo", params);

    }
}
