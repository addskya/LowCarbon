package com.mylowcarbon.app.my.wallet.transfer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Toast;

import com.mylowcarbon.app.App;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.JsActionBarActivity;
import com.mylowcarbon.app.databinding.ActivityTransferBinding;
import com.mylowcarbon.app.javascript.JsCallback;
import com.mylowcarbon.app.login.LoginActivity;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.wallet.scan.ScanningActivity;
import com.mylowcarbon.app.net.response.Wallet;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.utils.LogUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 转账
 */
public class TransferActivity extends JsActionBarActivity implements TransferContract.View {
    private static final String TAG = "TransferActivity";
    private TransferContract.Presenter mPresenter;
    private ActivityTransferBinding mBinding;
    private final int REQUEST_CODE_QR = 1;
    private UserInfo mUserInfo;
    private String mWalletAddress;
    private float mBalance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transfer);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new TransferPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_transfer;
    }

    public void initView() {
        mBinding.etAddress.setText("");

    }

    public void initData() {
        mUserInfo = ModelDao.getInstance().getUserInfo();


    }

    @Override
    protected void onWalletInitCompleted() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //创建钱包
//                createWallet("18680758192","qqqqqq",new ValueCallback<String>(){
//
//                    @Override
//                    public void onReceiveValue(String value) {
//                        LogUtil.e(TAG, "onWalletInitCompleted createWallet value: "+value);
//                        mWalletAddress = value;
//                    }
//                });


//                exchangeToken(mUserInfo.getMobile(),"qqqqqq",mUserInfo.getWallet_address(),99,null);
//                balanceOf(mUserInfo.getWallet_address(),new ValueCallback<String>(){
//
//                    @Override
//                    public void onReceiveValue(String value) {
//                        LogUtil.e(TAG, "**********balanceOf  value: "+value);
//
//                    }
//                });
//                checkUser(mUserInfo.getMobile(),new ValueCallback<String>(){
//
//                    @Override
//                    public void onReceiveValue(String value) {
//                        LogUtil.e(TAG, "**********checkUser  value: "+value);
//
//                    }
//                });


                //获取钱包汇率
                getRate(null);
            }
        });

    }

    private float mRate;

    @Override
    protected JsCallback getJsCallback() {

        return new JsCallback() {

            @Override
            public void onCallback(int message, String error, Object result) {
                LogUtil.e(TAG, "JsCallback onCallback : " + message);

                switch (message) {
                    case JsCallback.MESSAGE_INITCTOKEN:
                        onWalletInitCompleted();
                        break;

                    case JsCallback.MESSAGE_RATE:
                        if (error == null && result != null) {
                            mRate = Float.valueOf(result.toString());
                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_RATE rate: " + mRate);
                            App application = (App) getApplication();
                            application.setRate(mRate);
                        }

                        break;
                    case JsCallback.MESSAGE_TRANSFER:
                        if (error == null && result != null) {

                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_TRANSFER  : " + result.toString());

                        }

                        break;
                    case JsCallback.MESSAGE_BALANCE:
                        if (error == null && result != null) {
                            mBalance = Float.valueOf(result.toString());
                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_BALANCE 余额: " + result);

                            CharSequence amountS = mBinding.etAmount.getText();
                            final float amount = Float.valueOf(amountS.toString());
                            if (mBalance > amount) {

                                transferByFee(mUserInfo.getMobile(), mUserInfo.getPay_pwd(), mUserInfo.getWallet_address(), mWallet.wallet_address, amount, new ValueCallback<String>() {

                                    @Override
                                    public void onReceiveValue(String value) {
                                        LogUtil.e(TAG, "**********transferByFee  value: " + value);
                                        if (TextUtils.equals(value, "true")) {
                                            mPresenter.transfer(mBinding.tvWalletAddress.getText(), mBinding.etAmount.getText(), mBinding.etPwd.getText());

                                        } else {
                                            ToastUtil.showShort(TransferActivity.this, "转账失败");
                                            dismissLoadingDialog();
                                        }


                                    }
                                });


                            } else {
                                ToastUtil.showShort(TransferActivity.this, "余额不足");
                                dismissLoadingDialog();

                            }


                        }

                        break;
                }


            }
        };
    }

    private Wallet mWallet;

    @Override
    public void onQuerySuc(Wallet data) {
        mWallet = data;
        dismissLoadingDialog();
        mBinding.llTransAccount.setVisibility(View.GONE);
        mBinding.llTransDetail.setVisibility(View.VISIBLE);
        mBinding.tvNickName.setText(data.nickname);
        mBinding.tvWalletAddress.setText(data.wallet_address);

    }

    @Override
    public void onQueryFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(TransferActivity.this, msg);

    }

    @Override
    public void onTransferSuc(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(TransferActivity.this, msg);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onTransferFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(TransferActivity.this, msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(TransferContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://扫描二维码
                Intent intent = new Intent(this, ScanningActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR);
                break;
            case 1://下一步
                CharSequence address = mBinding.etAddress.getText();
                if (TextUtils.isEmpty(address)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_wallet_address),
                            getString(R.string.text_sure));
                    return;
                }
                showLoadingDialog();
                mPresenter.queryByWalletAddress(address);

                break;
            case 2://确定
                final CharSequence amount = mBinding.etAmount.getText();
                CharSequence pwd = mBinding.etPwd.getText();
                if (TextUtils.isEmpty(amount)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_transfer_amount),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_transfer_pwd),
                            getString(R.string.text_sure));
                    return;
                }
                if (!TextUtils.equals(pwd, mUserInfo.getPay_pwd())) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_transfer_pwd_erro),
                            getString(R.string.text_sure));
                    return;
                }
                showLoadingDialog();

                //获取钱包余额
                balanceOf(mUserInfo.getWallet_address(), new ValueCallback<String>() {

                    @Override
                    public void onReceiveValue(String value) {
                        LogUtil.e(TAG, "**********balanceOf  value: " + value);

                    }
                });

                break;


            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE_QR) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!TextUtils.isEmpty(result)) {
                        mBinding.etAddress.setText(result);
                        mBinding.etAddress.setSelection(result.length());
                    }

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(TransferActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
