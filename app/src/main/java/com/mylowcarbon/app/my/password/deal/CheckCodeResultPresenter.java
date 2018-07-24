package com.mylowcarbon.app.my.password.deal;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * WalkSubFragment的Presenter
 */

class CheckCodeResultPresenter implements CheckCodeResultContract.Presenter {
    private static final String TAG = "MainPresenter";
    private CheckCodeResultContract.View mView;
    private CheckCodeResultContract.Model mData;

    CheckCodeResultPresenter(@NonNull CheckCodeResultContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new CheckCodeResultModel();
    }

    @Override
    public void queryPayPwd() {
        mData.queryPayPwd()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<PayEntry>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onQueryDealCodeStart();
                        }
                    }

                    @Override
                    public void onNext(Response<PayEntry> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onQueryDealCodeSuccess(response.getValue());
                        } else {
                            mView.onQueryDealCodeFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onQueryDealCodeError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onQueryDealCodeCompleted();
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
