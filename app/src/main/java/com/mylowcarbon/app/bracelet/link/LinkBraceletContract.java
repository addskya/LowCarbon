package com.mylowcarbon.app.bracelet.link;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * 连接手环
 */
public interface LinkBraceletContract {

    interface View extends BaseView<Presenter> {

        Context getApplicationContext();

        // 添加手环
        void addBracelet();

        void onLoadBraceletStart();

        void onLoadBraceletSuccess(@Nullable List<BleDevices> devices);

        void onLoadBraceletError(Throwable error);

        void onLoadBraceletCompleted();

        // 连接到指定的手环
        void connectDevice(@Nullable BleDevices device);

        // 蓝牙设备未启用
        void onBleNotEnabled();

        // 当前设备不支持蓝牙4.0,悲剧了.
        void onNotSupportBle40();

        // 开始连接手环
        void onConnectBleDeviceStart(@NonNull BleDevices device);

        // 手环连接成功,静候下一步指示
        void onBleDeviceConnected(@NonNull BleDevices device);

        // 手环连接失败,或与手环断开连接
        void onBleDeviceDisconnected(@NonNull BleDevices device);

        void onBleDeviceReady(@NonNull BleDevices device);

        // 步数
        String getStepText(@Nullable SportInfo sport);

        // 骑行距离
        String getRidingText(@Nullable SportInfo sport);

        // 总能量值
        String getCalorieText(@Nullable SportInfo sport);

        void onSportInfoChanged(@NonNull BleDevices device);

        void onTapBleDevice(@Nullable BleDevices device);

        void onStepSyncing(@NonNull BleDevices device);

        void onStepSyncOk(@NonNull BleDevices device);

        void onRateSyncing(@NonNull BleDevices device);

        void onRateSyncOk(@NonNull BleDevices device);

        void onBloodPressureSyncing(@NonNull BleDevices device);

        void onBloodPressureSyncOk(@NonNull BleDevices device);

        void onSleepSyncing(@NonNull BleDevices device);

        void onSleepSyncOk(@NonNull BleDevices device);
    }

    interface Presenter extends BasePresenter {

        // 加载所有手环设备
        void loadBracelet();

        // 连接指定手环
        void connectDevice(@NonNull BleDevices device);

        // 同步手环数据
        void syncData(@NonNull BleDevices device);

        void onTapBleDevice(@Nullable BleDevices device);

        void uploadSportData(@NonNull BleDevices device);
    }

    interface Model {
        Observable<List<BleDevices>> loadBracelet();

        Observable<Response<?>> uploadSportData(@NonNull BleDevices device);
    }
}
