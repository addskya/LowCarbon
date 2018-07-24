package com.mylowcarbon.app.login;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 */
class LoginModel implements LoginContract.Model {

    private LoginRequest mRequest;

    LoginModel() {
        mRequest = new LoginRequest();
    }

    @Override
    public Observable<Response<UserInfo>> login(@NonNull CharSequence mobile,
                                                @NonNull CharSequence password,
                                                @NonNull DeviceParameters deviceParams) {
        return mRequest.login(mobile, password, deviceParams)
                .doOnNext(new Action1<Response<UserInfo>>() {
                    @Override
                    public void call(Response<UserInfo> response) {
                        // 理论只会有一个用户登录,所以,新登录时,删除之前的数据
                        // 登录成功,需要保存 UserInfo到数据库中
                        if (response.isSuccess()) {
                            UserInfo user = response.getValue();
                            ModelDao.getInstance()
                                    .login(user);
                        }
                    }
                });
    }
}
