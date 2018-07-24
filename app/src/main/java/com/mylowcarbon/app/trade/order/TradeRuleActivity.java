package com.mylowcarbon.app.trade.order;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityTraderuleBinding;
//import com.mylowcarbon.app.bracelet.link.LinkBraceletAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 低碳派交易规则
 */
public class TradeRuleActivity extends ActionBarActivity implements TradeRuleContract.View {
    private static final String TAG = "TradeRuleActivity";
    private TradeRuleContract.Presenter mPresenter;
    //private LinkBraceletAdapter mAdapter;
    private ActivityTraderuleBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_traderule);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new TradeRulePresenter(this);
        initView();
        initData();
     }

    @Override
    protected int getUiTitle() {
        return R.string.title_trade_rule;
    }

    public void initView() {
         List dataList = new ArrayList<Object>();
        dataList.add(new Object());
        dataList.add(new Object());
        dataList.add(new Object());
       // mAdapter = new LinkBraceletAdapter(this, dataList);


    }
    public void initData() {


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(TradeRuleContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
