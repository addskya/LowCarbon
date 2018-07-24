package com.mylowcarbon.app.mine.mining;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

class MiningModel implements MiningContract.Model {

    private MiningRequest mRequest;

    MiningModel() {
        mRequest = new MiningRequest();
    }

    @Override
    public Observable<Response<Mining>> getTodayMining(
            @NonNull CharSequence imei,
            @NonNull Sport data) {
        return mRequest.getTodayMining(imei, data);
    }
}
