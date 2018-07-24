package com.mylowcarbon.app.my.email;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改电子邮箱地址
 */

class BindEmailPresenter implements BindEmailContract.Presenter {
    private static final String TAG = "MainPresenter";
    private BindEmailContract.View mView;
    private BindEmailContract.Model mData;


    BindEmailPresenter(@NonNull BindEmailContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new BindEmailModel();
    }

    @Override
    public void sendVerifyCode(@NonNull final CharSequence email) {
        mData.sendVerifyCode(email)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onSendVerifyCodeStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onSendVerifyCodeSuccess(email);
                        } else {
                            mView.onSendVerifyCodeFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onSendVerifyCodeError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onSendVerifyCodeCompleted();
                        }
                    }
                });
    }

    @Override
    public void modifyEmail(@NonNull final CharSequence email,
                            @NonNull CharSequence verifyCode) {
        mData.modifyEmail(email, verifyCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyEmailStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onModifyEmailSuccess(email);
                        } else {
                            mView.onModifyEmailFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyEmailError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyEmailCompleted();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
