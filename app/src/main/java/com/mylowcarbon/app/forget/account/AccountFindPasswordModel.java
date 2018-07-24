package com.mylowcarbon.app.forget.account;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class AccountFindPasswordModel implements AccountFindPasswordContract.Mode {

    private AccountFindPasswordRequest mRequest;

    AccountFindPasswordModel() {
        mRequest = new AccountFindPasswordRequest();
    }

    @Override
    public Observable<Response<?>> sendVerifyCode(@NonNull CharSequence mobile) {
        return mRequest.sendVerifyCode(mobile);
    }
}
