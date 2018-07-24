package com.mylowcarbon.app.my.wallet.bankcard;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityAddbankcardBinding;
import com.mylowcarbon.app.ui.OkDialog;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends ActionBarActivity implements AddBankCardContract.View {
    private static final String TAG = "AddBankCardActivity";
    private final int REQUEST_CODE_CHOOSE_BANK = 1;

    private AddBankCardContract.Presenter mPresenter;
    private ActivityAddbankcardBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_addbankcard);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new AddBankCardPresenter(this);
        initView();
        initData();
    }


    @Override
    protected int getUiTitle() {
        return R.string.title_add_bank;
    }

    public void initView() {


    }

    public void initData() {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(AddBankCardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://下一步
                CharSequence userName = mBinding.etCardUserName.getText();
                CharSequence cardNum = mBinding.etCardNumber.getText();

                if (TextUtils.isEmpty(userName)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_card_user_name),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(cardNum)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_card_number),
                            getString(R.string.text_sure));
                    return;
                }
                Intent intent = new Intent(this, ChooseBankActivity.class);
                intent.putExtra(AppConstants.CARD_USER_NAME,userName.toString());
                intent.putExtra(AppConstants.CARD_NUMBER,cardNum.toString());
                startActivityForResult(intent,REQUEST_CODE_CHOOSE_BANK);
                break;
            case 1://

                break;


            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理选择银行卡类型
         * 添加成功后关闭当前页面
         */
        if (requestCode == REQUEST_CODE_CHOOSE_BANK && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();

        }
    }
}
