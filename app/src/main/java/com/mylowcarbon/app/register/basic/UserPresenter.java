package com.mylowcarbon.app.register.basic;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class UserPresenter implements BasicContract.UserPresenter {

    private BasicContract.UserView mView;
    private BasicContract.UserModel mData;

    UserPresenter(@NonNull BasicContract.UserView view) {
        mView = view;
        mData = new UserModelImpl();
    }

    @Override
    public void loadUserInfo() {
        mData.loadUserInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<UserInfo>() {
                    @Override
                    public void onStart() {
                        if (mView != null) {
                            mView.onLoadUserInfoStart();
                        }
                    }

                    @Override
                    public void onNext(UserInfo response) {
                        if (mView != null) {
                            mView.onLoadUserInfoSuccess(response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.onLoadUserInfoError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView != null) {
                            mView.onLoadUserInfoCompleted();
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
