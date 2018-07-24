package com.mylowcarbon.app.forget.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.DefaultTextWatcher;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentAccountFindPasswordBinding;
import com.mylowcarbon.app.forget.ForgetPasswordBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * 通过账号/手机号找回密码界面
 */

public class AccountFindPasswordFragment extends ForgetPasswordBaseFragment
        implements AccountFindPasswordContract.View {


    private static final String TAG = "AccountFindPasswordFragment";

    private FragmentAccountFindPasswordBinding mBinding;
    private AccountFindPasswordContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAccountFindPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new AccountFindPasswordPresenter(this);
        mBinding.account.addTextChangedListener(mTextWatcher);
        mBinding.setView(this);
        mBinding.executePendingBindings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
        mTextWatcher = null;
    }

    private DefaultTextWatcher mTextWatcher = new DefaultTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            verifyInputPhoneNumber(s);
        }
    };

    public void verifyInputPhoneNumber(Editable input) {
        mBinding.next.setEnabled(TextUtils.getTrimmedLength(input) >= 11);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_find_password);
    }

    @Override
    public void setPresenter(AccountFindPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void findPasswordByQuestion() {
        translateTo("QuestionFindPasswordFragment", null);
    }

    @Override
    public void sendVerifyCode() {
        CharSequence mobile = mBinding.account.getText();
        if (TextUtils.isEmpty(mobile)) {
            return;
        }

        mPresenter.sendVerifyCode(mobile);
    }


    @Override
    public void onSendVerifyCodeStart() {
        showLoadingDialog();
    }

    @Override
    public void onSendVerifyCodeSuccess(@NonNull CharSequence mobile) {
        //goto next
        Bundle args = new Bundle(1);
        args.putCharSequence(EXTRA_MOBILE, mobile);
        translateTo("ForgetCodeVerityFragment", args);
    }

    @Override
    public void onSendVerifyCodeFail(int errorCode) {
        showErrorCode(errorCode);
    }

    @Override
    public void onSendVerifyCodeError(Throwable error) {
        dismissLoadingDialog();
    }

    @Override
    public void onSendVerifyCodeCompleted() {
        dismissLoadingDialog();
    }

}
