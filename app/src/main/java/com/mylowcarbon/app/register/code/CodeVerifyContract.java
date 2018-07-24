package com.mylowcarbon.app.register.code;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public interface CodeVerifyContract {

    interface View extends BaseView<Presenter> {

        void onVerifyCodeStart();

        void onVerifyCodeSuccess(@NonNull CharSequence phoneNumber,
                                 @NonNull CharSequence code);

        void onVerifyCodeFail(int errorCode);

        void onVerifyCodeError(Throwable error);

        void onVerifyCodeCompleted();
    }

    interface Presenter extends BasePresenter {
        void verifyCode(@NonNull CharSequence mobile,
                        @NonNull CharSequence code,
                        @NonNull boolean is_register);
    }

    interface Model {
        Observable<Response<?>> verifyCode(@NonNull CharSequence mobile,
                                                @NonNull CharSequence code,
                                           @NonNull boolean is_register);
    }
}
