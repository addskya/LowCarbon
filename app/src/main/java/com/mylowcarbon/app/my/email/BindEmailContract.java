package com.mylowcarbon.app.my.email;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * WalkSubFragment契约类
 */
public interface BindEmailContract {

    interface View extends BaseView<Presenter> {
        void sendVerifyCode();

        void commit();

        void onSendVerifyCodeStart();

        void onSendVerifyCodeSuccess(@NonNull CharSequence email);

        void onSendVerifyCodeFail(int errorCode);

        void onSendVerifyCodeError(Throwable error);

        void onSendVerifyCodeCompleted();


        void onModifyEmailStart();

        void onModifyEmailSuccess(@NonNull CharSequence email);

        void onModifyEmailFail(int errorCode);

        void onModifyEmailError(Throwable error);

        void onModifyEmailCompleted();
    }

    interface Presenter extends BasePresenter {
        void sendVerifyCode(@NonNull CharSequence email);

        void modifyEmail(@NonNull CharSequence email,
                         @NonNull CharSequence verifyCode);
    }

    interface Model {
        Observable<Response<?>> sendVerifyCode(@NonNull CharSequence email);

        Observable<Response<?>> modifyEmail(@NonNull CharSequence email,
                                            @NonNull CharSequence verifyCode);
    }
}
