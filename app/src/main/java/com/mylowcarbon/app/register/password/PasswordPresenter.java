package com.mylowcarbon.app.register.password;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.login.DeviceParameters;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class PasswordPresenter implements PasswordContract.Presenter {

    private static final String TAG = "PasswordPresenter";
    private PasswordContract.View mView;
    private PasswordContract.Model mData;

    PasswordPresenter(@NonNull PasswordContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new PasswordModel();
    }

    @Override
    public void commit(@NonNull final CharSequence mobile,
                       @NonNull final CharSequence loginPassword,
                       @NonNull final CharSequence dealPassword,
                       @NonNull CharSequence walletAddress,
                       @NonNull CharSequence keystore,
                       @Nullable final CharSequence recommendCode,
                       @NonNull DeviceParameters deviceParameters) {
        if (TextUtils.isEmpty(loginPassword) ||
                TextUtils.isEmpty(dealPassword) ||
                TextUtils.isEmpty(walletAddress)) {
            return;
        }
        mData.commit(mobile, loginPassword, dealPassword,
                walletAddress, keystore, recommendCode, deviceParameters)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<RegisterInfo>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onCommitStart();
                        }
                    }

                    @Override
                    public void onNext(Response<RegisterInfo> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onCommitSuccess(mobile, loginPassword, response.getValue());
                        } else {
                            mView.onCommitFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onCommitError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onCommitCompleted();
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
