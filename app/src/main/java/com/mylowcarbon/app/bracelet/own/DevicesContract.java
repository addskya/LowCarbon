package com.mylowcarbon.app.bracelet.own;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * 获取用户设备列表及数据
 */
public interface DevicesContract {

    interface View extends BaseView<Presenter> {
        void exchange();

        void onLoadDevicesStart();

        void onLoadDevicesSuccess(@Nullable List<Device> value);

        void onLoadDevicesFail(int errorCode);

        void onLoadDevicesError(Throwable e);

        void onLoadDevicesCompleted();

        void addBracelet();

        /**
         * 获取指定日期的步数格式化文本
         *
         * @param device  指定设备
         * @param padding 指定日期,相对今天, -2 为前天, -1为昨天, 0为今天
         * @return 步数格式化文本
         */
        String getStepText(@Nullable Device device, int padding);

        /**
         * 获取指定日期的总骑行格式化文本
         *
         * @param device  指定设备
         * @param padding 指定日期,相对今天, -2 为前天, -1为昨天, 0为今天
         * @return 总骑行格式化文本
         */
        String getRidingText(@Nullable Device device, int padding);

        /**
         * 获取指定日期的总能量(卡路里)格式文本
         *
         * @param device  指定设备
         * @param padding 指定日期,相对今天, -2 为前天, -1为昨天, 0为今天
         * @return 总能量(卡路里)格式文本
         */
        String getCalorieText(@Nullable Device device, int padding);
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取用户设备列表及近三日挖矿数据
         *
         * @param condition 数据汇总条件（1:按日期, 2:按设备）
         */
        void loadDevices(@NonNull CharSequence condition);
    }

    interface Model {

        Observable<Response<List<Device>>> loadDevices(
                @NonNull CharSequence condition);

    }
}
