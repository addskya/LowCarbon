package com.mylowcarbon.app.register.gender;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class GenderModel implements GenderContract.Model {

    private GenderRequest mRequest;

    GenderModel() {
        mRequest = new GenderRequest();
    }

    @Override
    public Observable<Response<?>> modifySex(final int sex) {
        return mRequest.changeSex(sex)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            ModelDao.getInstance().modifyGender(sex);
                        }
                    }
                });
    }
}
