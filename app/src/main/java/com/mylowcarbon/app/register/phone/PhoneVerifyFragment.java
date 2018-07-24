package com.mylowcarbon.app.register.phone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.DefaultTextWatcher;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentPhoneVerifyBinding;
import com.mylowcarbon.app.register.RegisterBaseFragment;
import com.mylowcarbon.app.ui.ConfirmDialog;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-10.
 * Email:addskya@163.com
 */
public class PhoneVerifyFragment extends RegisterBaseFragment
        implements PhoneVerifyContract.View {

    private static final String TAG = "PhoneVerifyFragment";

    private static final int REQUEST_CODE_PICK_REGION = 0x20;

    private PhoneVerifyContract.Presenter mPresenter;
    private FragmentPhoneVerifyBinding mBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPhoneVerifyBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new PhoneVerifyPresenter(this);
        mBinding.setView(this);
        mBinding.account.addTextChangedListener(mTextWatcher);
        mBinding.executePendingBindings();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_reg);
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

    @Override
    public void setPresenter(PhoneVerifyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onVerifyPhoneNumberStart() {
        showLoadingDialog();
    }

    @Override
    public void onVerifyPhoneNumberSuccess(@NonNull CharSequence mobile) {
        Log.d(TAG, "onVerifyPhoneNumberSuccess:" + mobile);
        dismissLoadingDialog();
        Bundle args = new Bundle(1);
        args.putCharSequence(EXTRA_MOBILE, mobile);
        translateTo("CodeVerifyFragment", args);
    }

    @Override
    public void onVerifyPhoneNumberFail(int responseCode) {
        Log.w(TAG, "onVerifyPhoneNumberFail: responseCode:" + responseCode);
        showErrorCode(responseCode);
    }

    @Override
    public void onVerifyPhoneNumberError(Throwable error) {
        Log.e(TAG, "onVerifyPhoneNumberError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onVerifyPhoneNumberCompleted() {
        LogUtil.i(TAG, "onVerifyPhoneNumberCompleted");
        dismissLoadingDialog();
    }


    @Override
    public void pickRegion() {
        // REQUEST_CODE_PICK_REGION

    }

    @Override
    public void verifyPhoneNumber() {
        CharSequence mobile = mBinding.account.getText();
        mPresenter.verifyPhoneNumber(mobile);
    }

    @Override
    public void showAppProtocol() {
        ConfirmDialog.intentTo(getContext(),
                getString(R.string.title_user_agreement),
                getString(R.string.user_agreement),
                getString(R.string.text_agree),
                getString(R.string.text_do_not_agree),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case BaseDialog.BUTTON_POSITIVE: {

                                break;
                            }
                            case BaseDialog.BUTTON_NEGATIVE: {
                                finish();
                                break;
                            }
                        }
                    }
                });
    }

    @Override
    public void verifyInputPhoneNumber(Editable input) {
        mBinding.register.setEnabled(TextUtils.getTrimmedLength(input) >= 11);
    }
}
