package com.mylowcarbon.app.my.recommend;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.FragmentMyrecommendBinding;
import com.mylowcarbon.app.mine.adapter.SpacesItemDecoration;
import com.mylowcarbon.app.net.response.MyRecommend;
import com.mylowcarbon.app.ui.QRDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的推荐
 */
public class MyRecommendFragment extends BaseFragment implements MyRecommendContract.View {
    private static final String TAG = "MyRecommendFragment";


    private MyRecommendContract.Presenter mPresenter;
    private FragmentMyrecommendBinding mBinding;
    private RecommendAdapter mAdapter;
    private List<MyRecommend.MyRecommendItem> mDatas;
    private QRDialog mQRDialog;
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private MyRecommend myRecommend;

    @Override
    public View initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_myrecommend, null, false);
        mBinding.setView(this);
        mPresenter = new MyRecommendPresenter(this);
        mDatas = new ArrayList<MyRecommend.MyRecommendItem>();


        mRecyclerView = mBinding.rvContent;
        mAdapter = new RecommendAdapter(mActivity, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
        // 设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setRefreshEnable(true);
        swipeRefreshLayout.setLoadmoreEnable(true);
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyRecommend(true);

            }

            @Override
            public void onLoading() {
                getMyRecommend(false);

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

    public void initData() {
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);
        getMyRecommend(true);

    }


    @Override
    public  void getMyRecommend(boolean isRefresh) {

        int lastId = 0 ;
        if (myRecommend != null ){
            lastId = myRecommend.last_id;
        }
        if (isRefresh){
            lastId = 0;
        }
        Log.e(TAG, "getMyRecommend lastId " + lastId  );

        mPresenter.getMyRecommend(""+lastId ,isRefresh);

    }

    @Override
    public void onDataSuc(MyRecommend myRecommend , boolean isRefresh ) {
        Log.e(TAG, "onDataSuc isRefresh " + isRefresh +" - myRecommend " + myRecommend);

        this.myRecommend = myRecommend;
         mBinding.tvCode.setText(String.format(getResources().getString(R.string.format_invitation_code), myRecommend.invitation_code));
        mBinding.tvRecommendProfit.setText(String.format(getResources().getString(R.string.format_recommend_profit), myRecommend.recommend_profit));
        mBinding.tvRecommendAwardDesc.setText(Html.fromHtml(myRecommend.recommend_award_desc));

        if(isRefresh){
            mDatas.clear();;
//            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);

            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadmore();
        }
        mDatas.addAll(myRecommend.data);
          mAdapter.notifyDataSetChanged();;
        if(myRecommend.list_more){
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
    public void initEvent() {

    }
    @Override
    public void onItemClick(int positton){

    }
    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://复制
                if (myRecommend == null) {
                    return;
                }
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("text", myRecommend.invitation_code);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.showShort(mActivity,R.string.txt_copy_suc);
                break;
            case 1://推荐二维码
                QRDialog.intentTo(mActivity, mBinding.tvCode.getText().toString(),getString(R.string.txt_recommend_qr),getString(R.string.txt_recommend_qr_tips));
                break;


            default:
                break;
        }
    }
    @Override
    public void setPresenter(MyRecommendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void exchange() {

    }


}

