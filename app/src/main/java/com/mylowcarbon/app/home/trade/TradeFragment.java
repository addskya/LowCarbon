package com.mylowcarbon.app.home.trade;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;

import com.mylowcarbon.app.App;
import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.MyBarBrowserActivity;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.FragmentTradeBinding;
import com.mylowcarbon.app.home.MainActivity;
import com.mylowcarbon.app.javascript.JsCallback;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.authentication.AuthenticationActivity;
import com.mylowcarbon.app.my.complaints.ComplaintsActivity;
import com.mylowcarbon.app.net.response.Trade;
import com.mylowcarbon.app.net.response.TradeDetail;
import com.mylowcarbon.app.register.RegisterActivity;
import com.mylowcarbon.app.trade.buy.BuyActivity;
import com.mylowcarbon.app.trade.mytrade.MyTradeActivity;
import com.mylowcarbon.app.trade.sell.OrderConfirmActivity;
import com.mylowcarbon.app.ui.ConfirmDialog;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.LogUtil;
import com.mylowcarbon.app.utils.MyInputFilter;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易中心
 */
public class TradeFragment extends BaseFragment implements TradeContract.View {
    private static final String TAG = "TradeFragment";
    private final int REQUEST_CODE_SELL = 1;
    private final int REQUEST_CODE_MYTRADE = 2;

    private TradeContract.Presenter mPresenter;
    private FragmentTradeBinding mBinding;
    private int score[] = new int[7];
    private RecyclerView mRecyclerView;
    private TradeAdapter mAdapter;
    private List<TradeDetail> dataList;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private Trade mTrade;
    private AppCompatActivity mAppCompatActivity;

    @Override
    public View initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_trade, (ViewGroup)mActivity.findViewById(android.R.id.content), false);
        mBinding.setView(this);
        mAppCompatActivity = (AppCompatActivity) mActivity;

        mAppCompatActivity.setSupportActionBar(mBinding.appBarLayout.toolbar);
        mAppCompatActivity.getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.appBarLayout.toolbar.setActionBarTitle(R.string.title_trade);
        mBinding.appBarLayout.toolbar.setOperateText(R.string.title_my_trade);
        mBinding.appBarLayout.toolbar.setOperateAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MyTradeActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MYTRADE);
            }
        });

        mPresenter = new TradePresenter(this);
        return mBinding.getRoot();
    }
    @Override
    public void initData() {

         mRecyclerView = mBinding.rvContent;


        dataList = new ArrayList<TradeDetail>();
        mAdapter = new TradeAdapter(mActivity, dataList);
        mAdapter.setView(this);
        mRecyclerView .setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvContent.setNestedScrollingEnabled(false);

        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                getTradeData(true);

            }

            @Override
            public void onLoading() {
                getTradeData(false);

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

        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);
        getTradeData(true);
    }

    @Override
    public void initEvent() {
        //限制小数点后保留位数
        mBinding.etNum.setFilters(new InputFilter[]{new MyInputFilter(8) });
        mBinding.etUnit.setFilters(new InputFilter[]{new MyInputFilter(2) });

    }

    @Override
    public void getTradeData(boolean isRefresh) {
        UserInfo userInfo = ModelDao.getInstance().getUserInfo();
        if (userInfo == null) {
            return;
        }
        int lastId = 0 ;
        if (mTrade != null ){
            lastId = mTrade.last_id;
        }
        mPresenter.getTradeData(userInfo.getMobile(),true, lastId ,isRefresh);

    }

    @Override
    public void onDataSuc(Trade data , boolean isRefresh ) {
        Log.e(TAG, "onDataSuc isRefresh " + isRefresh +" - Trade " + data);

        this.mTrade = data;
        mBinding.curveTrend.setData(mTrade.tradeData);
//        mBinding.tvSurplusAmount.setText("" + myWalletInfo.surplus_amount);
        mBinding.tvAvgPrice.setText(String.format(getResources().getString(R.string.format_trade_avg_price), mTrade.todayAvgPrice/100));

        if(isRefresh){
            dataList.clear();;
            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);
//            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadmore();
        }
        dataList.addAll(data.coinList);
        mAdapter.notifyDataSetChanged();;
        if(mTrade.list_more){
            mBinding.swipeRefreshLayout.setLoadmoreEnable(true);
        } else {
            mBinding.swipeRefreshLayout.setLoadmoreEnable(false);
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
    public void onDataFail(String msg) {
        swipeRefreshLayout.finishRefresh();
        swipeRefreshLayout.finishLoadmore();
        ToastUtil.showShort(mActivity, msg);

    }


    @Override
    public void setPresenter(TradeContract.Presenter presenter) {

    }

    @Override
    public void showTradeRule() {
        Intent intent6 = new Intent(mActivity, MyBarBrowserActivity.class);
        intent6.putExtra(AppConstants.URL,AppConstants.URL_TRADE_RULE_DESC);
        startActivity(intent6);
    }

    private UserInfo userInfo;

    @Override
    public void sell() {
        userInfo = ModelDao.getInstance().getUserInfo();
        if (userInfo == null) {
            return;
        }
        ////状态（-1：账号异常，0：未实名认证，1：实名认证审核中，2：已实名认证）
//        if (userInfo.getStatus()!=2){
//            ConfirmDialog.intentTo(getContext(),null,
//                    getString(R.string.text_trade_unauth_tip),
//                    getString(R.string.text_auth),
//                    getString(R.string.text_know),
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//                                case BaseDialog.BUTTON_POSITIVE: {
//                                    //认证
//                                    Intent intent = new Intent(mActivity, AuthenticationActivity.class);
//                                    startActivity(intent);
//                                    break;
//                                }
//                                case BaseDialog.BUTTON_NEGATIVE: {
//
//
//                                    break;
//                                }
//                            }
//                        }
//                    });
//        } else {
             if (mActivity instanceof MainActivity) {
                 final MainActivity host = ((MainActivity) mActivity);
                 //获取钱包余额
                 host.balanceOf(userInfo.getWallet_address(),new ValueCallback<String>(){

                     @Override
                     public void onReceiveValue(String value) {
                         LogUtil.e(TAG, "**********balanceOf  value: "+value);
                         if(!TextUtils.isEmpty(value)){
                             float mBalance = Float.valueOf(value );
                             verify(mBalance);
                         }

                     }
                 });
             }
//         }

    }



    private void verify(float mBalance){
        CharSequence price = mBinding.etUnit.getText();
        CharSequence num = mBinding.etNum.getText();
        if (TextUtils.isEmpty(price)) {
            OkDialog.intentTo(getContext(),
                    getString(R.string.text_trade_pricae_tip),
                    getString(R.string.text_refill));
            return;
        }

        if (TextUtils.isEmpty(num)) {
            OkDialog.intentTo(getContext(),
                    getString(R.string.text_trade_num_tip0),
                    getString(R.string.text_refill));
            return;
        }
        float numFloat = Float.valueOf(num.toString());

        if (numFloat>mBalance||numFloat==0) {
            OkDialog.intentTo(getContext(),
                    getString(R.string.text_trade_num_tip1),
                    getString(R.string.text_refill));
            return;
        }


        Intent intent = new Intent(mActivity, OrderConfirmActivity.class);
        intent.putExtra(AppConstants.TRADE_NUMBER,num.toString());
        intent.putExtra(AppConstants.TRADE_PRICE,price.toString());
        startActivityForResult(intent,REQUEST_CODE_SELL);
     }

    @Override
    public void onItemClick(int position) {
        TradeDetail data = dataList.get(position);
        Intent intent = new Intent(mActivity, BuyActivity.class);
        intent.putExtra(AppConstants.COIN_ID, data.id);
        startActivityForResult(intent,REQUEST_CODE_SELL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *
         */
        if (requestCode == REQUEST_CODE_SELL && resultCode == Activity.RESULT_OK) {
            getTradeData(true);

        } else if  (requestCode == REQUEST_CODE_MYTRADE && resultCode == Activity.RESULT_OK) {
            getTradeData(true);

        }
    }


}
