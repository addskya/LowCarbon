package com.mylowcarbon.app.my.wallet.bankcard;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.BankType;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class ChooseBankRequest extends BaseRequest {

    /**
     * 根据钱包地址获取用户信息
     * <p>
     * <p>
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     * 1002	服务器内部错误
     * 1003	授权失败
     * 1004	未登录
     * 1005	你的账号在另外的设备登录，请重新登录
     * 2014	该钱包地址不存在
     */
    Observable<Response<BankType>> queryByBankNum(@NonNull CharSequence bankNum) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("card_number", valueOf(bankNum));


        return request("wallet/query-by-cardnum", params, BankType.class);

    }

    /**
     * 添加银行卡
     * <p>
     * <p>
     * 错误码	说明
     * 1001	参数不正确（可能是参数没传，或者参数格式错误）
     * 1002	服务器内部错误
     * 1003	认证失败
     * 1004	未登录
     */
    Observable<Response<?>> addCard(@NonNull CharSequence user_name,
                                    @NonNull CharSequence card_number,
                                    @NonNull CharSequence card_type,
                                    @NonNull CharSequence card_mobile) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("user_name", valueOf(user_name));
        params.put("card_number", valueOf(card_number));
        params.put("card_type", valueOf(card_type));
        params.put("card_mobile", valueOf(card_mobile));

        return request("wallet/add-card", params);

    }

    /**
     * mobile            手机号码
     * is_register       是否是注册
     * <p>
     * <p>
     * 1001         参数不正确（可能是参数没传，或者参数格式错误）
     * 1003         授权失败
     * 2001         手机号码格式不正确
     * 2002         该手机号已被注册(注册的时候)
     * 2003         短信发送出现异常
     * 2005         该手机号码不存在（非注册的时候）
     */

    Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("mobile", valueOf(mobile));
        params.put("is_register", false);
        return request("common/send-sms", params);
    }

}
