package com.mylowcarbon.app.bracelet.link;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.bracelet.base.BraceletManager;
import com.mylowcarbon.app.bracelet.base.InitCallBack;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.utils.LogUtil;
import com.yc.pedometer.info.StepOneDayAllInfo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 连接手环处理
 */

class LinkBraceletPresenter implements LinkBraceletContract.Presenter {
    private static final String TAG = "LinkBraceletPresenter";
    private LinkBraceletContract.Model mData;
    private LinkBraceletContract.View mView;

    private BraceletManager mBraceletManager;


    LinkBraceletPresenter(@NonNull LinkBraceletContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new LinkBraceletModel();

        mBraceletManager = BraceletManager.getInstance();
    }

    @Override
    public void loadBracelet() {
        mData.loadBracelet()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<BleDevices>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadBraceletStart();
                        }
                    }

                    @Override
                    public void onNext(List<BleDevices> response) {
                        if (mView.isAdded()) {
                            mView.onLoadBraceletSuccess(response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadBraceletError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadBraceletCompleted();
                        }
                    }
                });
    }

    @Override
    public void connectDevice(@NonNull final BleDevices device) {
        LogUtil.i(TAG, "connectDevice:" + device);
        mBraceletManager.setUp(mView.getApplicationContext(), mCallback, new Runnable() {
            @Override
            public void run() {
                mBraceletManager.connectDevice(device);
            }
        });
    }

    private boolean mSyncStepOk;
    private boolean mSyncRateOk;
    private boolean mSyncBloodPressureOk;
    private boolean mSyncSleepOk;


    private InitCallBack mCallback =
            new InitCallBack.SimpleInitCallBack() {
                @Override
                public void onBleNotEnabled() {
                    LogUtil.i(TAG, "onBleNotEnabled");
                    if (mView.isAdded()) {
                        mView.onBleNotEnabled();
                    }
                }

                @Override
                public void onNotSupportBle40() {
                    LogUtil.e(TAG, "onNotSupportBle40");
                    if (mView.isAdded()) {
                        mView.onNotSupportBle40();
                    }
                }

                @Override
                public void onConnectBleDeviceStart(@NonNull BleDevices device) {
                    LogUtil.i(TAG, "onConnectBleDeviceStart:" + device);
                    if (mView.isAdded()) {
                        mView.onConnectBleDeviceStart(device);
                    }
                }

                @Override
                public void onConnectedBleDevice(@NonNull BleDevices device) {
                    LogUtil.i(TAG, "onConnectedBleDevice:" + device);
                    if (mView.isAdded()) {
                        mView.onBleDeviceConnected(device);
                    }
                }

                @Override
                public void onDisconnectBleDevice(@NonNull BleDevices device) {
                    LogUtil.i(TAG, "onDisconnectBleDevice:" + device);
                    if (mView.isAdded()) {
                        mView.onBleDeviceDisconnected(device);
                    }
                }

                @Override
                public void onSyncTimeOk(BleDevices device) {
                    LogUtil.i(TAG, "onSyncTimeOk");
                    if (mView.isAdded()) {
                        mView.onBleDeviceReady(device);
                    }
                }

                @Override
                public void onStepChanged(@NonNull BleDevices device,
                                          @Nullable StepOneDayAllInfo stepOneDayAllInfo) {
                    LogUtil.i(TAG, "onStepChanged");
                    if (mView.isAdded()) {
                        mView.onSportInfoChanged(device);
                    }
                    readSportData(device);
                }

                @Override
                public void onStepSyncing(BleDevices device) {
                    LogUtil.i(TAG, "onStepSyncing");
                    mSyncStepOk = false;
                    if (mView.isAdded()) {
                        mView.onStepSyncing(device);
                    }
                }

                @Override
                public void onStepSyncOk(@NonNull BleDevices device) {
                    LogUtil.i(TAG, "onStepSyncOk");
                    mSyncStepOk = true;
                    if (mView.isAdded()) {
                        mView.onStepSyncOk(device);
                    }
                    readSportData(device);
                }

                @Override
                public void onRateSyncing(BleDevices device) {
                    LogUtil.i(TAG, "onRateSyncing");
                    mSyncRateOk = false;
                    if (mView.isAdded()) {
                        mView.onRateSyncing(device);
                    }
                }

                @Override
                public void onRateSyncOk(BleDevices device) {
                    LogUtil.i(TAG, "onRateSyncOk");
                    mSyncRateOk = true;
                    if (mView.isAdded()) {
                        mView.onRateSyncOk(device);
                    }
                    readSportData(device);
                }

                @Override
                public void onBloodPressureSyncing(BleDevices device) {
                    LogUtil.i(TAG, "onBloodPressureSyncing");
                    mSyncBloodPressureOk = false;
                    if (mView.isAdded()) {
                        mView.onBloodPressureSyncing(device);
                    }
                }

                @Override
                public void onBloodPressureSyncOk(BleDevices device) {
                    LogUtil.i(TAG, "onBloodPressureSyncOk");
                    mSyncBloodPressureOk = true;
                    if (mView.isAdded()) {
                        mView.onBloodPressureSyncOk(device);
                    }
                    readSportData(device);
                }

                @Override
                public void onSleepSyncing(BleDevices device) {
                    LogUtil.i(TAG, "onSleepSyncing");
                    mSyncSleepOk = false;
                    if (mView.isAdded()) {
                        mView.onSleepSyncing(device);
                    }
                }

                @Override
                public void onSleepSyncOk(BleDevices device) {
                    LogUtil.i(TAG, "onSleepSyncOk");
                    mSyncSleepOk = true;
                    if (mView.isAdded()) {
                        mView.onSleepSyncOk(device);
                    }
                    readSportData(device);
                }
            };

    @Override
    public void syncData(@NonNull BleDevices device) {
        LogUtil.i(TAG, "syncData");
        // 每次同步数据前,此标记置为 0
        mSyncStepOk = false;
        mSyncRateOk = false;
        mSyncBloodPressureOk = false;
        mSyncSleepOk = false;
        mBraceletManager.syncAllData();
    }

    /**
     * 读取前天,昨天,今天的运动数据
     *
     * @param device 手环设备
     */
    private void readSportData(@NonNull BleDevices device) {
        // 先保证所有同步操作已经完成
        if (!(mSyncStepOk && mSyncRateOk && mSyncBloodPressureOk && mSyncSleepOk)) {
            LogUtil.i(TAG, "Can't read Sport data while syncing");
            return;
        }

        Log.i(TAG, "=============== Read Sport Data START ===============");

        // 前天
        {
            final int padding = -2;
            device.setBeforeYesterday(
                    SportInfo.from(
                            device.getAddress(),
                            mBraceletManager.queryStepInfo(padding),
                            mBraceletManager.queryHeartRate(padding),
                            mBraceletManager.queryBloodPressure(padding),
                            mBraceletManager.querySleep(padding),
                            padding
                    )
            );
        }

        // 昨天
        {
            final int padding = -1;
            device.setYesterday(
                    SportInfo.from(
                            device.getAddress(),
                            mBraceletManager.queryStepInfo(padding),
                            mBraceletManager.queryHeartRate(padding),
                            mBraceletManager.queryBloodPressure(padding),
                            mBraceletManager.querySleep(padding),
                            padding
                    )
            );
        }

        // 今天
        {
            final int padding = 0;
            device.setToday(
                    SportInfo.from(
                            device.getAddress(),
                            mBraceletManager.queryStepInfo(padding),
                            mBraceletManager.queryHeartRate(padding),
                            mBraceletManager.queryBloodPressure(padding),
                            mBraceletManager.querySleep(padding),
                            padding
                    )
            );
        }
        Log.i(TAG, "=============== Read Sport Data OVER ===============");
        if (mView.isAdded()) {
            mView.onSportInfoChanged(device);
        }

        uploadSportData(device);
    }

    @Override
    public void onTapBleDevice(@Nullable BleDevices device) {
        mBraceletManager.findBand();
    }

    @Override
    public void uploadSportData(@NonNull BleDevices device) {
        mData.uploadSportData(device)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
        mCallback = null;
        mBraceletManager.tearDown();
        mBraceletManager = null;
    }
}
