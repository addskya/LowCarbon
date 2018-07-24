package com.mylowcarbon.app.weather;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 */

class WeatherModel implements WeatherContract.Model {

    private WeatherRequest mRequest;

    WeatherModel() {
        mRequest = new WeatherRequest();
    }

    @Override
    public Observable<Response<Weather>> loadWeather() {
        return mRequest.loadWeather();
    }
}
