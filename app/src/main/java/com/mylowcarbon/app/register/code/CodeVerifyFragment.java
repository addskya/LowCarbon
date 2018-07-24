package com.mylowcarbon.app.register.code;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.vcedittext.VerificationAction;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentCodeVerifyBinding;
import com.mylowcarbon.app.register.RegisterBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public class CodeVerifyFragment extends RegisterBaseFragment
        implements CodeVerifyContract.View {

    private static final String TAG = "CodeVerifyFragment";

    private static final String EXTRA_SUMMARY =
            "com.mylowcarbon.app.EXTRA_SUMMARY";

    protected static final String EXTRA_REGISTER =
            "com.mylowcarbon.app.EXTRA_REGISTER";

    /**
     * 获取验证码Fragment实例
     *
     * @param mobile 当前使用的手机号码
     * @return 验证码Fragment实例
     */
    public static Fragment getArgsFragment(@NonNull CharSequence mobile) {
        Fragment fragment = new CodeVerifyFragment();
        Bundle args = getArgsBundle(mobile);
        args.putBoolean(EXTRA_REGISTER, true);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取验证码Fragment实例
     *
     * @param mobile  当前使用的手机号码
     * @param summary 显示文字.
     * @return 验证码Fragment实例
     */
    public static Fragment getArgsFragment(@NonNull CharSequence mobile,
                                           @NonNull CharSequence summary,
                                           boolean isRegister) {
        Fragment fragment = new CodeVerifyFragment();
        Bundle args = new Bundle(1);
        args.putCharSequence(EXTRA_MOBILE, mobile);
        args.putCharSequence(EXTRA_SUMMARY, summary);
        args.putBoolean(EXTRA_REGISTER, isRegister);

        fragment.setArguments(args);
        return fragment;
    }

    private FragmentCodeVerifyBinding mBinding;
    private CodeVerifyContract.Presenter mPresenter;

    private CharSequence mPhoneNumber;
    private CharSequence mSummary;     // 显示文字
    private boolean mIsRegister;        // 是否是注册

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");

        Bundle args = getArguments();
        mPhoneNumber = getArgsMobile(args);
        mSummary = args != null ? args.getCharSequence(EXTRA_SUMMARY) : null;
        mIsRegister = args != null && args.getBoolean(EXTRA_REGISTER);

        LogUtil.e(TAG, " mPhoneNumber : " + mPhoneNumber);
        LogUtil.e(TAG, " mSummary : " + mSummary);
        LogUtil.e(TAG, " mIsRegister : " + mIsRegister);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCodeVerifyBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new CodeVerifyPresenter(this);

        String html = getString(R.string.text_verify_code_tips, mPhoneNumber);
        mBinding.tips.setText(Html.fromHtml(html));
        if (!TextUtils.isEmpty(mSummary)) {
            mBinding.tips.setText(Html.fromHtml(String.valueOf(mSummary)));
        }

        mBinding.verifyCode.setOnVerificationCodeChangedListener(mListener);
        mBinding.executePendingBindings();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_input_verify_code);
    }

    private VerificationAction.OnVerificationCodeChangedListener mListener =
            new VerificationAction.OnVerificationCodeChangedListener() {
                @Override
                public void onVerCodeChanged(CharSequence s, int start,
                                             int before, int count) {

                }

                @Override
                public void onInputCompleted(CharSequence s) {
                    mPresenter.verifyCode(mPhoneNumber, s, mIsRegister);
                    mBinding.verifyCode.setText(null);
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
        mListener = null;
    }

    @Override
    public void setPresenter(CodeVerifyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onVerifyCodeStart() {
        LogUtil.i(TAG, "onVerifyCodeStart");
        showLoadingDialog();
    }

    @Override
    public void onVerifyCodeSuccess(@NonNull CharSequence phoneNumber,
                                    @NonNull CharSequence code) {
        LogUtil.i(TAG, "onVerifyCodeSuccess");
        dismissLoadingDialog();
        if (mIsRegister) {
            Bundle args = getArguments();
            translateTo("PasswordFragment", args);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    @Override
    public void onVerifyCodeFail(int errorCode) {
        Log.w(TAG, "onVerifyCodeFail:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onVerifyCodeError(Throwable error) {
        Log.e(TAG, "onVerifyCodeError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onVerifyCodeCompleted() {
        LogUtil.i(TAG, "onVerifyCodeCompleted");
        dismissLoadingDialog();
    }

}
