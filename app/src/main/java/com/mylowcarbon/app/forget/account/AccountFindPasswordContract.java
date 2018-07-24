package com.mylowcarbon.app.forget.account;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public interface AccountFindPasswordContract {

    interface View extends BaseView<Presenter> {

        /**
         * 通过密保找回入口,
         * 点击跳转到 密保问题界面
         */
        void findPasswordByQuestion();

        /**
         * 发送找回密码验证码
         */
        void sendVerifyCode();

        void onSendVerifyCodeStart();

        void onSendVerifyCodeSuccess(@NonNull CharSequence mobile);

        void onSendVerifyCodeFail(int errorCode);

        void onSendVerifyCodeError(Throwable error);

        void onSendVerifyCodeCompleted();
    }


    interface Presenter extends BasePresenter {

        void sendVerifyCode(@NonNull CharSequence mobile);
    }

    interface Mode {
        Observable<Response<?>> sendVerifyCode(@NonNull CharSequence mobile);
    }
}
