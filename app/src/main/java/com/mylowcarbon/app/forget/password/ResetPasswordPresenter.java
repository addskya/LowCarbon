package com.mylowcarbon.app.forget.password;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class ResetPasswordPresenter implements ResetPasswordContract.Presenter {

    private static final String TAG = "ResetPasswordPresenter";

    private ResetPasswordContract.View mView;
    private ResetPasswordContract.Model mData;

    ResetPasswordPresenter(@NonNull ResetPasswordContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ResetPasswordModel();
    }

    @Override
    public void commit(@NonNull final CharSequence mobile,
                       @NonNull final CharSequence password,
                       @NonNull CharSequence confirmPassword) {
        if (TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)) {
            return;
        }

        mData.commit(mobile, password, confirmPassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onResetPasswordStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onResetPasswordSuccess(mobile, password);
                        } else {
                            mView.onResetPasswordFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onResetPasswordError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onResetPasswordCompleted();
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
