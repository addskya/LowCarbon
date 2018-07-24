package com.mylowcarbon.app.bracelet.bind;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.BleDevices;

/**
 * 绑定手环
 */
public interface BindBraceletContract {

    interface View extends BaseView<Presenter> {

        Context getApplicationContext();

        // 蓝牙设备未启用
        void onBleNotEnabled();

        // 当前设备不支持蓝牙4.0,悲剧了.
        void onNotSupportBle40();

        void connectDevice(BleDevices device);

        // 发现新Bt设备
        void onFindDevice(@NonNull BleDevices device);

        // 开始连接Bt设备
        void onConnectBleDeviceStart();

        // 成功连接到指定的BleDevice
        void onBleDeviceConnected();

        // 与BleDevice 连接断开,连接失败
        void onBleDeviceDisconnected();

        void gotoEnableBt();

        void gotoBack();

        void onStartScan();

        void onStopScan();
    }

    interface Presenter extends  BasePresenter {

        void setupBtService();

        void startScan();

        void stopScan();

        void connectBleDevice(@NonNull BleDevices device);
    }

    interface Model {

        void saveConnectedDevice(BleDevices mConnectingDevice);
    }
}
