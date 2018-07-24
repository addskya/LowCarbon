package com.mylowcarbon.app.exchange.fragment;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.Mining;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *
 */
class ExchangeModel implements ExchangeContract.Model {

    private ExchangeRequest mRequest;

    ExchangeModel() {
        mRequest = new ExchangeRequest();
    }

    @Override
    public Observable<Response<ExchangeResp>> exchangeAll(
            @NonNull CharSequence date) {
        return mRequest.exchangeAll(date);
    }

    @Override
    public Observable<Response<ExchangeResp>> exchange(
            @NonNull Device device,
            @NonNull Mining mining) {
        return mRequest.exchange(device, mining);
    }
}
