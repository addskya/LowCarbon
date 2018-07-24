package com.mylowcarbon.app.exchange.fragment;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.Mining;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
class ExchangeRequest extends BaseRequest {

    Observable<Response<ExchangeResp>> exchangeAll(
            @NonNull CharSequence date) {
        Map<String, Object> params = newMap();
        params.put("date", valueOf(date));
        return request("mining/exchange", params, ExchangeResp.class);
    }

    Observable<Response<ExchangeResp>> exchange(@NonNull Device device,
                                                @NonNull Mining mining) {
        Map<String, Object> params = newMap();
        params.put("date", device.getDate());
        params.put("imei", mining.getImei());
        return request("mining/exchange", params, ExchangeResp.class);
    }
}
