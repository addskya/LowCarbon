package com.mylowcarbon.app.bracelet.bind;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.bracelet.base.BraceletManager;
import com.mylowcarbon.app.bracelet.base.InitCallBack;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 绑定手环
 */

class BindBraceletPresenter implements BindBraceletContract.Presenter {
    private static final String TAG = "BindBraceletPresenter";

    private BindBraceletContract.Model mData;
    private BindBraceletContract.View mView;
    private BraceletManager mBraceletManager;

    BindBraceletPresenter(@NonNull BindBraceletContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new BindBraceletModel();

        mBraceletManager = BraceletManager.getInstance();
    }

    @Override
    public void setupBtService() {
        LogUtil.i(TAG, "setupBtService");
        mBraceletManager.setUp(mView.getApplicationContext(), mCallback, new Runnable() {
            @Override
            public void run() {
                startScan();
            }
        });
    }

    @Override
    public void startScan() {
        LogUtil.i(TAG, "startScan");
        mBraceletManager.startBtScan();
    }

    @Override
    public void stopScan() {
        mBraceletManager.stopBtScan();
    }

    @Override
    public void connectBleDevice(@NonNull BleDevices device) {
        mBraceletManager.connectDevice(device);
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
        mCallback = null;
        mBraceletManager.tearDown();
        mBraceletManager = null;
    }

    private InitCallBack mCallback =
            new InitCallBack.SimpleInitCallBack() {
                @Override
                public void onBleNotEnabled() {
                    if (mView.isAdded()) {
                        mView.onBleNotEnabled();
                    }
                }

                @Override
                public void onNotSupportBle40() {
                    if (mView.isAdded()) {
                        mView.onNotSupportBle40();
                    }
                }

                @Override
                public void onConnectBleDeviceStart(@NonNull BleDevices device) {
                    if (mView.isAdded()) {
                        mView.onConnectBleDeviceStart();
                    }
                }

                @Override
                public void onConnectedBleDevice(@NonNull BleDevices device) {
                    // 设备连接成功,
                    // 是否先验证设备是否为手环,而不是其他BT4.0设备,
                    // 验证成功后,应该添加到服务器数据中
                    if (mBraceletManager.isBraceletDevice(device)) {
                        mData.saveConnectedDevice(device);
                        if (mView.isAdded()) {
                            mView.onBleDeviceConnected();
                        }
                    }

                }

                @Override
                public void onDisconnectBleDevice(@NonNull BleDevices device) {
                    if (mView.isAdded()) {
                        mView.onBleDeviceDisconnected();
                    }
                }

                @Override
                public void onScanBleDeviceStart() {
                    if (mView.isAdded()) {
                        mView.onStartScan();
                    }
                }

                @Override
                public void onScanBleDeviceStop() {
                    if (mView.isAdded()) {
                        mView.onStopScan();
                    }
                }

                @Override
                public void onFindBleDevice(@NonNull BleDevices device) {
                    if (mView.isAdded()) {
                        mView.onFindDevice(device);
                    }
                }
            };
}
