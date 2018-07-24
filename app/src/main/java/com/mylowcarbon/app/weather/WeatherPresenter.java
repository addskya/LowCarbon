package com.mylowcarbon.app.weather;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 */

class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View mView;
    private WeatherContract.Model mData;

    WeatherPresenter(@NonNull WeatherContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new WeatherModel();
    }

    @Override
    public void loadWeather() {
        mData.loadWeather()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Weather>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadWeatherStart();
                        }
                    }

                    @Override
                    public void onNext(Response<Weather> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onLoadWeatherSuccess(response.getValue());
                        } else {
                            mView.onLoadWeatherFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO天气接口报错导致应用停止 暂时屏蔽
//                        if (mView.isAdded()) {
//                            mView.onLoadWeatherError(e);
//                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadWeatherCompleted();
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
