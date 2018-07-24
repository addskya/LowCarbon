package com.mylowcarbon.app.my.recommend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.FragmentRecommendrankingBinding;
import com.mylowcarbon.app.mine.adapter.SpacesItemDecoration;
import com.mylowcarbon.app.net.response.RecommendRank;
import com.mylowcarbon.app.ui.ExchangeDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐排行
 */
public class RecommendRankingFragment extends BaseFragment implements RecommendRankingContract.View {
    private static final String TAG = "RecommendRankingFragmen";

    private final int REQUEST_CODE_PICK_DATE = 1;

    private RecommendRankingContract.Presenter mPresenter;
    private FragmentRecommendrankingBinding mBinding;
    private RecommendRankingAdapter mAdapter;
    private List<RecommendRank.RecommendRankItem> mDatas;
    private ExchangeDialog mExchangeDialog;//兑换框
    private MySwipeRefreshLayout swipeRefreshLayout;
    private String startTime;
    private String endTime;
    private RecommendRank mRecommendRank;

    @Override
    public View initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_recommendranking, null, false);
        mBinding.setView(this);
        mPresenter = new RecommendRankingPresenter(this);
        mDatas = new ArrayList<RecommendRank.RecommendRankItem>();



        mAdapter = new RecommendRankingAdapter(mActivity, mDatas);
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvContent.addItemDecoration(new SpacesItemDecoration(2));
        // 设置Item增加、移除动画
        mBinding.rvContent.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvContent.setAdapter(mAdapter);
        mBinding.rvContent.setNestedScrollingEnabled(false);

        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecommendRank(true);

            }

            @Override
            public void onLoading() {
                getRecommendRank(false);

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
//        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);
        getRecommendRank(true);

    }


    @Override
    public  void getRecommendRank(boolean isRefresh) {
        if(TextUtils.isEmpty(startTime)){
            startTime = DateUtil.getMonthFirstDate();
        }
        if(TextUtils.isEmpty(endTime)){
            endTime = DateUtil.getTodayDate();
        }
        Log.e(TAG, "getRecommendRank startTime " + startTime +" - endTime " + endTime);
        int lastId = 0 ;
        if (mRecommendRank != null ){
            lastId = mRecommendRank.last_id;
        }
        if (isRefresh){
            lastId = 0;
        }
        mPresenter.getRecommendRank(""+DateUtil.getString2Date2(startTime+" 00:00:00"), ""+DateUtil.getString2Date2(endTime+" 23:59:59"), ""+lastId ,isRefresh);

    }

    @Override
    public void onDataSuc(RecommendRank mRecommendRank , boolean isRefresh ) {
        Log.e(TAG, "onDataSuc isRefresh " + isRefresh +" - mRecommendRank " + mRecommendRank);

        this.mRecommendRank = mRecommendRank;
        if (mRecommendRank.current==null){
            mBinding.rlTop.setVisibility(View.GONE);
            swipeRefreshLayout.finishRefresh();
            swipeRefreshLayout.setLoadmoreEnable(false);
            return;
        }
        mBinding.tvName.setText(mRecommendRank.current.nickname);
        mBinding.tvSumLevel.setText(String.format(getResources().getString(R.string.format_sum_level), mRecommendRank.current.sum_level_1, mRecommendRank.current.sum_level_2, mRecommendRank.current.sum_level_3));
        mBinding.tvRank.setText(String.format(getResources().getString(R.string.format_rank), mRecommendRank.current.rank));
        if (TextUtils.isEmpty(mRecommendRank.current.avatar)) {
            mBinding.civHead.setImageResource(R.drawable.ic_header_default);
        }
        if(isRefresh){
            mDatas.clear();
            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);
            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadmore();
        }
        mDatas.addAll(mRecommendRank.data);
//        for (int i = 0 ;i<20;i++){
//            mDatas.add(new RecommendRank.RecommendRankItem(1,2,3,"张三"));
//
//        }
        mAdapter.notifyDataSetChanged();;
        if(mRecommendRank.list_more){
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
    public void setPresenter(RecommendRankingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void exchange() {

    }
    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://日期选择
                Intent intent4 = new Intent(mActivity, PickDateActivity.class);
                startActivityForResult(intent4,REQUEST_CODE_PICK_DATE);
                break;
            case 1://

                break;


            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                getRecommendRank(true);
            }
        }
    }
}

