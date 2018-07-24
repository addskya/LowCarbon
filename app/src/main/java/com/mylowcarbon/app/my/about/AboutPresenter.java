package com.mylowcarbon.app.my.about;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.About;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class AboutPresenter implements AboutContract.Presenter {
    private static final String TAG = "MainPresenter";
    private AboutContract.View mView;
    private AboutContract.Model mData;


    AboutPresenter(@NonNull AboutContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new AboutModel();


     }

    @Override
    public void getAboutData() {
        mData.getAboutData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<About>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<About> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getMyRecommend 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue());

                        } else {
                            Log.e(TAG, "getMyRecommend 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
     }


}
