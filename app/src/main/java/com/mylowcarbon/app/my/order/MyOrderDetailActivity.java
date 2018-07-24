package com.mylowcarbon.app.my.order;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityMyorderdetailBinding;

/**
 * 订单详情
 */
public class MyOrderDetailActivity extends ActionBarActivity implements MyOrderDetailContract.View {
    private static final String TAG = "BindEmailActivity";
    private MyOrderDetailContract.Presenter mPresenter;
    private ActivityMyorderdetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_myorderdetail);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new MyOrderDetailPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_my_order_detail;
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
    public void setPresenter(MyOrderDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://设置
                finish();
                break;
            case 1://

                break;


            default:
                break;
        }
    }
}
