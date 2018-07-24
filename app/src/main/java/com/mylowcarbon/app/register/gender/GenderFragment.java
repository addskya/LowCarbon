package com.mylowcarbon.app.register.gender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentGenderBinding;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.register.basic.UserFragment;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-3.
 * Email:addskya@163.com
 * 性别（0：未知，1：男，2：女）
 */

public class GenderFragment extends UserFragment
        implements GenderContract.View {

    private static final String TAG = "GenderFragment";

    private static final String EXTRA_MODE =
            "com.mylowcarbon.app.EXTRA_PICK_MODE";

    /**
     * 修改性别Fragment
     *
     * @param userInfo the Origin userInfo
     * @param pickMode 是否以pick mode,用于在设置中选择性别修改.
     * @return Fragment
     */
    public static Fragment getArgsFragment(@Nullable UserInfo userInfo,
                                           boolean pickMode) {
        Bundle args = new Bundle(1);
        args.putBoolean(EXTRA_MODE, pickMode);
        Fragment fragment = new GenderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 启动此Fragment的方式：
    // 0 默认,此时显示下一步,注册时使用
    // 1 在设置中修改性别时,此时显示完成,选取性别使用
    private boolean mPickMode;
    private FragmentGenderBinding mBinding;
    private GenderContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
        Bundle args = getArguments();
        mPickMode = args != null && args.getBoolean(EXTRA_MODE, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreateView");
        mBinding = FragmentGenderBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "onViewCreated");
        new GenderPresenter(this);
        mBinding.setView(this);
        mBinding.setPickMode(mPickMode);
        mBinding.executePendingBindings();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_gender);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void commit() {
        LogUtil.i(TAG, "mPickMode:" + mPickMode);
        int checkedId = mBinding.genderGroup.getCheckedRadioButtonId();
        int selectedSex = 0;
        switch (checkedId) {
            case R.id.male: {
                selectedSex = 1;
                break;
            }
            case R.id.female: {
                selectedSex = 2;
                break;
            }
            default: {
                selectedSex = 0;
            }
        }
        mPresenter.modifySex(selectedSex);
    }

    @Override
    public void setPresenter(GenderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadUserInfoSuccess(@NonNull UserInfo userInfo) {
        // 解析当前用户的性别并显示,
        mBinding.setUserInfo(userInfo);
        mBinding.executePendingBindings();
    }

    @Override
    public void onModifyGenderStart() {
        showLoadingDialog();
    }

    @Override
    public void onModifyGenderSuccess(int gender) {
        dismissLoadingDialog();
        // 提示不?
        // 如果是PickMode,则应该返回当前选择的性别,并finish 当前界面
        if (mPickMode) {
            Intent data = new Intent();
            data.putExtra("select_sex", gender);
            getActivity().setResult(Activity.RESULT_OK, data);
            getActivity().finish();
            return;
        }
        translateTo("HeightFragment", null);
    }

    @Override
    public void onModifyGenderFail(int errorCode) {
        LogUtil.w(TAG, "onModifyGenderFail:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onModifyGenderError(Throwable error) {
        LogUtil.e(TAG, "onModifyGenderError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onModifyGenderCompleted() {
        LogUtil.i(TAG, "onModifyGenderCompleted");
        dismissLoadingDialog();
    }
}
