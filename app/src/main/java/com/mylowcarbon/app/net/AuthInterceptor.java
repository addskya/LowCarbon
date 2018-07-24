package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.model.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 * 在网络请求头添加用户鉴权的拦截器
 */

class AuthInterceptor extends BaseInterceptor {

    private ModelDao mModelDao;

    AuthInterceptor() {
        mModelDao = ModelDao.getInstance();
    }

    @Override
    void addHeaders(@NonNull Map<String, String> headers) {
        headers.put("Content-Type", "application/json;charset=utf-8");
        String accessToken = getAccessTokenByUserInfo();
        /*if (TextUtils.isEmpty(accessToken)) {
            accessToken = getAccessToken();
        }*/
        headers.put("access-token", accessToken);
    }

    /*private String getAccessToken() {
        List<RegisterInfo> list =
                mModelDao.getDaoSession()
                        .getRegisterInfoDao()
                        .loadAll();
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        return list.get(size - 1).getAccess_token();
    }*/

    private String getAccessTokenByUserInfo() {
        List<UserInfo> list = mModelDao.getDaoSession()
                .getUserInfoDao().loadAll();
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        return list.get(size - 1).getAccess_token();
    }
}
