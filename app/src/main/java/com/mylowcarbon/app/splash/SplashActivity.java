package com.mylowcarbon.app.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivitySplashBinding;
import com.mylowcarbon.app.home.MainActivity;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 * 启动页
 * 从服务器上获取最新的启动页,
 * 从本地获取缓存的启动图
 */
public class SplashActivity extends ActionBarActivity implements SplashContract.View {
    private static final String TAG = "SplashActivity";
    private static final int DELAY_MILLIS = 3000;
    private static final int MSG_GOTO_MAIN = 0x1;

    private SplashContract.Presenter mPresenter;
    private ActivitySplashBinding mBinding;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GOTO_MAIN: {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new SplashPresenter(this);
        mPresenter.loadLogo();

        mHandler.sendEmptyMessageDelayed(MSG_GOTO_MAIN, DELAY_MILLIS);
    }

    @Override
    protected int getUiTitle() {
        return R.string.app_name;
    }

    @Override
    public void onLoadCacheLogoSuccess(@Nullable List<Logo> list) {
        if (mDisplayNewestLogo) {
            return;
        }
        if (list == null || list.size() <= 0) {
            // 本地已经缓存过的Logo为空
            Logo res = new Logo();
            res.setLogoImageUri("res:///" + R.drawable.bg_splash_1);
            mBinding.setLogo(res);
            mBinding.executePendingBindings();
        } else {
            final int size = list.size();
            Logo cache = list.get(size - 1);
            mBinding.setLogo(cache);
            mBinding.executePendingBindings();
        }
    }

    // 是否已经下载到了最新的Logo图
    private boolean mDisplayNewestLogo;

    @Override
    public void onLoadLogoSuccess(Logo data) {
        mDisplayNewestLogo = true;
        mBinding.setLogo(data);
        mBinding.executePendingBindings();
    }

    @Override
    public void onLoadLogoFail(int errorCode) {
        LogUtil.w(TAG, "onDataFail,errorCode:" + errorCode);
        showCode(errorCode);
    }

    @Override
    public void onLoadLogoError(Throwable error) {
        LogUtil.e(TAG, "onLoadLogoError", error);
        showError(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
        mHandler.removeMessages(MSG_GOTO_MAIN);
        mHandler = null;
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

