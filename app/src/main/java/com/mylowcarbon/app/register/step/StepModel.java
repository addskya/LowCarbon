package com.mylowcarbon.app.register.step;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class StepModel implements StepContract.Model {

    private StepRequest mRequest;

    StepModel() {
        mRequest = new StepRequest();
    }

    @Override
    public Observable<Response<?>> modifyStep(int stepInCm) {
        return mRequest.modifyStep(stepInCm);
    }
}
