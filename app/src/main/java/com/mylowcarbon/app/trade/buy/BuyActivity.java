package com.mylowcarbon.app.trade.buy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.App;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityBuyBinding;
import com.mylowcarbon.app.net.response.Buy;
import com.mylowcarbon.app.net.response.OrderDetail;
import com.mylowcarbon.app.trade.order.OrderDetailActivity;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.utils.AmountUtil;
import com.mylowcarbon.app.utils.ToastUtil;

/**
 * 买入
 */
public class BuyActivity extends ActionBarActivity implements BuyContract.View {
    private static final String TAG = "BuyActivity";
    private BuyContract.Presenter mPresenter;
    private ActivityBuyBinding mBinding;
    private int mCoinId;
    private float mRate;
    private Buy mBuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_buy);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new BuyPresenter(this);
        initView();
        initData();
     }

    @Override
    protected int getUiTitle() {
        return R.string.title_buy;
    }

    public void initView() {
         mBinding.etBuyNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    mBinding.tvFees.setText("-");    ;
                    mBinding.tvTotalAmount.setText("-");
                    return;
                }
                // 输入的内容变化的监听
                float num =Float.valueOf(s.toString());
                mBinding.tvFees.setText(AmountUtil.getFeesToLCL(mBuy.data.price,num,mRate));
                mBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(mBuy.data.price,num) );
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });


    }
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        App application = (App)getApplication();
        mRate =  application.getRate();

        mCoinId= bundle.getInt(AppConstants.COIN_ID);
        Log.e(TAG, "initData mCoinId " + mCoinId  );

        showLoadingDialog();
        mPresenter.getDetailData(mCoinId);

    }
    @Override
    public void confirm() {
        CharSequence num = mBinding.etBuyNum.getText();
        if (TextUtils.isEmpty(num)) {
            OkDialog.intentTo(this,
                    getString(R.string.hint_buy_num),
                    getString(R.string.text_sure));
            return;
        }
        float number =Float.valueOf(num.toString());

        if (number>mBuy.data.number) {
            OkDialog.intentTo(this,
                    getString(R.string.hint_num_overflow),
                    getString(R.string.text_sure));
            return;
        }
        showLoadingDialog();
        mPresenter.comfirm(mCoinId,Integer.valueOf(num.toString()));
    }

    @Override
    public void onGetDataSuc(Buy data) {
        dismissLoadingDialog();
        mBuy = data ;
        mBinding.tvRule.setText(Html.fromHtml(mBuy.rule_desc));
         mBinding.tvPayQixian.setText(""+mBuy.pay_qixian);
        if (mBuy.data==null){
            return;
        }
        // GlideUtil.getInstance().into(this,mBuy.data.avatar,mBinding.civHead,R.drawable.ic_header_default);
        mBinding.tvNickName.setText(mBuy.data.nickname);
        mBinding.tvPrice.setText(AmountUtil.priceToRMB(mBuy.data.price));
        mBinding.tvNumber.setText(""+mBuy.data.number);
        mBinding.tvTradeNum.setText(String.format(getResources().getString(R.string.format_trade_num), mBuy.data.trade_num));
        mBinding.tvSuccessTradeNum.setText(String.format(getResources().getString(R.string.format_success_trade_num), mBuy.data.success_trade_num));
        mBinding.tvGoodCommentRate.setText(String.format(getResources().getString(R.string.format_good_comment_rate), mBuy.data.good_comment_num));
        mBinding.tvSuccessTradeRate.setText(String.format(getResources().getString(R.string.format_success_trade_rate), mBuy.data.success_trade_num));
        mBinding.tvReceipt.setText(String.format(getResources().getString(R.string.format_sell_receipt_alipay), mBuy.data.card_number));
        mBinding.tvTradeNum.setText(String.format(getResources().getString(R.string.format_trade_num), mBuy.data.trade_num));
        mBinding.civHead.setImageURI(Uri.parse(mBuy.data.avatar));


    }

    @Override
    public void onGetDataFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(this, msg);

    }

    @Override
    public void onConfirmSuc(OrderDetail data) {
        dismissLoadingDialog();
        setResult(RESULT_OK);
        finish();
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(AppConstants.ORDER_ID,data.order_id);
        intent.putExtra(AppConstants.ORDER_IS_BUYER,true);
        intent.putExtra(AppConstants.ORDER_IS_CREATE,true);
        startActivity(intent);
    }

    @Override
    public void onConfirmFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(this, msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(BuyContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
