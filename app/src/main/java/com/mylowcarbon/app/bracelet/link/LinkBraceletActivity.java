package com.mylowcarbon.app.bracelet.link;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.base.BaseBraceletActivity;
import com.mylowcarbon.app.bracelet.bind.BindBraceletActivity;
import com.mylowcarbon.app.databinding.ActivityLinkBraceletBinding;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 * 连接手环
 */
public class LinkBraceletActivity extends BaseBraceletActivity
        implements LinkBraceletContract.View {

    private static final String TAG = "LinkBraceletActivity";
    private static final int REQUEST_CODE_ADD_BRACELET = 0x60;

    private LinkBraceletContract.Presenter mPresenter;
    private ActivityLinkBraceletBinding mBinding;
    private LinkBraceletAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_link_bracelet);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new LinkBraceletPresenter(this);
        LayoutInflater inflater = getLayoutInflater();
        mAdapter = new LinkBraceletAdapter(inflater, this);
        mBinding.list.setAdapter(mAdapter);

        // 加载连接过的手环历史
        mPresenter.loadBracelet();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_link_bracelet;
    }


    // 用户请求添加一个新手环
    @Override
    public void addBracelet() {
        Intent intent = new Intent(this, BindBraceletActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_BRACELET);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        LogUtil.i(TAG, "onActivityResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_BRACELET: {
                if (resultCode == RESULT_OK) {
                    mPresenter.loadBracelet();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void onLoadBraceletStart() {
        LogUtil.i(TAG, "onLoadBraceletStart");
        showLoadingDialog();
    }

    @Override
    public void onLoadBraceletSuccess(@Nullable List<BleDevices> devices) {
        LogUtil.i(TAG, "onLoadBraceletSuccess" + devices);
        mAdapter.addData(devices);
    }

    @Override
    public void onLoadBraceletError(Throwable error) {
        LogUtil.e(TAG, "onLoadBraceletError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onLoadBraceletCompleted() {
        LogUtil.i(TAG, "onLoadBraceletCompleted");
        dismissLoadingDialog();
        if (mAdapter.getDataCount() > 0) {
            mBinding.list.setVisibility(View.VISIBLE);
            mBinding.emptyView.setVisibility(View.GONE);
        } else {
            mBinding.list.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onNotSupportBle40() {
        toastMessage(R.string.not_support_ble);
    }

    @Override
    public void onBleNotEnabled() {
        // 检测到蓝牙硬件未启动
        requestEnableBt();
    }

    // 用户同意,并开启了蓝牙设备
    @CallSuper
    @Override
    protected void onEnableBtSuccess() {
        super.onEnableBtSuccess();
        LogUtil.i(TAG, "onEnableBtSuccess");
    }

    // 用户请求连接指定的手环设备
    @CallSuper
    @Override
    public void connectDevice(@Nullable BleDevices device) {
        if (device != null) {
            mPresenter.connectDevice(device);
        }
    }

    @Override
    public void onConnectBleDeviceStart(@NonNull BleDevices device) {
        LogUtil.i(TAG, "onConnectBleDeviceStart");
        showLoadingDialog();
    }

    @Override
    public void onBleDeviceConnected(@NonNull BleDevices device) {
        LogUtil.i(TAG, "onBleDeviceConnected");
        device.setIsOnline(true);
        dismissLoadingDialog();

    }

    @Override
    public void onBleDeviceDisconnected(@NonNull BleDevices device) {
        LogUtil.i(TAG, "onBleDeviceDisconnected");
        device.setIsOnline(false);
        dismissLoadingDialog();
    }

    @Override
    public void onBleDeviceReady(@NonNull BleDevices device) {
        // 连接到指定的蓝牙设备了
        mPresenter.syncData(device);
    }

    @Override
    public void onSportInfoChanged(@NonNull BleDevices device) {
        LogUtil.i(TAG, "onSportInfoChanged:" + device.toDebugString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onTapBleDevice(@Nullable BleDevices device) {
        mPresenter.onTapBleDevice(device);
    }

    @Override
    public String getStepText(@Nullable SportInfo sport) {
        int step = sport != null ? sport.getSteps() : 0;
        return getString(R.string.format_step_count, step);
    }

    @Override
    public String getRidingText(@Nullable SportInfo sport) {
        float riding = sport != null ? sport.getClycle() : 0F;
        return getString(R.string.format_riding_km, riding);
    }

    @Override
    public String getCalorieText(@Nullable SportInfo sport) {
        long calorie = sport != null ? sport.getCalories() : 0L;
        return getString(R.string.format_calorie, calorie);
    }

    @Override
    public void onStepSyncing(@NonNull BleDevices device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog(R.string.text_step_syncing);
            }
        });
    }

    @Override
    public void onStepSyncOk(@NonNull BleDevices device) {
        dismissDialog();
    }

    @Override
    public void onRateSyncing(@NonNull BleDevices device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog(R.string.text_heart_rate_syncing);
            }
        });
    }

    @Override
    public void onRateSyncOk(@NonNull BleDevices device) {
        dismissDialog();
    }

    @Override
    public void onBloodPressureSyncing(@NonNull BleDevices device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog(R.string.text_blood_pressure_syncing);
            }
        });
    }

    @Override
    public void onBloodPressureSyncOk(@NonNull BleDevices device) {
        dismissDialog();
    }

    @Override
    public void onSleepSyncing(@NonNull BleDevices device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog(R.string.text_sleep_syncing);
            }
        });
    }

    @Override
    public void onSleepSyncOk(@NonNull BleDevices device) {
        dismissDialog();
    }

    private void dismissDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void setPresenter(LinkBraceletContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
