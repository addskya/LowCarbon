package com.mylowcarbon.app.trade.sell;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.App;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.JsActionBarActivity;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityOrderconfirmBinding;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptActivity;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.LogUtil;
import com.mylowcarbon.app.utils.AmountUtil;
import com.mylowcarbon.app.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 挂单确认
 */
public class OrderConfirmActivity extends ActionBarActivity implements OrderConfirmContract.View {
    private static final String TAG = "OrderConfirmActivity";
    private OrderConfirmContract.Presenter mPresenter;
    private ActivityOrderconfirmBinding mBinding;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<String> options2Items = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private float number;
    private float price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_orderconfirm);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new OrderConfirmPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_order_confirm;
    }

    public void initView() {
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                options1Items.add("0" + i + ":00");

            } else {
                options1Items.add(i + ":00");
            }
        }
        options2Items.add("至");
        options3Items = options1Items;
    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        price = Float.valueOf(bundle.getString(AppConstants.TRADE_PRICE));
        number = Float.valueOf(bundle.getString(AppConstants.TRADE_NUMBER));

        mBinding.tvNum.setText("" + number);
        mBinding.tvPrice.setText("" + price);
        App application = (App)getApplication();

        mBinding.tvFees.setText(AmountUtil.getFeesToLCL(price,number,application.getRate()));
        UserInfo userInfo = ModelDao.getInstance().getUserInfo();

        if (userInfo == null) {
            return;
        }
        //1：支付宝，2：微信，3：银行卡）
        if (userInfo.getPay_type()==1){
            mBinding.tvReceipt.setText(R.string.txt_pay_type_alipay);
        } else  if (userInfo.getPay_type()==2){
            mBinding.tvReceipt.setText(R.string.txt_pay_type_wechat);
        } else  if (userInfo.getPay_type()==3){
            mBinding.tvReceipt.setText(R.string.txt_pay_type_bank_card);
        }
    }




    @Override
    public void updateReceivingAccount() {
        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);

    }

    @Override
    public void confirm() {
//        Intent intent = new Intent(this, LinkBraceletActivity.class);
//        startActivity(intent); CharSequence ,



        showLoadingDialog();
        String set_today_time = "0,0";
        String set_second_time = "0,0";
        String set_third_time = "0,0";
        String set_fourth_time = "0,0";
        String set_fifth_time = "0,0";
        String set_sixth_time = "0,0";
        String set_seventh_time = "0,0";

        if (!TextUtils.isEmpty(mBinding.tvDay1Start.getText())) {

            set_today_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay2Start.getText())) {
            set_second_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(1) + " " + mBinding.tvDay2Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(1) + " " + mBinding.tvDay2End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay3Start.getText())) {
            set_third_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(2) + " " + mBinding.tvDay3Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(2) + " " + mBinding.tvDay3End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay4Start.getText())) {
            set_fourth_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(3) + " " + mBinding.tvDay4Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(3) + " " + mBinding.tvDay4End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay5Start.getText())) {
            set_fifth_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(4) + " " + mBinding.tvDay5Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(4) + " " + mBinding.tvDay5End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay6Start.getText())) {
            set_sixth_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(5) + " " + mBinding.tvDay6Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(5) + " " + mBinding.tvDay6End.getText());
        }
        if (!TextUtils.isEmpty(mBinding.tvDay7Start.getText())) {
            set_seventh_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(6) + " " + mBinding.tvDay7Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(6) + " " + mBinding.tvDay7End.getText());
        }


        mPresenter.comfirm(number, (int) (price * 100), set_today_time, set_second_time, set_third_time, set_fourth_time, set_fifth_time, set_sixth_time, set_seventh_time);

    }

    @Override
    public void onClickDay(final int day) {
        LogUtil.e(TAG, "onClickDay : " + day);

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (day) {
                    case 1://
                        //
                        mBinding.tvDay1Start.setText(options1Items.get(options1));
                        mBinding.tvDay1End.setText(options3Items.get(options3));

//                        String set_today_time = DateUtil.getStringToDate(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1Start.getText()) + "," + DateUtil.getStringToDate(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1End.getText());
//                        LogUtil.e("test","**************  "+(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1Start.getText()));
//                        LogUtil.e("test","**************  "+(DateUtil.getCalculationDate(0) + " " + mBinding.tvDay1End.getText()));
//                        LogUtil.e("test","************** set_today_time : "+set_today_time);
                        break;
                    case 2://
                        //
                        mBinding.tvDay2Start.setText(options1Items.get(options1));
                        mBinding.tvDay2End.setText(options3Items.get(options3));
                        break;
                    case 3://
                        //
                        mBinding.tvDay3Start.setText(options1Items.get(options1));
                        mBinding.tvDay3End.setText(options3Items.get(options3));
                        break;
                    case 4://
                        //
                        mBinding.tvDay4Start.setText(options1Items.get(options1));
                        mBinding.tvDay4End.setText(options3Items.get(options3));
                        break;
                    case 5://
                        //
                        mBinding.tvDay5Start.setText(options1Items.get(options1));
                        mBinding.tvDay5End.setText(options3Items.get(options3));
                        break;
                    case 6://
                        //
                        mBinding.tvDay6Start.setText(options1Items.get(options1));
                        mBinding.tvDay6End.setText(options3Items.get(options3));
                        break;
                    case 7://
                        //
                        mBinding.tvDay7Start.setText(options1Items.get(options1));
                        mBinding.tvDay7End.setText(options3Items.get(options3));
                        break;


                    default:
                        break;
                }

            }
        })

                .setTitleText("")
                .setSubmitColor(getResources().getColor(R.color.orange3))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.white))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.colorPrimaryDark))//标题背景颜色 Night mode
                .setContentTextSize(20)
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .build();

        pvOptions.setNPicker(options1Items, options2Items, options3Items);//不联动
        pvOptions.show();

    }

    @Override
    public void onConfirmSuc(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(OrderConfirmActivity.this, msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onConfirmFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(OrderConfirmActivity.this, msg);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(OrderConfirmContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
