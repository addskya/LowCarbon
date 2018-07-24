package com.mylowcarbon.app.trade.mytrade;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.FragmentMytradeBinding;
import com.mylowcarbon.app.net.response.MyTradeDetail;
import com.mylowcarbon.app.trade.mytrade.childorder.ChildOrderActivity;
import com.mylowcarbon.app.trade.order.OrderDetailActivity;
import com.mylowcarbon.app.ui.ConfirmDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的交易fragment
 */
public class MyTradeFragment extends BaseFragment implements MyTradeFragmentContract.View {
    private static final String TAG = "MyTradeFragment";

    public static final int MYTRADE_TYPE_SELL = 0;
    public static final int MYTRADE_TYPE_BUY = 1;
    public static final int MYTRADE_TYPE_FINISH = 2;
    private MyTradeFragmentContract.Presenter mPresenter;
    private FragmentMytradeBinding mBinding;
    private MytradeAdapter mAdapter;
    private List<MyTradeDetail.MyTradeDetailItem> mDatas;
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private int mType;//0:卖出 1：买入 2：已完成
    private MyTradeDetail myTradeDetail;

    public static MyTradeFragment CreateFragment(int type){
        MyTradeFragment myTradeFragment = new MyTradeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.MYTRADE_TYPE, type);
        myTradeFragment.setArguments(bundle);
        return myTradeFragment;
    }


    @Override
    public View initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_mytrade, null, false);
        mBinding.setView(this);
        mPresenter = new MyTradeFragmentPresenter(this);
         mDatas = new ArrayList<MyTradeDetail.MyTradeDetailItem>();

        Bundle bundle = getArguments();
        mType = bundle.getInt(AppConstants.MYTRADE_TYPE);

        mRecyclerView = mBinding.rvContent;
        mAdapter = new MytradeAdapter(mActivity, mDatas, mType );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        mAdapter.setView(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
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

        return mBinding.getRoot();
    }

    @Override
    public void initData() {
        switch (mType) {
            case MYTRADE_TYPE_SELL:
                mBinding.appBarLayout.tvType.setText(R.string.txt_mytrade_type1);
                mBinding.appBarLayout.tvNum.setText(R.string.txt_num_type1);
                mBinding.appBarLayout.tvOperation.setText(R.string.txt_num_operation1);

                break;
            case MYTRADE_TYPE_BUY:
                mBinding.appBarLayout.tvType.setText(R.string.txt_mytrade_type2);
                mBinding.appBarLayout.tvNum.setText(R.string.txt_num_type2);
                mBinding.appBarLayout.tvOperation.setText(R.string.txt_num_operation1);
                 break;
            case MYTRADE_TYPE_FINISH:
                mBinding.appBarLayout.tvType.setText(R.string.txt_mytrade_type3);
                mBinding.appBarLayout.tvNum.setText(R.string.txt_num_type3);
                mBinding.appBarLayout.tvOperation.setText(R.string.txt_num_operation2);
                 break;

            default:

        }
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);
     }

    private void getData(boolean isRefresh){

        int last_time = 0 ;
        if (myTradeDetail != null ){
            last_time = myTradeDetail.last_time;
        }
        if (isRefresh){
            last_time = 0;
        }
         switch (mType) {
            case MYTRADE_TYPE_SELL:
                mPresenter.getSellData(last_time,isRefresh);
                break;
            case MYTRADE_TYPE_BUY:
                mPresenter.getBuyData(last_time,isRefresh);
                break;
            case MYTRADE_TYPE_FINISH:
                mPresenter.getCompleteData(last_time,isRefresh);
                break;
            default:

        }
    }
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handleMessage msg.what " + msg.what  );

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
    public void initEvent() {

    }
    @Override
    public void onItemClick(int position){
        MyTradeDetail.MyTradeDetailItem item = mDatas.get(position);

        switch (mType) {
            case MyTradeFragment.MYTRADE_TYPE_SELL:

                if(item.type==1){//（1：测单，2：子单列表）

                } else {
                    Intent intent = new Intent(mActivity, ChildOrderActivity.class);
                    intent.putExtra(AppConstants.COIN_ID,item.id);
                    startActivity(intent);
                }


                break;
            case MyTradeFragment.MYTRADE_TYPE_BUY:
                 //订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价）
                Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                intent.putExtra(AppConstants.ORDER_ID,item.id);
                intent.putExtra(AppConstants.ORDER_IS_BUYER,true);

                startActivity(intent);
                break;
            case MyTradeFragment.MYTRADE_TYPE_FINISH:
                Intent intent1 = new Intent(mActivity, OrderDetailActivity.class);
                intent1.putExtra(AppConstants.ORDER_ID,item.id);
                intent1.putExtra(AppConstants.ORDER_IS_BUYER,item.type == 1?true:false);

                startActivity(intent1);
                break;

            default:

        }

    }
    @Override
    public void onItemViewClick(int position){
        MyTradeDetail.MyTradeDetailItem item = mDatas.get(position);

        switch (mType) {
            case MyTradeFragment.MYTRADE_TYPE_SELL:

                if(item.type==1){//（1：测单，2：子单列表）
                    cancelDeal(item.id);
                } else {
                    Intent intent = new Intent(mActivity, ChildOrderActivity.class);
                    intent.putExtra(AppConstants.COIN_ID,item.id);
                    startActivity(intent);
                }


                break;
            case MyTradeFragment.MYTRADE_TYPE_BUY:
                Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                intent.putExtra(AppConstants.ORDER_ID,item.id);
                intent.putExtra(AppConstants.ORDER_IS_BUYER,true);

                startActivity(intent);
                break;
            case MyTradeFragment.MYTRADE_TYPE_FINISH:
                Intent intent1 = new Intent(mActivity, OrderDetailActivity.class);
                intent1.putExtra(AppConstants.ORDER_ID,item.id);
                intent1.putExtra(AppConstants.ORDER_IS_BUYER,item.type == 1?true:false);

                startActivity(intent1);
                break;

            default:

        }

    }

    private void cancelDeal(final int coin_id) {

        ConfirmDialog.intentTo(mActivity, null,
                getString(R.string.text_trade_cancel_tip),
                getString(R.string.text_trade_cancel_continue),
                getString(R.string.text_trade_cancel_sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case BaseDialog.BUTTON_POSITIVE: {
                                //继续交易

                                break;
                            }
                            case BaseDialog.BUTTON_NEGATIVE: {
                                //确定取消
                                showLoadingDialog();
                                mPresenter.cancelOrder(coin_id);
                                break;
                            }
                        }
                    }
                });
    }



    @Override
    public void onDataSuc(MyTradeDetail data, boolean isRefresh) {
        dismissLoadingDialog();
        this.myTradeDetail = data;

        Log.e(TAG, "mType : "+mType+" onDataSuc isRefresh " + isRefresh +" - MyTradeDetail " + myTradeDetail);


        if(isRefresh){
            mDatas.clear();;
            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);

//            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadmore();
        }
        mDatas.addAll(myTradeDetail.data);
        mAdapter.notifyDataSetChanged();;
        if(myTradeDetail.list_more){
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
        ToastUtil.showShort(mActivity,msg);
    }

    @Override
    public void setPresenter(MyTradeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }



    @Override
    public void onCancelOrderSuc(String msg ) {
        mActivity.setResult(Activity.RESULT_OK);
        dismissLoadingDialog();
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);

    }

    @Override
    public void onCancelOrderFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(mActivity, msg);

    }


}

