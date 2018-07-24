package com.mylowcarbon.app.exchange;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
class ExchangesModel implements ExchangesContract.Model {

    private ExchangesRequest mRequest;

    ExchangesModel() {
        mRequest = new ExchangesRequest();
    }

    @Override
    public Observable<Response<List<Device>>> getDevices(
            @NonNull CharSequence condition) {
        return mRequest.getDevices(condition);
    }
}
