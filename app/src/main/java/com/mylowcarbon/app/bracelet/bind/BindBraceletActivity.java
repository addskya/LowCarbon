package com.mylowcarbon.app.bracelet.bind;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.base.BaseBraceletActivity;
import com.mylowcarbon.app.databinding.ActivityBindBraceletBinding;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.ui.customize.WaveView;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 绑定手环
 */
public class BindBraceletActivity extends BaseBraceletActivity
        implements BindBraceletContract.View {
    private static final String TAG = "BindBraceletActivity";

    private BindBraceletContract.Presenter mPresenter;
    private BindBraceletAdapter mAdapter;
    private ActivityBindBraceletBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_bind_bracelet);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        LayoutInflater inflater = getLayoutInflater();
        mAdapter = new BindBraceletAdapter(inflater, this);
        mBinding.list.setAdapter(mAdapter);

        new BindBraceletPresenter(this);

        WaveView waveView = mBinding.waveView;
        waveView.setDuration(5000);
        waveView.setStyle(Paint.Style.FILL);
        waveView.setColor(getResources().getColor(R.color.purple));
        waveView.setInterpolator(new LinearOutSlowInInterpolator());
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
        };

        requestPermissions(permissions, new Runnable() {
            @Override
            public void run() {
                mPresenter.setupBtService();
            }
        });
    }

    @Override
    protected void onRequestPermissionsFailure(String[] requestFailurePermissions) {
        super.onRequestPermissionsFailure(requestFailurePermissions);
        finish();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_bind_bracelet;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopScan();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void gotoBack() {
        mPresenter.stopScan();
        finish();
    }

    @Override
    public void onNotSupportBle40() {
        LogUtil.i(TAG, "onBlt40NotSupport");
        toastMessage(R.string.not_support_ble);
        finish();
    }

    @Override
    public void onBleNotEnabled() {
        LogUtil.i(TAG, "onBtNotEnable");
        mBinding.btDisable.setVisibility(View.VISIBLE);
        mBinding.btnOpen.setVisibility(View.VISIBLE);

        mBinding.btSearching.setVisibility(View.GONE);
        mBinding.btCloseTips.setVisibility(View.GONE);
    }

    @Override
    public void gotoEnableBt() {
        requestEnableBt();
    }

    @CallSuper
    @Override
    protected void onEnableBtSuccess() {
        super.onEnableBtSuccess();
        mPresenter.startScan();
    }

    @CallSuper
    @Override
    protected void onEnableBtFail() {
        super.onEnableBtFail();
        // 用户拒绝了蓝牙开启的请求,退出当前Activity
        toastMessage(R.string.not_support_ble);
        finish();
    }

    @Override
    public void onStartScan() {
        LogUtil.i(TAG, "onStartScan");
        mBinding.waveView.start();
        mAdapter.clear();

        mBinding.btDisable.setVisibility(View.GONE);
        mBinding.btnOpen.setVisibility(View.GONE);

        mBinding.btSearching.setVisibility(View.VISIBLE);
        mBinding.btCloseTips.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopScan() {
        LogUtil.i(TAG, "onStopScan");
        mBinding.waveView.stop();
    }

    @Override
    public void onFindDevice(@NonNull BleDevices device) {
        LogUtil.i(TAG, "onFindDevice:" + device);
        mAdapter.addData(device);
        mAdapter.replaceData(device);
        // mBinding.list.setVisibility(mAdapter.getDataCount() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void connectDevice(BleDevices device) {
        LogUtil.i(TAG, "connectDevice:" + device);
        mPresenter.connectBleDevice(device);
    }

    @Override
    public void onConnectBleDeviceStart() {
        LogUtil.i(TAG, "onConnectBleDeviceStart");
        showLoadingDialog();
    }

    @Override
    public void onBleDeviceConnected() {
        LogUtil.i(TAG, "onConnectBleDeviceSuccess");
        dismissLoadingDialog();
        setResult(RESULT_OK);
        gotoBack();
    }

    @Override
    public void onBleDeviceDisconnected() {
        LogUtil.w(TAG, "onConnectBleDeviceFail");
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(BindBraceletContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
