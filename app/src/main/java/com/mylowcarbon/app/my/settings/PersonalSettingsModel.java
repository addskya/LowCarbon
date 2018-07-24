package com.mylowcarbon.app.my.settings;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * 用户设置数据层
 */
class PersonalSettingsModel implements PersonalSettingsContract.Model {
    @Override
    public Observable<UserInfo> getUserInfo() {
        UserInfo userInfo = ModelDao.getInstance().getUserInfo();
        return Observable.just(userInfo);
    }

    @Override
    public Observable<?> logout() {
        ModelDao.getInstance().logout();
        return Observable.just(true);
    }

    @Override
    public Observable<Response<?>> modifyAvatar(@NonNull final CharSequence url) {
        return new AvatarRequest().modifyAvatar(url)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            ModelDao.getInstance().modifyAvatar(url);
                        }
                    }
                });
    }
}
