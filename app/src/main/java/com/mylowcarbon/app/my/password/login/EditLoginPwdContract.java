package com.mylowcarbon.app.my.password.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  修改登录密码
 */
public interface EditLoginPwdContract {

    interface View extends BaseView<Presenter> {
        void commit();

        void onModifyLoginPasswordStart();

        void onModifyLoginPasswordSuccess();

        void onModifyLoginPasswordFail(int errorCode);

        void onModifyLoginPasswordError(Throwable error);

        void onModifyLoginPasswordCompleted();
    }

    interface Presenter extends BasePresenter {
        void modifyLoginPassword(@NonNull CharSequence oldPassword,
                                 @NonNull CharSequence newPassword,
                                 @NonNull CharSequence confirmPassword);

    }

    interface Model {
        Observable<Response<?>> modifyLoginPassword(@NonNull CharSequence oldPassword,
                                                    @NonNull CharSequence newPassword,
                                                    @NonNull CharSequence confirmPassword);
    }
}
