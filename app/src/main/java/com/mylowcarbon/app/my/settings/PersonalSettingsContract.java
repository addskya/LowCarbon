package com.mylowcarbon.app.my.settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  用户设置
 */
public interface PersonalSettingsContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);

        void onLoadUserInfoSuccess(@Nullable UserInfo userInfo);

        void onLogoutSuccess();

        void onModifyAvatarStart();

        void onModifyAvatarSuccess(@NonNull CharSequence url);

        void onModifyAvatarFail(int errorCode);

        void onModifyAvatarError(Throwable error);

        void onModifyAvatarCompleted();
    }

    interface Presenter extends BasePresenter {

        void loadUserInfo();

        void logout();

        void modifyAvatar(@NonNull CharSequence avatar);
    }

    interface Model {

        Observable<UserInfo> getUserInfo();

        Observable<?> logout();

        Observable<Response<?>> modifyAvatar(@NonNull CharSequence url);
    }
}
