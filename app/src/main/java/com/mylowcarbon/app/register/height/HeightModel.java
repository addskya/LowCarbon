package com.mylowcarbon.app.register.height;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class HeightModel implements HeightContract.Model {

    private ModifyHeightRequest mRequest;

    HeightModel() {
        mRequest = new ModifyHeightRequest();
    }

    @Override
    public Observable<Response<?>> modifyHeight(final int heightInCm) {
        return mRequest.modifyHeight(heightInCm)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            ModelDao.getInstance().modifyHeight(heightInCm);
                        }
                    }
                });
    }
}
