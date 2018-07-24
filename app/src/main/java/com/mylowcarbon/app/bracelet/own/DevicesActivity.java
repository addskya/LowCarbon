package com.mylowcarbon.app.bracelet.own;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityDevicesBinding;
import com.mylowcarbon.app.exchange.ExchangesActivity;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 * 我的设备
 */
public class DevicesActivity extends ActionBarActivity implements DevicesContract.View {

    private static final String TAG = "DevicesActivity";

    private DevicesContract.Presenter mPresenter;
    private DevicesAdapter mAdapter;
    private ActivityDevicesBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_devices);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        mAdapter = new DevicesAdapter(
                LayoutInflater.from(this), this);
        mBinding.list.setAdapter(mAdapter);


        new DevicesPresenter(this);
        mPresenter.loadDevices("2");//（1:按日期, 2:按设备）
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_my_device;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(DevicesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void exchange() {
        Intent intent = new Intent(this, ExchangesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadDevicesStart() {
        LogUtil.i(TAG, "onLoadDevicesStart");
        showLoadingDialog();
    }

    @Override
    public void onLoadDevicesSuccess(@Nullable List<Device> value) {
        LogUtil.i(TAG, "onLoadDevicesSuccess" + value);
        mAdapter.addData(value);
    }

    @Override
    public void onLoadDevicesFail(int errorCode) {
        LogUtil.w(TAG, "" + errorCode);
    }

    @Override
    public void onLoadDevicesError(Throwable e) {
        LogUtil.e(TAG, "onLoadDevicesError", e);
        dismissLoadingDialog();
        showError(e);
    }

    @Override
    public void onLoadDevicesCompleted() {
        LogUtil.i(TAG, "onLoadDevicesCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void addBracelet() {

    }

    @Override
    public String getStepText(@Nullable Device device, int padding) {
        Device.Mining mining = getMining(device, padding);
        int steps = mining != null ? mining.getSteps() : 0;
        return getString(R.string.format_step_count, steps);
    }

    @Override
    public String getRidingText(@Nullable Device device, int padding) {
        Device.Mining mining = getMining(device, padding);
        float clycle = mining != null ? mining.getClycle() : 0;
        return getString(R.string.format_riding_km, clycle);
    }

    @Override
    public String getCalorieText(@Nullable Device device, int padding) {
        Device.Mining mining = getMining(device, padding);
        int total_energy = mining != null ? mining.getTotal_energy() : 0;
        return getString(R.string.format_calorie, total_energy);
    }

    private Device.Mining getMining(@Nullable Device device, int padding) {
        if (device == null) {
            return null;
        }
        List<Device.Mining> list = device.getMining();
        if (list == null || list.size() <= 0) {
            return null;
        }
        // list[0] today
        // list[1] yesterday
        // list[2] the day before yesterday
        switch (padding) {
            case -2:
                return list.size() >= 3 ? list.get(2) : null;
            case -1: {
                return list.size() >= 2 ? list.get(1) : null;
            }
            case 0: {
                return list.size() >= 1 ? list.get(0) : null;
            }
            default: {
                throw new IllegalArgumentException("padding is NOT support:" + padding);
            }
        }
    }
}
