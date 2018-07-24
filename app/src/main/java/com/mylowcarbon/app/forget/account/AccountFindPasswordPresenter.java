package com.mylowcarbon.app.forget.account;

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

class AccountFindPasswordPresenter implements AccountFindPasswordContract.Presenter {

    private static final String TAG = "AccountFindPasswordPresenter";
    private AccountFindPasswordContract.View mView;
    private AccountFindPasswordContract.Mode mData;

    AccountFindPasswordPresenter(@NonNull AccountFindPasswordContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new AccountFindPasswordModel();
    }

    @Override
    public void sendVerifyCode(@NonNull final CharSequence mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }

        mData.sendVerifyCode(mobile)
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
                            mView.onSendVerifyCodeSuccess(mobile);
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
    public void destroy() {
        mView = null;
        mData = null;
    }
}
