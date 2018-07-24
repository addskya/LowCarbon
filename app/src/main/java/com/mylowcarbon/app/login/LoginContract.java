package com.mylowcarbon.app.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void login();

        void onLoginStart();

        void onLoginSuccess(@NonNull UserInfo userInfo);

        /**
         * Login failed,
         *
         * @param responseCode the responseCode
         * @param error        the UserInfo errorBean
         */
        void onLoginFail(int responseCode,
                         @Nullable UserInfo error);

        void onLoginError(Throwable error);

        void onLoginCompleted();

        void gotoRegister();

        void gotoForgetPassword();

        void pickRegion();
    }

    interface Presenter extends BasePresenter {

        void login(@NonNull CharSequence mobile,
                   @NonNull CharSequence password,
                   @NonNull DeviceParameters deviceParams);

    }

    interface Model {
        Observable<Response<UserInfo>> login(@NonNull CharSequence mobile,
                                             @NonNull CharSequence password,
                                             @NonNull DeviceParameters deviceParams);
    }
}
