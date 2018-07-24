package com.mylowcarbon.app.exchange;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
class ExchangesPresenter implements ExchangesContract.Presenter {

    private ExchangesContract.View mView;
    private ExchangesContract.Model mData;

    ExchangesPresenter(@NonNull ExchangesContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ExchangesModel();
    }

    @Override
    public void loadDevice() {
        CharSequence condition = "1";
        mData.getDevices(condition)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<List<Device>>>(){

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadDeviceStart();
                        }
                    }

                    @Override
                    public void onNext(Response<List<Device>> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoadDeviceSuccess(response.getValue());
                        } else {
                            mView.onLoadDeviceFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadDeviceError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadDeviceCompleted();
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
