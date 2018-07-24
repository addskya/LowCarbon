package com.mylowcarbon.app.register.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mylowcarbon.app.BaseLoadingFragment;
import com.mylowcarbon.app.register.RegisterBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
public abstract class UserFragment extends RegisterBaseFragment
        implements BasicContract.UserView {

    private static final String TAG = "UserFragment";

    private BasicContract.UserPresenter mPresenter;

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new UserPresenter(this);
        mPresenter.loadUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void onLoadUserInfoStart() {
        LogUtil.i(TAG, "");
        showLoadingDialog();
    }

    @Override
    public void onLoadUserInfoFail() {
        LogUtil.i(TAG, "onLoadUserInfoFail");
    }

    @Override
    public void onLoadUserInfoError(Throwable error) {
        LogUtil.e(TAG, "onLoadUserInfoError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onLoadUserInfoCompleted() {
        LogUtil.i(TAG, "");
        dismissLoadingDialog();
    }
}
