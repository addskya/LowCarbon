package com.mylowcarbon.app.my.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.BaseActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityEdittranspwdBinding;

/**
 * 修改交易密码
 */
public class EditTransPwdActivity extends ActionBarActivity implements EditTransPwdContract.View {
    private static final String TAG = "EditTransPwdActivity";
    private EditTransPwdContract.Presenter mPresenter;
    private ActivityEdittranspwdBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edittranspwd);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new EditTransPwdPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_edit_trans_pwd;
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
    public void setPresenter(EditTransPwdContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://设置
                finish();
                break;
            case 1://

                break;


            default:
                break;
        }
    }
}
