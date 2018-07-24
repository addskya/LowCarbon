package com.mylowcarbon.app.trade.sell;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


class OrderConfirmRequest extends BaseRequest {


    /**
     * 挂单确认
     * <p>
     * <p>
     * 错误码	说明
     1001	参数不正确（可能是参数没传，或者参数格式错误）
     1002	服务器内部错误
     1003	授权失败
     1004	未登录
     1005	你的账号在另外的设备登录，请重新登录
     */
    Observable<Response<?>> comfirm(@NonNull  float number,
                                    @NonNull int price,
                                    @NonNull CharSequence set_today_time,
                                    @NonNull CharSequence set_second_time,
                                    @NonNull CharSequence set_third_time,
                                    @NonNull CharSequence set_fourth_time,
                                    @NonNull CharSequence set_fifth_time,
                                    @NonNull CharSequence set_sixth_time,
                                    @NonNull CharSequence set_seventh_time){
        Map<String, Object> params = new HashMap<>(1);
        params.put("number", number);
        params.put("price", price);
        params.put("set_today_time", valueOf(set_today_time));
        params.put("set_second_time", valueOf(set_second_time));
        params.put("set_third_time", valueOf(set_third_time));
        params.put("set_fourth_time", valueOf(set_fourth_time));
        params.put("set_fifth_time", valueOf(set_fifth_time));
        params.put("set_sixth_time", valueOf(set_sixth_time));
        params.put("set_seventh_time", valueOf(set_seventh_time));

        return request("trade/comfirm-guadan", params);

    }
}
