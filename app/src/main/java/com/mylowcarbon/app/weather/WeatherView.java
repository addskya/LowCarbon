package com.mylowcarbon.app.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.ui.SimpleImageView;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-25.
 * Email:addskya@163.com
 */

public class WeatherView extends RelativeLayout implements WeatherContract.View {

    private static final String TAG = "WeatherView";

    public WeatherView(@NonNull Context context) {
        super(context);
    }

    public WeatherView(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherView(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private SimpleImageView mWeatherIcon;
    private TextView mWeatherTemperature;

    private WeatherContract.Presenter mPresenter;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWeatherIcon = findViewById(R.id.weather_icon);
        mWeatherTemperature = findViewById(R.id.weather_temperature);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtil.i(TAG, "onAttachedToWindow");
        new WeatherPresenter(this);
        mPresenter.loadWeather();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.i(TAG, "onDetachedFromWindow");
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isAdded() {
        return getParent() != null;
    }

    @Override
    public void onLoadWeatherStart() {
        LogUtil.i(TAG, "onLoadWeatherStart");
    }

    @Override
    public void onLoadWeatherSuccess(@Nullable Weather weather) {
        LogUtil.i(TAG, "onLoadWeatherSuccess,weather:" + weather);
        if (weather != null) {
            mWeatherIcon.setImageURI(weather.getIcon());

            String temperature = getResources().getString(R.string.format_temperature, weather.getWendu());
            mWeatherTemperature.setText(temperature);
        }
    }

    @Override
    public void onLoadWeatherFail(int errorCode) {
        LogUtil.w(TAG, "onLoadWeatherFail:" + errorCode);
    }

    @Override
    public void onLoadWeatherError(Throwable error) {
        LogUtil.e(TAG, "onLoadWeatherError", error);
    }

    @Override
    public void onLoadWeatherCompleted() {
        LogUtil.i(TAG, "onLoadWeatherCompleted");
    }
}
