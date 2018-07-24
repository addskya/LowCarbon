package com.mylowcarbon.app.forget.password;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public interface ResetPasswordContract {

    interface View extends BaseView<Presenter> {

        void onResetPasswordStart();

        void onResetPasswordSuccess(@NonNull CharSequence mobile,
                                    @NonNull CharSequence password);

        void onResetPasswordFail(int errorCode);

        void onResetPasswordError(Throwable error);

        void onResetPasswordCompleted();

        void commit();
    }


    interface Presenter extends BasePresenter {

        void commit(@NonNull CharSequence mobile,
                    @NonNull CharSequence password,
                    @NonNull CharSequence confirmPassword);
    }

    interface Model {

        Observable<Response<?>> commit(@NonNull CharSequence mobile,
                                       @NonNull CharSequence password,
                                       @NonNull CharSequence confirmPassword);
    }
}
