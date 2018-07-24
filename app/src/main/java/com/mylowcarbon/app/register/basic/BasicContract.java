package com.mylowcarbon.app.register.basic;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.model.UserInfo;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 * 用户信息基础协议,用于实现从本地或服务器读取用户信息及修改信息
 */
interface BasicContract {

    interface UserView {

        void onLoadUserInfoStart();

        void onLoadUserInfoSuccess(@NonNull UserInfo userInfo);

        void onLoadUserInfoFail();

        void onLoadUserInfoError(Throwable error);

        void onLoadUserInfoCompleted();
    }

    interface UserPresenter extends BasePresenter {

        void loadUserInfo();
    }

    interface UserModel {

        Observable<UserInfo> loadUserInfo();
    }
}
