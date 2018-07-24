package com.mylowcarbon.app.trade.mytrade.childorder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityChildorderBinding;
import com.mylowcarbon.app.net.response.MyChildOrder;
import com.mylowcarbon.app.net.response.OrderDetail;
import com.mylowcarbon.app.trade.order.OrderDetailActivity;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 子订单列表
 */
public class ChildOrderActivity extends ActionBarActivity implements ChildOrderContract.View {
    private static final String TAG = "ChildOrderActivity";
    private ChildOrderContract.Presenter mPresenter;
    private ChildOrderAdapter mAdapter;
    private ActivityChildorderBinding mBinding;
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private List<OrderDetail> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_childorder);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new ChildOrderPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_child_order;
    }

    public void initView() {
        mDatas = new ArrayList<OrderDetail>();


        mRecyclerView = mBinding.rvContent;
        mAdapter = new ChildOrderAdapter(this, mDatas);
        mAdapter.setView(this);


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setLoadmoreEnable(false);

        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {

                getData(true);

            }

            @Override
            public void onLoading() {
                getData(false);

            }

            /**
             * 监听下拉刷新过程中的状态改变
             * @param percent 当前下拉距离的百分比（0-1）
             * @param state 分三种状态{NOT_OVER_TRIGGER_POINT：还未到触发下拉刷新的距离；OVER_TRIGGER_POINT：已经到触发下拉刷新的距离；START：正在下拉刷新}
             */
            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_1));
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_2));
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_3));
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_1));
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_2));
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_3));
                        break;
                }
            }
        });

    }

    private int mCoinId;

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        mCoinId = bundle.getInt(AppConstants.COIN_ID);
        Log.e(TAG, "initData mCoinId : " + mCoinId);
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);

    }

    private void getData(boolean isRefresh) {

        int last_id = 0;
        if (myChildOrder != null) {
            last_id = myChildOrder.last_id;
        }
        if (isRefresh) {
            last_id = 0;
        }
        mPresenter.getListData(mCoinId, last_id, isRefresh);

    }

    private MyChildOrder myChildOrder;

    @Override
    public void onDataSuc(MyChildOrder data, boolean isRefresh) {
        dismissLoadingDialog();
        this.myChildOrder = data;
        mBinding.itemHeader.tvTotalNum.setText(String.format(getResources().getString(R.string.format_total_num), myChildOrder.total_num));
        mBinding.itemHeader.tradeNum.setText(String.format(getResources().getString(R.string.format_trade_num2), myChildOrder.trade_num));
        mBinding.itemHeader.successTradeNum.setText(String.format(getResources().getString(R.string.format_success_trade_num3), myChildOrder.success_trade_num));

        if (isRefresh) {
            mDatas.clear();
             handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);

//            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadmore();
        }
         mDatas.addAll(myChildOrder.data);
        mAdapter.notifyDataSetChanged();
        if (myChildOrder.list_more) {
            mBinding.swipeRefreshLayout.setLoadmoreEnable(true);
        } else {
            mBinding.swipeRefreshLayout.setLoadmoreEnable(false);
        }
    }

    @Override
    public void onDataFail(String msg) {
        dismissLoadingDialog();
        swipeRefreshLayout.finishRefresh();
        swipeRefreshLayout.finishLoadmore();
        ToastUtil.showShort(this, msg);
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handleMessage msg.what " + msg.what);

            boolean handled = false;
            switch (msg.what) {
                case AppConstants.SWIPEREFRESH_REFRESH_FINISH:
                    swipeRefreshLayout.finishRefresh();
                    break;
                case AppConstants.SWIPEREFRESH_LOAD_FINISH:
                    swipeRefreshLayout.finishLoadmore();

                    break;
                case AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH:
                    swipeRefreshLayout.startRefresh();
                    swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_3));
                    break;
            }

            return handled;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onItemClick(int position) {
        OrderDetail item = mDatas.get(position);
        Log.e(TAG, "onItemClick position: " + position);

        //订单状态（订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价））
        if (item.order_status == 0) {

        } else if (item.order_status == 1) {
        } else if (item.order_status == 2 ||item.order_status == 3) {

        }
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(AppConstants.ORDER_ID, item.id);
        intent.putExtra(AppConstants.ORDER_IS_BUYER, false);
        startActivity(intent);

    }

    @Override
    public void setPresenter(ChildOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
