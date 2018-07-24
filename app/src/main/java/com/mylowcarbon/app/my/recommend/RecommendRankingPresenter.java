package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.RecommendRank;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class RecommendRankingPresenter implements RecommendRankingContract.Presenter {
    private static final String TAG = "MainPresenter";
    private RecommendRankingContract.Model mData;
    private RecommendRankingContract.View mView;


    RecommendRankingPresenter(@NonNull RecommendRankingContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new RecommendRankingModel();
    }

    @Override
    public void getRecommendRank(@NonNull String start_time,
                            @NonNull String end_time,
                            @NonNull String last_id,
                            final @NonNull boolean isRefresh) {
        mData.getRecommendRank(start_time,end_time,last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<RecommendRank>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<RecommendRank> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getRecommendRank 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getRecommendRank 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
