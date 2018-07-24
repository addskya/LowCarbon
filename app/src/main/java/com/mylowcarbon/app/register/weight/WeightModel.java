package com.mylowcarbon.app.register.weight;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class WeightModel implements WeightContract.Model {

    private WeightRequest mRequest;

    WeightModel() {
        mRequest = new WeightRequest();
    }

    @Override
    public Observable<Response<?>> modifyWeight(final int weightInKg) {
        return mRequest.modifyWeight(weightInKg)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            ModelDao.getInstance().modifyWeight(weightInKg);
                        }
                    }
                });
    }
}
