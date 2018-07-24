package com.mylowcarbon.app.bracelet.own;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取用户设备列表及数据
 */

class DevicesPresenter implements DevicesContract.Presenter {
    private static final String TAG = "MainPresenter";
    private DevicesContract.Model mData;
    private DevicesContract.View mView;

    DevicesPresenter(@NonNull DevicesContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new DevicesModel();
    }

    @Override
    public void loadDevices(@NonNull CharSequence condition) {
        mData.loadDevices(condition)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<List<Device>>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadDevicesStart();
                        }
                    }

                    @Override
                    public void onNext(Response<List<Device>> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onLoadDevicesSuccess(response.getValue());
                        } else {
                            mView.onLoadDevicesFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadDevicesError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadDevicesCompleted();
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
