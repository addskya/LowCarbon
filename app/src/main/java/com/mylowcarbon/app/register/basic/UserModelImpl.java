package com.mylowcarbon.app.register.basic;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class UserModelImpl implements BasicContract.UserModel {

    @Override
    public Observable<UserInfo> loadUserInfo() {
        return ModelDao.getInstance()
                .getDaoSession()
                .getUserInfoDao()
                .queryBuilder()
                .rx()
                .unique();
    }
}
