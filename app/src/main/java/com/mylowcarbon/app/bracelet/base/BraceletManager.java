package com.mylowcarbon.app.bracelet.base;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.utils.LogUtil;
import com.yc.pedometer.info.BPVOneDayInfo;
import com.yc.pedometer.info.RateOneDayInfo;
import com.yc.pedometer.info.SleepTimeInfo;
import com.yc.pedometer.info.StepInfo;
import com.yc.pedometer.info.StepOneDayAllInfo;
import com.yc.pedometer.sdk.BLEServiceOperate;
import com.yc.pedometer.sdk.BluetoothLeService;
import com.yc.pedometer.sdk.DataProcessing;
import com.yc.pedometer.sdk.DeviceScanInterfacer;
import com.yc.pedometer.sdk.ICallback;
import com.yc.pedometer.sdk.ICallbackStatus;
import com.yc.pedometer.sdk.ServiceStatusCallback;
import com.yc.pedometer.sdk.StepChangeListener;
import com.yc.pedometer.sdk.UTESQLOperate;
import com.yc.pedometer.sdk.WriteCommandToBLE;
import com.yc.pedometer.update.Updates;
import com.yc.pedometer.utils.CalendarUtils;
import com.yc.pedometer.utils.GetFunctionList;

import java.util.List;

/**
 * Created by Orange on 18-4-19.
 * Email:addskya@163.com
 */

public class BraceletManager {

    private static final String TAG = "BraceletManager";
    private static final int MAX_SCANNING_DURATION = 10 * 1000;

    // 保持一个实例,但在不同的Activity中所有完毕后,记得tearDown
    private static final BraceletManager sInstance = new BraceletManager();

    public static BraceletManager getInstance() {
        return sInstance;
    }

    private BLEServiceOperate mBLEServiceOperate;
    private BluetoothLeService mBluetoothLeService;
    private WriteCommandToBLE mWriteCommandToBLE;
    private DataProcessing mDataProcessing;
    private Updates mUpdates;
    private UTESQLOperate mUTESQLOperate;

    private CalendarUtils mCalendarUtils;
    private GetFunctionList mGetFunctionList;

    // 蓝牙是否正在扫描
    private boolean mBtScanning;

    // 蓝牙服务是否准备好
    private boolean mBtReady;

    private Handler mHandler;

    private InitCallBack mCallback;

    /**
     * 初始化蓝牙手环SDK,初始化完毕后的回调
     *
     * @param applicationContext application context
     * @param callBack           初始化状态回调
     */
    public void setUp(@NonNull final Context applicationContext,
                      @Nullable InitCallBack callBack) {
        setUp(applicationContext, callBack, null);
    }

    /**
     * 初始化蓝牙手环SDK,
     *
     * @param applicationContext application context
     * @param callBack           初始化状态回调
     * @param action             初始化完毕后的回调
     */
    public void setUp(@NonNull final Context applicationContext,
                      @Nullable final InitCallBack callBack,
                      @Nullable final Runnable action) {

        if (mBtReady) {
            // 无奈之选
            // 1.先断开已经连接好的设备
            // 2.清理
            // 3.重新初始化
            disConnect();
            tearDown();
            setUp(applicationContext, callBack, action);
        } else {
            mBLEServiceOperate = BLEServiceOperate.getInstance(applicationContext);

            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }

            if (!mBLEServiceOperate.isSupportBle4_0()) {
                if (callBack != null) {
                    callBack.onNotSupportBle40();
                }
                return;
            }

            if (!mBLEServiceOperate.isBleEnabled()) {
                if (callBack != null) {
                    callBack.onBleNotEnabled();
                }
                return;
            }

            mBLEServiceOperate.setServiceStatusCallback(null);
            mBLEServiceOperate.setServiceStatusCallback(new ServiceStatusCallback() {
                @Override
                public void OnServiceStatuslt(int status) {
                    LogUtil.i(TAG, "OnServiceStatuslt:" + status);
                    if (status == ICallbackStatus.BLE_SERVICE_START_OK) {
                        mBtReady = true;
                        mCallback = callBack;
                        if (mBluetoothLeService == null) {
                            mBluetoothLeService = mBLEServiceOperate.getBleService();
                        }

                        mWriteCommandToBLE = WriteCommandToBLE.getInstance(applicationContext);
                        mDataProcessing = DataProcessing.getInstance(applicationContext);
                        mUpdates = Updates.getInstance(applicationContext);
                        mUTESQLOperate = UTESQLOperate.getInstance(applicationContext);
                        if (action != null) {
                            action.run();
                        }
                    }
                }
            });
        }
    }

    /**
     * 清理手环连接
     */
    public void tearDown() {
        if (mBtReady) {
            mBtReady = false;
            mWriteCommandToBLE = null;
            mDataProcessing.setOnStepChangeListener(null);
            mDataProcessing = null;
            mUpdates = null;
            mUTESQLOperate = null;

            mBLEServiceOperate.setDeviceScanListener(null);
            mBLEServiceOperate.setServiceStatusCallback(null);

            mBLEServiceOperate.stopLeScan();
            mBLEServiceOperate.disConnect();
            mBLEServiceOperate.unBindService();
            mBLEServiceOperate = null;

            mBluetoothLeService.setICallback(null);
            mBluetoothLeService = null;

            mCallback = null;
            mHandler = null;
        }
    }

    public void findBand() {
        LogUtil.i(TAG, "find Band");
        mWriteCommandToBLE.findBand(10);
    }

    /**
     * 获取步行运动量
     *
     * @param padding 相对今天的日期. -2 前天, -1 昨天, 0 今天
     * @return 步行运动量
     */
    public StepInfo queryStepInfo(int padding) {
        String date = CalendarUtils.getCalendar(padding);
        return mUTESQLOperate.queryStepInfo(date);
    }

    /**
     * 获取心率值
     *
     * @param padding 相对今天的日期. -2 前天, -1 昨天, 0 今天
     * @return 心率值
     */
    public RateOneDayInfo queryHeartRate(int padding) {
        String date = CalendarUtils.getCalendar(padding);
        return mUTESQLOperate.queryRateOneDayMainInfo(date);
    }

    /**
     * 获取血压值
     *
     * @param padding 相对今天的日期. -2 前天, -1 昨天, 0 今天
     * @return 血压值
     */
    public List<BPVOneDayInfo> queryBloodPressure(int padding) {
        String date = CalendarUtils.getCalendar(padding);
        return mUTESQLOperate.queryBloodPressureOneDayInfo(date);
    }

    /**
     * 获取睡眠数据
     *
     * @param padding 相对今天的日期. -2 前天, -1 昨天, 0 今天
     * @return 睡眠数据
     */
    public SleepTimeInfo querySleep(int padding) {
        String date = CalendarUtils.getCalendar(padding);
        return mUTESQLOperate.querySleepInfo(date);
    }

    /**
     * 开始蓝牙扫描
     */
    public void startBtScan() {
        startBtScan(MAX_SCANNING_DURATION);
    }

    /**
     * 开始蓝牙扫描
     *
     * @param timeout 超时时间,即多少毫秒后停止扫描
     */
    public void startBtScan(int timeout) {
        LogUtil.i(TAG, "onStartScan,timeOut:" + timeout);
        mBtScanning = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopBtScan();
            }
        }, timeout);

        mBLEServiceOperate.setDeviceScanListener(new DeviceScanInterfacer() {
            @Override
            public void LeScanCallback(BluetoothDevice device, int rssi) {
                notifyFindDevice(device, rssi);
            }
        });
        mBLEServiceOperate.startLeScan();
        if (mCallback != null) {
            mCallback.onScanBleDeviceStart();
        }
    }

    private void notifyFindDevice(BluetoothDevice device, int rssi) {
        // 如果用户已经停止了扫描,不再回调.
        if (!isBtScanning()) {
            return;
        }
        BleDevices bleDevice = new BleDevices(device.getName(), device.getAddress(), rssi);
        if (mCallback != null) {
            mCallback.onFindBleDevice(bleDevice);
        }
    }

    /**
     * 停止蓝牙扫描
     */
    public void stopBtScan() {
        LogUtil.i(TAG, "stopBtScan");
        mBtScanning = false;
        mBLEServiceOperate.setDeviceScanListener(null);
        mBLEServiceOperate.stopLeScan();
        if (mCallback != null) {
            mCallback.onScanBleDeviceStop();
        }
    }

    /**
     * 蓝牙是否正在扫描
     *
     * @return true 正在扫描,
     */
    public boolean isBtScanning() {
        return mBtScanning;
    }

    public void disConnect() {
        mBLEServiceOperate.disConnect();
    }

    /**
     * 连接到指定设备
     *
     * @param device 需要连接的设备
     */
    public void connectDevice(@NonNull BleDevices device) {
        if (!mBtReady) {
            LogUtil.w(TAG, "Bt is NOT Ready");
            return;
        }
        stopBtScan();
        mBLEServiceOperate.disConnect();
        mBluetoothLeService.setICallback(null);
        ICallback callback = new DefaultCallBack(device);
        mBluetoothLeService.setICallback(callback);

        String address = device.getAddress();
        if (mCallback != null) {
            mCallback.onConnectBleDeviceStart(device);
        }
        mBLEServiceOperate.connect(address);
    }

    public void syncAllData() {
        LogUtil.i(TAG, "syncAllData");
        if (mWriteCommandToBLE != null) {
            mWriteCommandToBLE.syncAllStepData();
            mWriteCommandToBLE.syncAllRateData();
            mWriteCommandToBLE.syncAllBloodPressureData();
            mWriteCommandToBLE.syncAllSleepData();

            //mWriteCommandToBLE.syncAllSkipData();
            //mWriteCommandToBLE.syncAllSwimData();
        }
    }

    /**
     * 判断指定的蓝牙设备是否是手环
     * 蓝牙4.0的设备很多,比如,Mobike,门禁都支持BT4.0
     *
     * @param device 指定的蓝牙设备
     * @return true, 确定是蓝牙手环(支持计步器功能), 否则false
     */
    public boolean isBraceletDevice(BleDevices device) {
        // GetFunctionList.isSupportFunction();
        return true;
    }

    private final class DefaultCallBack extends SimpleCallBack {

        DefaultCallBack(@NonNull BleDevices device) {
            super(device);
        }

        @Override
        public void OnResult(boolean result, int status) {
            super.OnResult(result, status);
            switch (status) {
                case ICallbackStatus.DISCONNECT_STATUS: {
                    LogUtil.i(TAG, "DISCONNECT_STATUS");
                    if (mCallback != null) {
                        mCallback.onDisconnectBleDevice(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.CONNECTED_STATUS: {
                    LogUtil.i(TAG, "CONNECTED_STATUS");
                    if (mCallback != null) {
                        mCallback.onConnectedBleDevice(mDevice);
                    }
                    mDataProcessing.setOnStepChangeListener(new StepChangeListener() {
                        @Override
                        public void onStepChange(StepOneDayAllInfo stepOneDayAllInfo) {
                            if (mCallback != null) {
                                mCallback.onStepChanged(mDevice, stepOneDayAllInfo);
                            }
                        }
                    });
                    break;
                }
                case ICallbackStatus.SYNC_TIME_OK: {
                    LogUtil.i(TAG, "SYNC_TIME_OK");
                    if (mCallback != null) {
                        mCallback.onSyncTimeOk(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.OFFLINE_STEP_SYNCING: {
                    LogUtil.i(TAG, "OFFLINE_STEP_SYNCING");
                    if (mCallback != null) {
                        mCallback.onStepSyncing(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.OFFLINE_STEP_SYNC_OK: {
                    LogUtil.i(TAG, "OFFLINE_STEP_SYNC_OK");
                    if (mCallback != null) {
                        mCallback.onStepSyncOk(mDevice);
                    }

                    mWriteCommandToBLE.syncAllRateData();
                    break;
                }
                case ICallbackStatus.OFFLINE_RATE_SYNCING: {
                    LogUtil.i(TAG, "OFFLINE_RATE_SYNCING");
                    if (mCallback != null) {
                        mCallback.onRateSyncing(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.OFFLINE_RATE_SYNC_OK: {
                    LogUtil.i(TAG, "OFFLINE_RATE_SYNC_OK");
                    if (mCallback != null) {
                        mCallback.onRateSyncOk(mDevice);
                    }
                    mWriteCommandToBLE.syncAllBloodPressureData();
                    break;
                }
                case ICallbackStatus.OFFLINE_BLOOD_PRESSURE_SYNCING: {
                    LogUtil.i(TAG, "OFFLINE_BLOOD_PRESSURE_SYNCING");
                    if (mCallback != null) {
                        mCallback.onBloodPressureSyncing(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.OFFLINE_BLOOD_PRESSURE_SYNC_OK: {
                    LogUtil.i(TAG, "OFFLINE_BLOOD_PRESSURE_SYNC_OK");
                    if (mCallback != null) {
                        mCallback.onBloodPressureSyncOk(mDevice);
                    }
                    mWriteCommandToBLE.syncAllSleepData();
                    break;
                }
                case ICallbackStatus.OFFLINE_SLEEP_SYNCING: {
                    LogUtil.i(TAG, "OFFLINE_SLEEP_SYNCING");
                    if (mCallback != null) {
                        mCallback.onSleepSyncing(mDevice);
                    }
                    break;
                }
                case ICallbackStatus.OFFLINE_SLEEP_SYNC_OK: {
                    LogUtil.i(TAG, "OFFLINE_SLEEP_SYNC_OK");
                    if (mCallback != null) {
                        mCallback.onSleepSyncOk(mDevice);
                    }
                    break;
                }
            }
        }
    }

}
