package com.mylowcarbon.app.exchange;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
public interface ExchangesContract {

    interface View extends BaseView<Presenter> {

        void onLoadDeviceStart();

        void onLoadDeviceSuccess(@Nullable List<Device> devices);

        void onLoadDeviceFail(int errorCode);

        void onLoadDeviceError(Throwable error);

        void onLoadDeviceCompleted();

    }

    interface Presenter extends BasePresenter {

        /**
         * 获取用户设备列表及数据
         */
        void loadDevice();
    }

    interface Model {

        /**
         * 数据汇总条件（1:按日期, 2:按设备）
         * @param condition 数据汇总条件（1:按日期, 2:按设备）
         * @return 数据汇总
         */
        Observable<Response<List<Device>>> getDevices(
                @NonNull CharSequence condition);
    }
}
