package com.mylowcarbon.app.my.wallet.transfer;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class TransferRequest extends BaseRequest {

    /**
     * 根据钱包地址获取用户信息
     * <p>
     * <p>
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     2014	该钱包地址不存在
     */
     Observable<Response<Wallet>> queryByWalletAddress(@NonNull  CharSequence wallet_address){

        Map<String, Object> params = new HashMap<>(1);
        params.put("wallet_address", valueOf(wallet_address));


        return request("wallet/query-by-walletaddress", params,Wallet.class);

    }
    /**
     * 转账
     * <p>
     * <p>
     * 错误码	说明
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     2014	该钱包地址不存在
     2015	交易密码不正确
     2016	钱包币数不够
     */
    Observable<Response<?>> transfer(@NonNull final CharSequence wallet_address,
                                     @NonNull CharSequence amount,
                                     @NonNull CharSequence pay_pwd) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("wallet_address", valueOf(wallet_address));
        params.put("amount", valueOf(amount));
        params.put("pay_pwd", valueOf(pay_pwd));

        return request("wallet/transfer", params);

    }
}
