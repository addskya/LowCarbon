package com.mylowcarbon.app.my.password.deal;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityCheckCodeResultBinding;
import com.mylowcarbon.app.my.verify.VerifyIdentityActivity;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 查看交易密码
 */
public class CheckCodeResultActivity extends ActionBarActivity
        implements CheckCodeResultContract.View {

    private static final String TAG = "BindEmailActivity";
    private static final int REQUEST_CODE_IDENTITY = 0x60;

    public static void intentTo(@NonNull Context context) {
        Intent intent = new Intent(context, CheckCodeResultActivity.class);
        context.startActivity(intent);
    }

    private CheckCodeResultContract.Presenter mPresenter;
    private ActivityCheckCodeResultBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_check_code_result);
        mBinding.executePendingBindings();
        new CheckCodeResultPresenter(this);
        verifyIdentity();
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_transaction_password;
    }

    // 请求验证用户身份
    private void verifyIdentity() {
        VerifyIdentityActivity.intentTo(this, REQUEST_CODE_IDENTITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_IDENTITY: {
                if (resultCode == RESULT_OK) {
                    mPresenter.queryPayPwd();
                } else {
                    finish();
                }
                break;
            }
            default: {
                LogUtil.w(TAG, "Unknown requestCode:" + requestCode);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void onQueryDealCodeStart() {
        showLoadingDialog();
    }

    @Override
    public void onQueryDealCodeSuccess(@NonNull PayEntry entry) {
        mBinding.setPayEntry(entry);
    }

    @Override
    public void onQueryDealCodeFail(int errorCode) {
        showCode(errorCode);
    }

    @Override
    public void onQueryDealCodeError(Throwable error) {
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onQueryDealCodeCompleted() {
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(CheckCodeResultContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
