package com.mylowcarbon.app.login;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;

import com.mylowcarbon.app.BaseActivity;
import com.mylowcarbon.app.DefaultTextWatcher;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.JsActionBarActivity;
import com.mylowcarbon.app.databinding.ActivityLoginBinding;
import com.mylowcarbon.app.forget.ForgetPasswordActivity;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.register.RegisterActivity;
import com.mylowcarbon.app.utils.LogUtil;
import com.mylowcarbon.app.utils.ToastUtil;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 */

public class LoginActivity extends JsActionBarActivity implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_CODE_REGISTER = 0x50;
    private static final int REQUEST_CODE_FORGET_PASSWORD = 0x51;

    /**
     * 请求登录,请求的Root Activity
     *
     * @param activity    请求的Root Activity
     * @param requestCode request Code
     */
    public static void intentTo(@NonNull Activity activity,
                                int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    private LoginContract.Presenter mPresenter;
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_login);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        mBinding.account.addTextChangedListener(mInputValidWatcher);
        mBinding.passwordView.addTextChangedListener(mInputValidWatcher);

        new LoginPresenter(this);
    }

    @Override
    protected int getUiTitle() {
        return 0;
    }

    private DefaultTextWatcher mInputValidWatcher = new DefaultTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            mBinding.login.setEnabled(checkInputValid());
        }
    };

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_REGISTER: {
                if (resultCode == RESULT_OK) {
                    CharSequence mobile = data.getCharSequenceExtra("mobile");
                    CharSequence password = data.getCharSequenceExtra("password");
                    // 进入登录流程
                    mPresenter.login(mobile, password, getDeviceParams());
                }
                break;
            }

            case REQUEST_CODE_FORGET_PASSWORD: {
                if (resultCode == RESULT_OK) {
                    CharSequence mobile = data.getCharSequenceExtra("mobile");
                    CharSequence password = data.getCharSequenceExtra("password");
                    // 进入登录流程
                    mPresenter.login(mobile, password, getDeviceParams());
                }
                break;
            }
            default: {
                LogUtil.w(TAG, "Unknown requestCode:" + requestCode);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
        mInputValidWatcher = null;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 判断当前用户名密码输入有效性
     *
     * @return true, 如果输入有效
     */
    private boolean checkInputValid() {
        String account = String.valueOf(mBinding.account.getText());
        Resources res = getResources();
        if (TextUtils.getTrimmedLength(account) <
                res.getInteger(R.integer.account_min_length)) {
            // 账号长度不够
            return false;
        }

        String password = String.valueOf(mBinding.passwordView.getText());
        if (TextUtils.getTrimmedLength(password) <
                res.getInteger(R.integer.password_min_length)) {
            // 密度长度不够
            return false;
        }

        return true;
    }

    /**
     * 判断验证码有效性
     *
     * @return true: 验证码有效
     */
    private boolean checkVerifyCode() {
        if (mBinding.verify.getVisibility() == View.VISIBLE) {
            // 验证码可见,检测验证码
            CharSequence verifyCode = mBinding.verifyCode.getText();
            return mBinding.verifyView.verifyCode(verifyCode);
            // 输入的验证码与显示的验证码不一致!
        }
        return true;
    }

    @Override
    public void login() {
        /*
        * 登录流程:
        * 1.账号及密码有效性
        * 2.验证码,验证
        * 正常发起登录请求
        *
        * */

        if (!checkInputValid()) {
            // Invalid account or password
            return;
        }

        if (!checkVerifyCode()) {
            // VerifyCode invalid
            return;
        }

        CharSequence account = mBinding.account.getText();
        CharSequence password = mBinding.passwordView.getText();

        // 进入登录流程
        mPresenter.login(account, password, getDeviceParams());
    }

    /**
     * 获取设备硬件唯一标识,
     * 如果需要修改,请一并修改PasswordFragment#getDeviceId()
     *
     * @return 设备硬件唯一标识
     */
    @NonNull
    private DeviceParameters getDeviceParams() {
        return DeviceParameters.from(getApplicationContext());
    }

    @Override
    public void onLoginStart() {
        LogUtil.i(TAG, "onLoginStart");
        mLoginSuccess = false;
        showLoadingDialog();
    }

    // 标记是否登录成功
    private boolean mLoginSuccess;

    @Override
    public void onLoginSuccess(@NonNull UserInfo userInfo) {
        //登录极光
        JMessageUtil.login(userInfo.getMobile());
        //初始化钱包
        initWallet(userInfo);




    }

    private void initWallet(final UserInfo userInfo){
        if (userInfo == null) {
            return;
        }
        //查找钱包
        findWallet(userInfo.getMobile(),userInfo.getWallet_address(),new ValueCallback<String>(){

            @Override
            public void onReceiveValue(String value) {
                LogUtil.e(TAG, "**********findWallet  value: "+value);
                if (!TextUtils.equals(value,"true")){
                    String keyStore = userInfo.getKeystore();
                    final StringBuilder sb = new StringBuilder(keyStore);
                    if (keyStore.startsWith("\"")) {
                        sb.deleteCharAt(0);

                     }
                    if (keyStore.endsWith("\"")) {
                        sb.deleteCharAt(sb.length() - 1);
                     }
                    importWallet(userInfo.getMobile(),keyStore,userInfo.getPay_pwd(),new ValueCallback<String>(){

                        @Override
                        public void onReceiveValue(String value) {
                            LogUtil.e(TAG, "**********importWallet  value: "+value);
                            if (!TextUtils.equals(value,"null")){
                                LogUtil.i(TAG, "onLoginSuccess:" + userInfo);
                                Intent data = new Intent();
                                data.putExtra("login_user", userInfo);
                                setResult(RESULT_OK, data);
                                mLoginSuccess = true;
                                finish();
                            } else {
                                ToastUtil.showShort(LoginActivity.this,"钱包导入失败");
                            }
                        }
                    });
                } else {
                    LogUtil.i(TAG, "onLoginSuccess:" + userInfo);
                    Intent data = new Intent();
                    data.putExtra("login_user", userInfo);
                    setResult(RESULT_OK, data);
                    mLoginSuccess = true;
                    finish();
                }


            }
        });
    }


    @Override
    public void onLoginFail(int responseCode,
                            @Nullable UserInfo error) {
        LogUtil.i(TAG, "onLoginFail,responseCode:" + responseCode);
        LogUtil.w(TAG, "LoginFail:" + error);
        if (error != null) {
            int errorCount = error.getError_count();
            int maxErrorCount = getResources().getInteger(
                    R.integer.max_login_fail_times);
            if (errorCount >= maxErrorCount) {
                // show the VerifyView
                mBinding.verify.setVisibility(View.VISIBLE);
            }
        }
        showCode(responseCode);
    }

    @Override
    public void onLoginError(Throwable error) {
        LogUtil.e(TAG, "onLoginError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onLoginCompleted() {
        LogUtil.i(TAG, "onLoginCompleted");
        dismissLoadingDialog();
//        if (mLoginSuccess) {
//            finish();
//        }
    }

    @Override
    public void pickRegion() {

    }

    @Override
    public void gotoRegister() {
        // 用户请求注册
        // 要求:在注册成功后,需要把注册时的参数返回,这样方便再做一次本地登录
        RegisterActivity.intentTo(this, REQUEST_CODE_REGISTER);
    }

    @Override
    public void gotoForgetPassword() {
        // 用户请求忘记密码
        // 要求:找回密码后,需要把登录的参数返回,这样方便做一次本地登录
        ForgetPasswordActivity.intentTo(this, REQUEST_CODE_FORGET_PASSWORD);
    }
}
