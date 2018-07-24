package com.mylowcarbon.app.my.password.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 */
class EditLoginPwdModel implements EditLoginPwdContract.Model {

    private EditLoginPwdRequest mRequest;

    EditLoginPwdModel() {
        mRequest = new EditLoginPwdRequest();
    }

    @Override
    public Observable<Response<?>> modifyLoginPassword(
            @NonNull CharSequence oldPassword,
            @NonNull CharSequence newPassword,
            @NonNull CharSequence confirmPassword) {
        return mRequest.modifyLoginPassword(oldPassword, newPassword, confirmPassword);
    }
}
