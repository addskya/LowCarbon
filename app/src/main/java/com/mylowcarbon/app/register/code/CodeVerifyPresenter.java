package com.mylowcarbon.app.register.code;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-3-22.
 * Email:addskay@163.com
 */

class CodeVerifyPresenter implements CodeVerifyContract.Presenter {

    private static final String TAG = "CodeVerifyPresenter";
    private CodeVerifyContract.View mView;
    private CodeVerifyContract.Model mData;

    CodeVerifyPresenter(@NonNull CodeVerifyContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new CodeVerifyModel();
    }


    @Override
    public void verifyCode(@NonNull final CharSequence mobile,
                           @NonNull final CharSequence code,
                           @NonNull boolean is_register) {
        if (TextUtils.isEmpty(mobile)
                || TextUtils.isEmpty(code)) {
            return;
        }
        Log.i(TAG, "verifyCode mobile:" + mobile + ",code:" + code);
        mData.verifyCode(mobile, code ,is_register)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onVerifyCodeStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onVerifyCodeSuccess(mobile, code);
                        } else {
                            mView.onVerifyCodeFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onVerifyCodeError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onVerifyCodeCompleted();
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
