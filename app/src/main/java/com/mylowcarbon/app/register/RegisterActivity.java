package com.mylowcarbon.app.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.ValueCallback;

import com.mylowcarbon.app.FragmentTransferActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.register.code.CodeVerifyFragment;
import com.mylowcarbon.app.register.gender.GenderFragment;
import com.mylowcarbon.app.register.height.HeightFragment;
import com.mylowcarbon.app.register.password.PasswordFragment;
import com.mylowcarbon.app.register.phone.PhoneVerifyFragment;

/**
 * Created by Orange on 18-3-15.
 * Email:addskya@163.com
 * 注册主界面
 */

public class RegisterActivity extends FragmentTransferActivity {

    private static final String TAG = "RegisterActivity";

    private static final String TAG_PHONE_VERIFY_FRAGMENT = "PhoneVerifyFragment";
    private static final String TAG_CODE_VERIFY_FRAGMENT = "CodeVerifyFragment";
    private static final String TAG_PASSWORD_FRAGMENT = "PasswordFragment";

    private static final String TAG_GENDER_FRAGMENT = "GenderFragment";
    private static final String TAG_HEIGHT_FRAGMENT = "HeightFragment";
    private static final String TAG_WEIGHT_FRAGMENT = "WeightFragment";

    private static final String EXTRA_MOBILE =
            "com.mylowcarbon.app.EXTRA_MOBILE";

    /**
     * Lunch the RegisterActivity for register new account
     * 启动注册界面,
     * 注册成功后,数据以
     * Intent("data",RegisterInfo)返回
     *
     * @param activity    the host activity
     * @param requestCode the requestCode
     */
    public static void intentTo(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setResult(RESULT_CANCELED);
    }

    @Override
    protected int getUiTitle() {
        return 0;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.content;
    }

    @NonNull
    @Override
    protected String getHomeFragmentTag() {
        return TAG_PHONE_VERIFY_FRAGMENT;
    }

    @Override
    protected Fragment createFragment(@NonNull String tag,
                                      @Nullable Bundle args) {
        switch (tag) {
            case TAG_PHONE_VERIFY_FRAGMENT: {
                return new PhoneVerifyFragment();
            }
            case TAG_CODE_VERIFY_FRAGMENT: {
                CharSequence mobile = getArgsMobile(args);
                return CodeVerifyFragment.getArgsFragment(mobile);
            }
            case TAG_PASSWORD_FRAGMENT: {
                CharSequence mobile = getArgsMobile(args);
                return PasswordFragment.getArgsFragment(mobile);
            }
            case TAG_GENDER_FRAGMENT: {
                return new GenderFragment();
            }
            case TAG_HEIGHT_FRAGMENT: {
                return new HeightFragment();
            }

            default: {
                throw new IllegalArgumentException("Can't create Fragment by tag:" + tag);
            }
        }
    }

    @Nullable
    private CharSequence getArgsMobile(@Nullable Bundle args) {
        return args != null ? args.getCharSequence(EXTRA_MOBILE) : null;
    }

    @Override
    public void createWallet(@NonNull CharSequence walletName,
                             @NonNull CharSequence password,
                             @Nullable ValueCallback<String> callback) {
        super.createWallet(walletName, password, callback);
    }

    @Override
    public void exportWallet(@NonNull CharSequence walletName,
                                @NonNull CharSequence password,
                                @Nullable ValueCallback<String> callback) {
        super.exportWallet(walletName, password, callback);
    }
}
