package com.mylowcarbon.app.my.password.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityEditLoginPwdBinding;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 修改登录密码
 */
public class EditLoginPwdActivity extends ActionBarActivity
        implements EditLoginPwdContract.View {

    private static final String TAG = "EditTransPwdActivity";

    public static void intentTo(@NonNull Context context) {
        Intent intent = new Intent(context, EditLoginPwdActivity.class);
        context.startActivity(intent);
    }

    private EditLoginPwdContract.Presenter mPresenter;
    private ActivityEditLoginPwdBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_edit_login_pwd);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new EditLoginPwdPresenter(this);
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_edit_login_password;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(EditLoginPwdContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void commit() {
        // 提取原始密码
        // 读取新密码
        // 长度限制
        // 正式提交
        CharSequence oldPassword = mBinding.oldPassword.getText();
        if (TextUtils.isEmpty(oldPassword)) {
            // 提示
            toastMessage(R.string.error_empty_password);
            return;
        }

        CharSequence newPassword1 = mBinding.newPassword1.getText();
        CharSequence newPassword2 = mBinding.newPassword2.getText();

        if (!TextUtils.equals(newPassword1, newPassword2)) {
            toastMessage(R.string.error_different_password);
            return;
        }

        if (TextUtils.isEmpty(newPassword1)) {
            toastMessage(R.string.error_empty_password);
            return;
        }

        mPresenter.modifyLoginPassword(oldPassword, newPassword1, newPassword2);
    }

    @Override
    public void onModifyLoginPasswordStart() {
        LogUtil.i(TAG, "onModifyLoginPasswordStart");
        showLoadingDialog();
    }

    @Override
    public void onModifyLoginPasswordSuccess() {
        LogUtil.i(TAG, "onModifyLoginPasswordSuccess");
        setResult(RESULT_OK);
    }

    @Override
    public void onModifyLoginPasswordFail(int errorCode) {
        LogUtil.w(TAG, "onModifyLoginPasswordFail:" + errorCode);
        showCode(errorCode);
    }

    @Override
    public void onModifyLoginPasswordError(Throwable error) {
        LogUtil.e(TAG, "onModifyLoginPasswordError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onModifyLoginPasswordCompleted() {
        dismissLoadingDialog();
    }
}
