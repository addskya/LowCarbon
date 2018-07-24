package com.mylowcarbon.app.forget.password;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class ResetPasswordModel implements ResetPasswordContract.Model {

    private ResetPasswordRequest mRequest;

    ResetPasswordModel() {
        mRequest = new ResetPasswordRequest();
    }

    @Override
    public Observable<Response<?>> commit(@NonNull CharSequence mobile,
                                          @NonNull CharSequence password,
                                          @NonNull CharSequence confirmPassword) {
        return mRequest.commit(mobile, password, confirmPassword);
    }
}
