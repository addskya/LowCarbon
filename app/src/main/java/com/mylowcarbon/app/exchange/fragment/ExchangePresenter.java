package com.mylowcarbon.app.exchange.fragment;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.Mining;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class ExchangePresenter implements ExchangeContract.Presenter {
    private static final String TAG = "MainPresenter";
    private ExchangeContract.Model mData;
    private ExchangeContract.View mView;


    ExchangePresenter(@NonNull ExchangeContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ExchangeModel();
    }

    @Override
    public void exchangeAll(@NonNull CharSequence date) {
        mData.exchangeAll(date)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<ExchangeResp>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onExchangeAllStart();
                        }
                    }

                    @Override
                    public void onNext(Response<ExchangeResp> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onExchangeAllSuccess(response.getValue());
                        } else {
                            mView.onExchangeAllFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onExchangeAllError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onExchangeAllCompleted();
                        }
                    }
                });
    }

    @Override
    public void exchange(@NonNull Device device,
                         @NonNull Mining mining) {
        mData.exchange(device, mining)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<ExchangeResp>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onExchangeStart();
                        }
                    }

                    @Override
                    public void onNext(Response<ExchangeResp> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onExchangeSuccess(response.getValue());
                        } else {
                            mView.onExchangeFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onExchangeError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onExchangeCompleted();
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
