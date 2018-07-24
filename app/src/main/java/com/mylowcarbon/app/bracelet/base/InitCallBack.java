package com.mylowcarbon.app.bracelet.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.model.BleDevices;
import com.yc.pedometer.info.StepOneDayAllInfo;

/**
 * Created by Orange on 18-4-20.
 * Email:addskya@163.com
 */

public interface InitCallBack {

    void onBleNotEnabled();

    void onNotSupportBle40();

    void onConnectBleDeviceStart(@NonNull BleDevices device);

    void onConnectedBleDevice(@NonNull BleDevices device);

    void onDisconnectBleDevice(@NonNull BleDevices device);

    void onScanBleDeviceStart();

    void onScanBleDeviceStop();

    void onFindBleDevice(@NonNull BleDevices device);

    void onSyncTimeOk(BleDevices device);

    void onStepChanged(@NonNull BleDevices device,
                       @Nullable StepOneDayAllInfo stepOneDayAllInfo);

    void onStepSyncing(BleDevices device);

    void onStepSyncOk(@NonNull BleDevices device);

    void onRateSyncing(BleDevices device);

    void onRateSyncOk(BleDevices device);

    void onBloodPressureSyncing(BleDevices device);

    void onBloodPressureSyncOk(BleDevices device);

    void onSleepSyncing(BleDevices device);

    void onSleepSyncOk(BleDevices device);


    class SimpleInitCallBack implements InitCallBack {
        @Override
        public void onBleNotEnabled() {

        }

        @Override
        public void onNotSupportBle40() {

        }

        @Override
        public void onConnectBleDeviceStart(@NonNull BleDevices device) {

        }

        @Override
        public void onConnectedBleDevice(@NonNull BleDevices device) {

        }

        @Override
        public void onDisconnectBleDevice(@NonNull BleDevices device) {

        }

        @Override
        public void onScanBleDeviceStart() {

        }

        @Override
        public void onScanBleDeviceStop() {

        }

        @Override
        public void onFindBleDevice(@NonNull BleDevices device) {

        }

        @Override
        public void onSyncTimeOk(BleDevices device) {

        }

        @Override
        public void onStepChanged(@NonNull BleDevices device,
                                  @Nullable StepOneDayAllInfo stepOneDayAllInfo) {

        }

        @Override
        public void onStepSyncing(BleDevices device) {

        }

        @Override
        public void onStepSyncOk(@NonNull BleDevices device) {

        }

        @Override
        public void onRateSyncing(BleDevices device) {

        }

        @Override
        public void onRateSyncOk(BleDevices device) {

        }

        @Override
        public void onBloodPressureSyncing(BleDevices device) {

        }

        @Override
        public void onBloodPressureSyncOk(BleDevices device) {

        }

        @Override
        public void onSleepSyncing(BleDevices device) {

        }

        @Override
        public void onSleepSyncOk(BleDevices device) {

        }
    }
}
