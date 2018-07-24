package com.mylowcarbon.app.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 */

class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginContract.Model mData;
    private LoginContract.View mView;


    LoginPresenter(@NonNull LoginContract.View view) {
        view.setPresenter(this);
        mView = view;

        mData = new LoginModel();
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }

    @Override
    public void login(@NonNull CharSequence mobile,
                      @NonNull CharSequence password,
                      @NonNull DeviceParameters deviceParams) {
        mData.login(mobile, password, deviceParams)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<UserInfo>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoginStart();
                        }
                    }

                    @Override
                    public void onNext(Response<UserInfo> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoginSuccess(response.getValue());
                        } else {
                            mView.onLoginFail(
                                    Response.getResponseCode(response),
                                    response.getValue());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoginError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoginCompleted();
                        }
                    }
                });
    }

}
