package com.mylowcarbon.app.bracelet.base;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.utils.LogUtil;
import com.yc.pedometer.sdk.ICallback;

/**
 * Created by Orange on 18-4-14.
 * Email:addskya@163.com
 */
public class SimpleCallBack implements ICallback {
    private static final String TAG = "SimpleCallBack";
    protected BleDevices mDevice;

    SimpleCallBack(@NonNull BleDevices device) {
        mDevice = device;
    }

    @Override
    public void OnResult(boolean result, int status) {
        LogUtil.i(TAG, "OnResult, result:" + result + ",status:" + status);
    }

    @Override
    public void OnDataResult(boolean result, int status, byte[] data) {
        LogUtil.i(TAG, "OnDataResult, result:" + result + ",status:" + status + ",data:" + data);
    }

    @Override
    public void onCharacteristicWriteCallback(int status) {
        LogUtil.i(TAG, "onCharacteristicWriteCallback,status:" + status);
    }

    @Override
    public void onIbeaconWriteCallback(boolean result, int ibeaconSetOrGet,
                                       int ibeaconType, String data) {
        LogUtil.i(TAG, "onCharacteristicWriteCallback,result:" + result);
    }

    @Override
    public void onQueryDialModeCallback(boolean result, int screenWith,
                                        int screenHeight, int screenCount) {
        LogUtil.i(TAG, "onCharacteristicWriteCallback,result:" + result +
                ",screenWith:" + screenWith +
                ",screenHeight:" + screenHeight
                + ",screenCount:" + screenCount);
    }

    @Override
    public void onControlDialCallback(boolean result, int leftRightHand,
                                      int dialType) {
        LogUtil.i(TAG, "onCharacteristicWriteCallback,result:" + result +
                ",leftRightHand:" + leftRightHand +
                ",dialType:" + dialType);
    }

    @Override
    public void onSportsTimeCallback(boolean result, String calendar, int sportsTime,
                                     int timeType) {
        LogUtil.i(TAG, "onSportsTimeCallback,result:" + result +
                ",calendar:" + calendar +
                ",sportsTime:" + sportsTime +
                ",timeType:" + timeType);
    }
}
