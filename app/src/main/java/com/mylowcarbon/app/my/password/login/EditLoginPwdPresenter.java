package com.mylowcarbon.app.my.password.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class EditLoginPwdPresenter implements EditLoginPwdContract.Presenter {
    private static final String TAG = "MainPresenter";
    private EditLoginPwdContract.View mView;
    private EditLoginPwdContract.Model mData;

    EditLoginPwdPresenter(@NonNull EditLoginPwdContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new EditLoginPwdModel();
    }

    @Override
    public void modifyLoginPassword(@NonNull CharSequence oldPassword,
                                    @NonNull CharSequence newPassword,
                                    @NonNull CharSequence confirmPassword) {
        mData.modifyLoginPassword(oldPassword, newPassword, confirmPassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyLoginPasswordStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onModifyLoginPasswordSuccess();
                        } else {
                            mView.onModifyLoginPasswordFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyLoginPasswordError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyLoginPasswordCompleted();
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
