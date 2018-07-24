package com.mylowcarbon.app.forget.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentResetPasswordBinding;
import com.mylowcarbon.app.forget.ForgetPasswordBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public class ResetPasswordFragment extends ForgetPasswordBaseFragment
        implements ResetPasswordContract.View {

    private static final String TAG = "ResetPasswordFragment";

    public static Fragment getArgsFragment(@NonNull CharSequence mobile) {
        Fragment fragment = new ResetPasswordFragment();
        fragment.setArguments(getArgsBundle(mobile));
        return fragment;
    }

    private ResetPasswordContract.Presenter mPresenter;
    private FragmentResetPasswordBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        CharSequence mobile = getArgsMobile(getArguments());
        String summary = getString(R.string.text_set_new_password, mobile);
        mBinding.summary.setText(summary);

        new ResetPasswordPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_set_new_password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(ResetPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResetPasswordStart() {
        LogUtil.i(TAG, "onResetPasswordStart");
        showLoadingDialog();
    }

    @Override
    public void onResetPasswordSuccess(@NonNull CharSequence mobile,
                                       @NonNull CharSequence password) {
        LogUtil.i(TAG, "onResetPasswordSuccess");
        Intent data = new Intent();
        data.putExtra("mobile", mobile);
        data.putExtra("password", password);
        dismissLoadingDialog();
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    @Override
    public void onResetPasswordFail(int errorCode) {
        LogUtil.w(TAG, "onResetPasswordFail,errorCode:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onResetPasswordError(Throwable error) {
        dismissLoadingDialog();
        LogUtil.e(TAG, "onResetPasswordError", error);
    }

    @Override
    public void onResetPasswordCompleted() {
        dismissLoadingDialog();
        LogUtil.i(TAG, "onResetPasswordCompleted");
    }

    @Override
    public void commit() {
        CharSequence mobile = getArgsMobile(getArguments());
        CharSequence password = mBinding.passwordView.getText();
        CharSequence confirmPassword = mBinding.confirmPassword.getText();
        mPresenter.commit(mobile, password, confirmPassword);
    }
}
