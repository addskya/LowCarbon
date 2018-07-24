package com.mylowcarbon.app.my.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityMyordersBinding;
import com.mylowcarbon.app.exchange.ExchangesActivity;
import com.mylowcarbon.app.mine.adapter.SpacesItemDecoration;
import com.mylowcarbon.app.my.activity.EditDeviceNameActivity;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 */
public class MyOrdersActivity extends ActionBarActivity implements MyOrdersContract.View {
    private static final String TAG = "MyOrdersActivity";
    private MyOrdersContract.Presenter mPresenter;
    private OrderAdapter mAdapter;
    private ActivityMyordersBinding mBinding;
    private RecyclerView mRecyclerView;
    private SHSwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_myorders);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new MyOrdersPresenter(this);
        initView();
        initData();
     }

    @Override
    protected int getUiTitle() {
        return R.string.title_my_order;
    }

    public void initView() {
         List dataList = new ArrayList<Object>();
        dataList.add(new Object());
        dataList.add(new Object());
        dataList.add(new Object());
        dataList.add(new Object());
        dataList.add(new Object());
        mRecyclerView = mBinding.rvContent;

        mAdapter = new OrderAdapter(this, dataList);
        mAdapter.setView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration( 20));

        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishRefresh();
                        Toast.makeText(MyOrdersActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
            }

            @Override
            public void onLoading() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishLoadmore();
                        Toast.makeText(MyOrdersActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
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
                        swipeRefreshLayout.setLoaderViewText("下拉刷新");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText("松开刷新");
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setLoaderViewText("正在刷新");
                        break;
                }
            }
            @Override
            public void onLoadmorePullStateChange(float percent, int state) {

            }
        });
    }
    public void initData() {


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(MyOrdersContract.Presenter presenter) {
        mPresenter = presenter;
    }



    /**
     * 提醒发货
     */
    @Override
    public void remind(int position) {
        Intent intent = new Intent(this, EditDeviceNameActivity.class);
        startActivity(intent);
    }

    /**
     * 查看物流
     */
    @Override
    public void view(int position) {
        Intent intent = new Intent(this, ExchangesActivity.class);
        startActivity(intent);
    }

    /**
     * 确认收货
     */
    @Override
    public void confirm(int position) {
        Intent intent = new Intent(this, ExchangesActivity.class);
        startActivity(intent);
    }
    /**
     * 再次兑换
     */
    @Override
    public void exchange(int position) {
        Intent intent = new Intent(this, ExchangesActivity.class);
        startActivity(intent);
    }
    /**
     * 订单详情
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MyOrderDetailActivity.class);
        startActivity(intent);
    }
}
