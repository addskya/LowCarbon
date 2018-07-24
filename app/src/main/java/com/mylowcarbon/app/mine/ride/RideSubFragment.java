package com.mylowcarbon.app.mine.ride;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.databinding.FragmentRideBinding;
import com.mylowcarbon.app.ride.RideActivity;
import com.mylowcarbon.app.sport.phone.PhoneDataSource;
import com.mylowcarbon.app.sport.SportDataSource;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 挖矿-骑行
 */
public class RideSubFragment extends BaseFragment implements RideContract.View {

    private static final String TAG = "RideSubFragment";

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
    }

    private RideContract.Presenter mPresenter;
    private FragmentRideBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRideBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setView(this);
        new RidePresenter(this);
        mBinding.progress.setProgress(16);
    }

    @Override
    public void setPresenter(RideContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void ride() {
        Intent intent = new Intent(mActivity, RideActivity.class);
        startActivity(intent);
    }

    /**
     * 设置运动数据源
     *
     * @param source 数据源
     */
    public void setSportDataSource(SportDataSource source) {
        mBinding.setDataSource(source);
        mBinding.executePendingBindings();
    }
}
