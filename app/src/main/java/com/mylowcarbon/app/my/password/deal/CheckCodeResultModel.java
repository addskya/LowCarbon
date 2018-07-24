package com.mylowcarbon.app.my.password.deal;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */
class CheckCodeResultModel implements CheckCodeResultContract.Model {

    private CheckCodeRequest mRequest;

    CheckCodeResultModel() {
        mRequest = new CheckCodeRequest();
    }

    @Override
    public Observable<Response<PayEntry>> queryPayPwd() {
        return mRequest.queryPayPwd();
    }
}
