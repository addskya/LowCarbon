package com.mylowcarbon.app.my.email;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityBindEmailBinding;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 绑定电子邮箱
 */
public class BindEmailActivity extends ActionBarActivity
        implements BindEmailContract.View {
    private static final String TAG = "BindEmailActivity";
    private BindEmailContract.Presenter mPresenter;
    private ActivityBindEmailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_bind_email);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new BindEmailPresenter(this);
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_bind_email;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(BindEmailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void sendVerifyCode() {
        CharSequence email = mBinding.email.getText();
        // 是否需要先验证邮件地址是否合法呢?
        // 暂时不做,让服务器来搞定.

        if (TextUtils.isEmpty(email)) {
            return;
        }

        mPresenter.sendVerifyCode(email);
    }

    @Override
    public void onSendVerifyCodeStart() {
        LogUtil.i(TAG, "onSendVerifyCodeStart");
        mVerifyEmail = null;
        showLoadingDialog();
    }

    private CharSequence mVerifyEmail;

    @Override
    public void onSendVerifyCodeSuccess(@NonNull CharSequence email) {
        // 目前表示最后一次发送验证码成功的邮箱地址.
        mVerifyEmail = email;
    }

    @Override
    public void onSendVerifyCodeFail(int errorCode) {
        LogUtil.w(TAG, "onSendVerifyCodeFail:" + errorCode);
        showCode(errorCode);
        mVerifyEmail = null;
    }

    @Override
    public void onSendVerifyCodeError(Throwable error) {
        LogUtil.e(TAG, "onSendVerifyCodeError", error);
        showError(error);
        dismissLoadingDialog();
        mVerifyEmail = null;
    }

    @Override
    public void onSendVerifyCodeCompleted() {
        LogUtil.i(TAG, "onSendVerifyCodeCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void commit() {
        // 提交确认修改邮件地址了.

        // 邮件地址取哪个值呢? 文本框的输入,还是最后一次发送验证码成功的地址?
        CharSequence email = mVerifyEmail;

        CharSequence verifyCode = mBinding.verifyCode.getText();
        if (TextUtils.isEmpty(verifyCode)) {
            return;
        }

        if (TextUtils.isEmpty(email)) {
            return;
        }

        mPresenter.modifyEmail(email, verifyCode);
    }

    @Override
    public void onModifyEmailStart() {
        LogUtil.i(TAG, "onModifyEmailStart");
        showLoadingDialog();
    }

    @Override
    public void onModifyEmailSuccess(@NonNull CharSequence email) {
        LogUtil.i(TAG, "onModifyEmailSuccess:" + email);
        setResult(RESULT_OK);
        dismissLoadingDialog();
        finish();
    }

    @Override
    public void onModifyEmailFail(int errorCode) {
        LogUtil.w(TAG, "onModifyEmailFail:" + errorCode);
        showCode(errorCode);
    }

    @Override
    public void onModifyEmailError(Throwable error) {
        LogUtil.e(TAG, "onModifyEmailError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onModifyEmailCompleted() {
        LogUtil.i(TAG, "onModifyEmailCompleted");
        dismissLoadingDialog();
    }
}
