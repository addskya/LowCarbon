package com.mylowcarbon.app.mine.mining;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

class MiningPresenter implements MiningContract.Presenter {

    private static final String TAG = "MiningPresenter";

    private MiningContract.View mView;
    private MiningContract.Model mData;

    MiningPresenter(@NonNull MiningContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MiningModel();
    }

    @Override
    public void loadTodayMining(@NonNull CharSequence imei,
                                @NonNull Sport data) {
        mData.getTodayMining(imei, data)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Mining>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadTodayMiningStart();
                        }
                    }

                    @Override
                    public void onNext(Response<Mining> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoadTodayMiningSuccess(response.getValue());
                        } else {
                            mView.onLoadTodayMiningFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadTodayMiningError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadTodayMiningCompleted();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mData = null;
        mView = null;
    }
}
