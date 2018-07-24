package com.mylowcarbon.app.my.email;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 */
class BindEmailModel implements BindEmailContract.Model {

    private BindEmailRequest mRequest;

    BindEmailModel() {
        mRequest = new BindEmailRequest();
    }

    @Override
    public Observable<Response<?>> sendVerifyCode(@NonNull CharSequence email) {
        return mRequest.sendVerifyCode(email);
    }

    @Override
    public Observable<Response<?>> modifyEmail(@NonNull final CharSequence email,
                                               @NonNull CharSequence verifyCode) {
        return mRequest.modifyEmail(email, verifyCode)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            ModelDao.getInstance().modifyEmail(email);
                        }
                    }
                });
    }
}
