package com.mylowcarbon.app.my.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.BaseActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityEditdevicenameBinding;

/**
 * 编辑手环名称
 */
public class EditDeviceNameActivity extends ActionBarActivity implements EditDeviceNameContract.View {
    private static final String TAG = "BindEmailActivity";
    private EditDeviceNameContract.Presenter mPresenter;
    private ActivityEditdevicenameBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_editdevicename);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new EditDeviceNamePresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_edit_device_name;
    }

    public void initView() {


    }

    public void initData() {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(EditDeviceNameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://
                finish();
                break;
            case 1://

                break;


            default:
                break;
        }
    }
}
