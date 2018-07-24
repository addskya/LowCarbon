package com.mylowcarbon.app.register.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentPasswordBinding;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.login.DeviceParameters;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.register.RegisterActivity;
import com.mylowcarbon.app.register.RegisterBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public class PasswordFragment extends RegisterBaseFragment
        implements PasswordContract.View {

    private static final String TAG = "PasswordFragment";

    public static Fragment getArgsFragment(@NonNull CharSequence mobile) {
        Fragment fragment = new PasswordFragment();
        fragment.setArguments(getArgsBundle(mobile));
        return fragment;
    }

    private FragmentPasswordBinding mBinding;
    private PasswordContract.Presenter mPresenter;
    private CharSequence mPhoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
        mPhoneNumber = getArgsMobile(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new PasswordPresenter(this);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        // Ready for JavaScript
        initWebView();
    }

    private void initWebView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_set_password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(PasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCommitStart() {
        LogUtil.i(TAG, "onCommitStart");
        showLoadingDialog();
    }

    @Override
    public void onCommitSuccess(@NonNull CharSequence mobile,
                                @NonNull CharSequence password,
                                @NonNull RegisterInfo regInfo) {
        //注册极光IM
        JMessageUtil.register(mobile.toString());

        dismissLoadingDialog();
        // 注册成功,
        // 1.设置返回参数
        // 2.finish当前Activity
        Intent data = new Intent();
        data.putExtra("mobile", mobile);
        data.putExtra("password", password);
        data.putExtra("regInfo", regInfo);
        getActivity().setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onCommitFail(int responseCode) {
        Log.w(TAG, "onCommitFail:" + responseCode);
        showErrorCode(responseCode);
    }

    @Override
    public void onCommitError(Throwable error) {
        Log.e(TAG, "onCommitError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onCommitCompleted() {
        LogUtil.i(TAG, "onCommitCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void commit() {
        final CharSequence loginPassword = mBinding.loginPassword.getText();
        final CharSequence dealPassword = mBinding.dealPassword.getText();
        int passwordLength = getResources().getInteger(R.integer.password_min_length);
        if (TextUtils.getTrimmedLength(loginPassword) < passwordLength
                || TextUtils.getTrimmedLength(dealPassword) < passwordLength) {
            // Password to short
            return;
        }

        // 创建钱包

        Activity activity = getActivity();
        // 这个设计相当的烂,但是,暂没有更好的办法
        if (activity instanceof RegisterActivity) {
            final RegisterActivity host = ((RegisterActivity) activity);
            // 钱包名使用 用户手机号
            // 钱包密码使用 交易密码
            final CharSequence walletName = mPhoneNumber;
            final CharSequence password = dealPassword;
            showLoadingDialog();
            host.createWallet(walletName, password, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if (TextUtils.isEmpty(value)) {
                        // Create Wallet Failure.
                        LogUtil.e(TAG, "Create Wallet Failure.");
                        dismissLoadingDialog();
                    } else {
                        // value 是钱包的地址, 这个地址首尾都有引号,需要去除
                        final StringBuilder sb = new StringBuilder(value);
                        if (value.startsWith("\"")) {
                            sb.deleteCharAt(0);
                        }
                        if (value.endsWith("\"")) {
                            sb.deleteCharAt(sb.length() - 1);
                        }

                        // 新的要求,要导出,取到keystore
                        host.exportWallet(walletName, password, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                // value 是账户的keystore
                                if (TextUtils.isEmpty(value)) {
                                    return;
                                }
                                CharSequence keystore = value;
                                CharSequence recommendCode = mBinding.recommendCode.getText();
                                mPresenter.commit(mPhoneNumber, loginPassword,
                                        dealPassword, sb.toString(),keystore,
                                        recommendCode, getDeviceParams());
                            }
                        });
                    }
                }
            });
        }

    }

    /**
     * 获取设备参数信息,
     * 如果需要修改,请一并修改LoginActivity#getDeviceParams()
     *
     * @return 设备参数信息
     */
    @NonNull
    private DeviceParameters getDeviceParams() {
        return DeviceParameters.from(getContext().getApplicationContext());
    }
}
