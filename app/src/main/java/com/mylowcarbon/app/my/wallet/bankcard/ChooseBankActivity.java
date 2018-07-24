package com.mylowcarbon.app.my.wallet.bankcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityChoosebankBinding;
import com.mylowcarbon.app.net.response.BankType;
import com.mylowcarbon.app.register.code.CodeVerifyActivity;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.utils.ToastUtil;

/**
 * 添加银行卡
 */
public class ChooseBankActivity extends ActionBarActivity implements ChooseBankContract.View {
    private static final String TAG = "ChooseBankActivity";
    private final int REQUEST_CODE_VERIFY_PHONE = 1;

    private ChooseBankContract.Presenter mPresenter;
    private ActivityChoosebankBinding mBinding;
    private String userName;
    private String cardNum;
    private BankType mBankType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choosebank);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new ChooseBankPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_add_bank_info;
    }

    public void initView() {


    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        userName = bundle.getString(AppConstants.CARD_USER_NAME);
        cardNum = bundle.getString(AppConstants.CARD_NUMBER);
        showLoadingDialog();
        mPresenter.queryByBankNum(cardNum);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(ChooseBankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://选择卡类型
                finish();
//                Intent intent0 = new Intent(this, AddBankCardActivity.class);
//                startActivity(intent0);
                break;
            case 1://下一步
                CharSequence phone = mBinding.etCardPhone.getText();
                if (TextUtils.isEmpty(phone)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_card_phone),
                            getString(R.string.text_sure));
                    return;
                }
                //验证手机号
                mPresenter.verifyPhoneNumber(phone);
                break;


            default:
                break;
        }
    }

    @Override
    public void onQuerySuc(BankType data) {
        this.mBankType = data;
        dismissLoadingDialog();
        if (mBankType == null) {
            return;
        }
        mBinding.tvCardType.setText(data.card_type);
    }

    @Override
    public void onQueryFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ChooseBankActivity.this, msg);
//        finish();
    }

    @Override
    public void onAddSuc(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ChooseBankActivity.this, msg);
        setResult(RESULT_OK);
//        finish();
        OkDialog.intentTo(this,
                msg,
                R.drawable.ic_add_success,
                getString(R.string.txt_finish), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

    }

    @Override
    public void onAddFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ChooseBankActivity.this, msg);
    }

    @Override
    public void onVerifySuc(String msg) {
        String summary = String.format(getResources().getString(R.string.format_html_code_1),mBinding.etCardPhone.getText());
        CodeVerifyActivity.intentTo(this,
                mBinding.etCardPhone.getText(),
                summary,
                REQUEST_CODE_VERIFY_PHONE);
    }

    @Override
    public void onVerifyFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ChooseBankActivity.this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理选择日期结果
         */
        if (requestCode == REQUEST_CODE_VERIFY_PHONE && resultCode == RESULT_OK) {

            showLoadingDialog();
            mPresenter.addCard(userName, cardNum, mBankType.card_type, mBinding.etCardPhone.getText());
        }
    }
}
