package com.mylowcarbon.app.register.weight;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class WeightPresenter implements WeightContract.Presenter {

    private WeightContract.View mView;
    private WeightContract.Model mData;

    WeightPresenter(@NonNull WeightContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new WeightModel();
    }

    @Override
    public void modifyWeight(final int weightInKg) {
        mData.modifyWeight(weightInKg)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyWeightStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onModifyWeightSuccess(weightInKg);
                        } else {
                            mView.onModifyWeightFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyWeightError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyWeightCompleted();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
