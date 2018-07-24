package com.mylowcarbon.app.weather;

import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 */

interface WeatherContract {

    interface View extends BaseView<Presenter> {

        void onLoadWeatherStart();

        void onLoadWeatherSuccess(@Nullable Weather weather);

        void onLoadWeatherFail(int errorCode);

        void onLoadWeatherError(Throwable error);

        void onLoadWeatherCompleted();
    }

    interface Presenter extends BasePresenter {

        void loadWeather();
    }

    interface Model {

        Observable<Response<Weather>> loadWeather();

    }
}
