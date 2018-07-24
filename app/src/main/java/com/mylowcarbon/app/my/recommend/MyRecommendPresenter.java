package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyRecommend;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class MyRecommendPresenter implements MyRecommendContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MyRecommendContract.Model mData;
    private MyRecommendContract.View mView;


    MyRecommendPresenter(@NonNull MyRecommendContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyRecommendModel();
    }

    @Override
    public void getMyRecommend(@NonNull String last_id,  final @NonNull boolean isRefresh) {
        mData.getMyRecommend(last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyRecommend>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyRecommend> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getMyRecommend 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getMyRecommend 失败:  " +response.getCode() + " : "+ response.getMsg());
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
