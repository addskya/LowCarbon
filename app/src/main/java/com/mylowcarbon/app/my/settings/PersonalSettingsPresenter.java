package com.mylowcarbon.app.my.settings;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  用户设置处理层
 */

class PersonalSettingsPresenter implements PersonalSettingsContract.Presenter {
    private static final String TAG = "MainPresenter";
    private PersonalSettingsContract.Model mData;
    private PersonalSettingsContract.View mView;


    PersonalSettingsPresenter(@NonNull PersonalSettingsContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new PersonalSettingsModel();
    }

    @Override
    public void loadUserInfo() {
        mData.getUserInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<UserInfo>(){
                    @Override
                    public void onNext(UserInfo response) {
                        if (mView.isAdded()) {
                            mView.onLoadUserInfoSuccess(response);
                        }
                    }
                });
    }

    @Override
    public void logout() {
        mData.logout()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Object>() {
                    @Override
                    public void onNext(Object response) {
                        if (mView.isAdded()) {
                            mView.onLogoutSuccess();
                        }
                    }
                });
    }

    @Override
    public void modifyAvatar(@NonNull final CharSequence avatar) {
        mData.modifyAvatar(avatar)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>(){
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyAvatarStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onModifyAvatarSuccess(avatar);
                        } else {
                            mView.onModifyAvatarFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyAvatarError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyAvatarCompleted();
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
