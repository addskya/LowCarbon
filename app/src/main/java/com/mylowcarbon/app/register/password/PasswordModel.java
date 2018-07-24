package com.mylowcarbon.app.register.password;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.login.DeviceParameters;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class PasswordModel implements PasswordContract.Model {

    private PasswordRequest mRequest;

    PasswordModel() {
        mRequest = new PasswordRequest();
    }

    @Override
    public Observable<Response<RegisterInfo>> commit(
            @NonNull CharSequence mobile,
            @NonNull CharSequence loginPassword,
            @NonNull CharSequence dealPassword,
            @NonNull CharSequence walletAddress,
            @NonNull CharSequence keystore,
            @Nullable CharSequence recommendCode,
            @NonNull DeviceParameters deviceParameters) {
        return mRequest.commit(mobile, loginPassword,
                dealPassword, walletAddress, keystore,
                recommendCode, deviceParameters)
                /*.doOnNext(new Action1<Response<RegisterInfo>>() {
                    @Override
                    public void call(Response<RegisterInfo> response) {
                        if (response.isSuccess()) {
                            RegisterInfo regInfo = response.getValue();
                            // 先清空之前的数据
                            ModelDao.getInstance()
                                    .getDaoSession()
                                    .getRegisterInfoDao()
                                    .deleteAll();

                            // Save the regInfo into DB
                            ModelDao.getInstance()
                                    .getDaoSession()
                                    .getRegisterInfoDao()
                                    .insert(regInfo);
                        }
                    }
                })*/;
    }
}
