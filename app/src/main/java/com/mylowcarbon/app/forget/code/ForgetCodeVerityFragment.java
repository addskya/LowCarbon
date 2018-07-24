package com.mylowcarbon.app.forget.code;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.mylowcarbon.app.register.code.CodeVerifyFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 */

public class ForgetCodeVerityFragment extends CodeVerifyFragment {

    private static final String TAG = "ForgetCodeVerityFragment";

    /**
     * 获取验证码Fragment实例
     *
     * @param mobile 当前使用的手机号码
     * @return 验证码Fragment实例
     */
    public static Fragment getArgsFragment(@NonNull CharSequence mobile) {
        Fragment fragment = new ForgetCodeVerityFragment();
        Bundle args = getArgsBundle(mobile);
        args.putBoolean(EXTRA_REGISTER, false);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onVerifyCodeSuccess(@NonNull CharSequence phoneNumber,
                                    @NonNull CharSequence code) {
        LogUtil.i(TAG, "onVerifyCodeSuccess");
        dismissLoadingDialog();
        Bundle args = getArguments();
        translateTo("ResetPasswordFragment", args);
    }
}
