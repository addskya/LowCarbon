package com.mylowcarbon.app.register.phone;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 * 验证手机号输入及发送验证码
 */
public interface PhoneVerifyContract {

    interface View extends BaseView<Presenter> {

        void onVerifyPhoneNumberStart();

        void onVerifyPhoneNumberSuccess(@NonNull CharSequence mobile);

        void onVerifyPhoneNumberFail(int responseCode);

        void onVerifyPhoneNumberError(Throwable error);

        void onVerifyPhoneNumberCompleted();

        void pickRegion();

        void verifyPhoneNumber();

        void showAppProtocol();

        void verifyInputPhoneNumber(Editable input);
    }

    interface Presenter extends BasePresenter {

        /**
         * 验证手机号并发送验证码
         *
         * @param mobile 手机号码
         */
        void verifyPhoneNumber(@NonNull CharSequence mobile);

    }

    interface Model {

        Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile);
    }
}
