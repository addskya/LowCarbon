package com.mylowcarbon.app.my.wallet;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityMywalletBinding;
import com.mylowcarbon.app.my.recommend.PickDateActivity;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptActivity;
import com.mylowcarbon.app.my.wallet.transfer.TransferActivity;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.ui.QRDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的钱包
 */
public class MyWalletActivity extends ActionBarActivity implements MyWalletContract.View {
    private static final String TAG = "MyWalletActivity";
    private final int REQUEST_CODE_PICK_DATE = 1;
    private final int REQUEST_CODE_TRANSFER = 2;

    private MyWalletContract.Presenter mPresenter;
    private ActivityMywalletBinding mBinding;
    private WalletAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private List<MyWallet.TransferItem> mDatas;
    private MyWallet myWalletInfo;
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mywallet);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new MyWalletPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_my_wallet;
    }

    public void initView() {
        mDatas = new ArrayList<MyWallet.TransferItem>();

        mRecyclerView = mBinding.rvContent;
        mAdapter = new WalletAdapter(this, mDatas);
        mAdapter.setView(this);

        mRecyclerView.addItemDecoration(new SpacesItemDecoration(1));


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyWallet(true);

            }

            @Override
            public void onLoading() {
                getMyWallet(false);

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

    public void initData() {
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);
//        getMyWallet(true);

    }

    @Override
    public void getMyWallet(boolean isRefresh) {
        if(TextUtils.isEmpty(startTime)){
            startTime = DateUtil.getMonthFirstDate();
        }
        if(TextUtils.isEmpty(endTime)){
            endTime = DateUtil.getTodayDate();
        }
        Log.e(TAG, "getMyWallet startTime " + startTime +" - endTime " + endTime);
        int lastId = 0 ;
        if (myWalletInfo != null ){
            lastId = myWalletInfo.last_id;
        }
         mPresenter.getMyWallet(""+DateUtil.getString2Date2(startTime+" 00:00:00"), ""+DateUtil.getString2Date2(endTime+" 23:59:59"), ""+lastId ,isRefresh);

    }

    @Override
    public void onDataSuc(MyWallet myWalletInfo ,boolean isRefresh ) {
        Log.e(TAG, "onDataSuc isRefresh " + isRefresh +" - myWalletInfo " + myWalletInfo);

        this.myWalletInfo = myWalletInfo;
        mBinding.tvSurplusAmount.setText("" + myWalletInfo.surplus_amount);
        mBinding.tvAddress.setText(String.format(getResources().getString(R.string.format_wallet_address), myWalletInfo.wallet_address));
        mBinding.tvIncome.setText(String.format(getResources().getString(R.string.format_income), myWalletInfo.income));
        mBinding.tvTransfer.setText(String.format(getResources().getString(R.string.format_transfer), myWalletInfo.transfer));

        if(isRefresh){
            mDatas.clear();;
//            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);

            swipeRefreshLayout.finishRefresh();
         } else {
            swipeRefreshLayout.finishLoadmore();
        }
        mDatas.addAll(myWalletInfo.data);
//        mDatas.add(new MyWallet.TransferItem(3,"","",10,1523275714,1));
//        mDatas.add(new MyWallet.TransferItem(3,"","",10,1523275714,2));
        mAdapter.notifyDataSetChanged();;
        if(myWalletInfo.list_more){
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
        ToastUtil.showShort(MyWalletActivity.this, msg);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(MyWalletContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://复制钱包地址
                if (myWalletInfo == null) {
                    return;
                }
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("text", myWalletInfo.wallet_address);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.showShort(this,R.string.txt_copy_suc);
                break;
            case 1://转账
                Intent intent = new Intent(this, TransferActivity.class);
                startActivityForResult(intent,REQUEST_CODE_TRANSFER);
                break;
            case 2://收款二维码
                 QRDialog.intentTo(this, myWalletInfo.wallet_address,getString(R.string.txt_receipt_qr),getString(R.string.txt_receipt_qr_tips));

                 break;
            case 3://收款账号
                Intent intent3 = new Intent(this, ReceiptActivity.class);
                startActivity(intent3);

                break;
            case 4://选择日期
                Intent intent4 = new Intent(this, PickDateActivity.class);
                startActivityForResult(intent4, REQUEST_CODE_PICK_DATE);
                break;


            default:
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理选择日期结果
         */
        if (requestCode == REQUEST_CODE_PICK_DATE) {

            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                startTime = bundle.getString(AppConstants.START_TIME);
                endTime = bundle.getString(AppConstants.END_TIME);
                mBinding.tvDateRange.setText(startTime+"-"+endTime);

                getMyWallet(true);
            }
        } else if (requestCode == REQUEST_CODE_TRANSFER && resultCode == Activity.RESULT_OK) {
            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);

        }
    }
}
