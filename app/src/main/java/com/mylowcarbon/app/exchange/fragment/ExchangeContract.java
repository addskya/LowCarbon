package com.mylowcarbon.app.exchange.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.Mining;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * 兑换 Lcl
 */
public interface ExchangeContract {

    interface View extends BaseView<Presenter> {

        void exchangeAll();

        void exchange(@Nullable Mining mining);

        String getDeviceCountDesc();

        String getEnergyDesc();

        void onExchangeStart();

        void onExchangeSuccess(@Nullable ExchangeResp resp);

        void onExchangeFail(int errorCode);

        void onExchangeError(Throwable error);

        void onExchangeCompleted();


        void onExchangeAllStart();

        void onExchangeAllSuccess(@Nullable ExchangeResp resp);

        void onExchangeAllFail(int errorCode);

        void onExchangeAllError(Throwable error);

        void onExchangeAllCompleted();
    }

    interface Presenter extends BasePresenter {

        void exchangeAll(@NonNull CharSequence date);

        /**
         * 兑换指定日期,指定设备的LCL
         *
         * @param device 指定设备
         * @param mining 设定设备
         */
        void exchange(@NonNull Device device,
                      @NonNull Mining mining);

    }

    interface Model {

        Observable<Response<ExchangeResp>> exchangeAll(
                @NonNull CharSequence date);

        Observable<Response<ExchangeResp>> exchange(
                @NonNull Device device,
                @NonNull Mining mining);

    }
}
