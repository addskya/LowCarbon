package com.mylowcarbon.app.register.phone;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 */
class PhoneVerifyPresenter implements PhoneVerifyContract.Presenter {

    private PhoneVerifyContract.View mView;
    private PhoneVerifyContract.Model mData;

    PhoneVerifyPresenter(@NonNull PhoneVerifyContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new PhoneVerifyModel();
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }

    @Override
    public void verifyPhoneNumber(@NonNull final CharSequence mobile) {
        if (TextUtils.isEmpty(mobile)) {
            // Empty phone number
            return;
        }
        mData.verifyPhoneNumber(mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onVerifyPhoneNumberStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (response.isSuccess()) {
                            mView.onVerifyPhoneNumberSuccess(mobile);
                        } else {
                            mView.onVerifyPhoneNumberFail(
                                    Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onVerifyPhoneNumberError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onVerifyPhoneNumberCompleted();
                        }
                    }
                });
    }

}
