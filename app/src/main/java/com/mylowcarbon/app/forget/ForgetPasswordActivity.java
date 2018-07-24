package com.mylowcarbon.app.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mylowcarbon.app.FragmentTransferActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.forget.account.AccountFindPasswordFragment;
import com.mylowcarbon.app.forget.code.ForgetCodeVerityFragment;
import com.mylowcarbon.app.forget.password.ResetPasswordFragment;
import com.mylowcarbon.app.forget.question.QuestionFindPasswordFragment;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@q63.com
 */

public class ForgetPasswordActivity extends FragmentTransferActivity {

    private static final String TAG = "ForgetPasswordActivity";

    private static final String TAG_ACCOUNT_FRAGMENT = "AccountFindPasswordFragment";
    private static final String TAG_VERIFY_CODE_FRAGMENT = "ForgetCodeVerityFragment";
    private static final String TAG_QUESTION_FRAGMENT = "QuestionFindPasswordFragment";
    private static final String TAG_PASSWORD_FRAGMENT = "ResetPasswordFragment";

    private static final String EXTRA_MOBILE =
            "com.mylowcarbon.app.EXTRA_MOBILE";

    /**
     * Lunch the RegisterActivity for register new account
     *
     * @param activity    the host activity
     * @param requestCode the requestCode
     */
    public static void intentTo(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setResult(RESULT_CANCELED);
    }

    @Override
    protected int getUiTitle() {
        return 0;
    }

    @Override
    public void setUiTitle(int titleId) {
        super.setUiTitle(titleId);
    }

    @Override
    protected int getContainerViewId() {
        return R.id.content;
    }

    @NonNull
    @Override
    protected String getHomeFragmentTag() {
        return TAG_ACCOUNT_FRAGMENT;
    }

    @Override
    protected Fragment createFragment(@NonNull String tag,
                                      @Nullable Bundle args) {
        switch (tag) {
            case TAG_ACCOUNT_FRAGMENT: {
                return new AccountFindPasswordFragment();
            }
            case TAG_VERIFY_CODE_FRAGMENT: {
                CharSequence mobile = getArgsMobile(args);
                return ForgetCodeVerityFragment.getArgsFragment(mobile);
            }
            case TAG_QUESTION_FRAGMENT: {
                return new QuestionFindPasswordFragment();
            }
            case TAG_PASSWORD_FRAGMENT: {
                CharSequence mobile = getArgsMobile(args);
                return ResetPasswordFragment.getArgsFragment(mobile);
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
}
