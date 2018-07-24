package com.mylowcarbon.app.register.phone;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 */
class PhoneVerifyModel implements PhoneVerifyContract.Model {

    private PhoneVerifyRequest mRequest;

    PhoneVerifyModel() {
        mRequest = new PhoneVerifyRequest();
    }

    @Override
    public Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile) {
        return mRequest.verifyPhoneNumber(mobile);
    }
}
