package com.mylowcarbon.app.mine.walk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.databinding.FragmentWalkBinding;
import com.mylowcarbon.app.sport.phone.PhoneDataSource;
import com.mylowcarbon.app.sport.SportDataSource;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 挖矿-步行
 */
public class WalkSubFragment extends BaseFragment implements WalkContract.View {

    private static final String TAG = "WalkSubFragment";

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


    private WalkContract.Presenter mPresenter;
    private FragmentWalkBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreateView");
        mBinding = FragmentWalkBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "onViewCreated");
        mBinding.setView(this);
        mBinding.arcProgress.setProgress(2500);


        mBinding.executePendingBindings();
        new WalkPresenter(this);
    }

    @Override
    public void setPresenter(WalkContract.Presenter presenter) {
        mPresenter = presenter;
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
