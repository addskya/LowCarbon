package com.mylowcarbon.app.register.height;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.adapter.WheelAdapter;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentHeightBinding;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.register.basic.UserFragment;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 * 身高修改界面,存在 两种模式,一个是注册时直接使用,完成后跳转到下一个fragment
 * 另外个是设置界面,点击完成后返回
 */
public class HeightFragment extends UserFragment
        implements HeightContract.View {
    private static final String TAG = "HeightFragment";
    private static final String EXTRA_MODE =
            "com.mylowcarbon.app.EXTRA_PICK_MODE";

    private static final List<HeightEntry> sHeight = new ArrayList<>();

    static {
        sHeight.add(new HeightEntry("110Cm", 110));
        sHeight.add(new HeightEntry("120Cm", 120));
        sHeight.add(new HeightEntry("130Cm", 130));
        sHeight.add(new HeightEntry("140Cm", 140));
        sHeight.add(new HeightEntry("150Cm", 150));
        sHeight.add(new HeightEntry("160Cm", 160));
        sHeight.add(new HeightEntry("170Cm", 170));
        sHeight.add(new HeightEntry("180Cm", 180));
        sHeight.add(new HeightEntry("190Cm", 190));
        sHeight.add(new HeightEntry("200Cm", 200));
    }

    /**
     * 修改身高Fragment
     *
     * @param userInfo the Origin userInfo
     * @param pickMode 是否以pick mode,用于在设置中选择性别修改.
     * @return Fragment
     */
    public static Fragment getArgsFragment(@Nullable UserInfo userInfo,
                                           boolean pickMode) {
        Bundle args = new Bundle(1);
        args.putBoolean(EXTRA_MODE, pickMode);
        Fragment fragment = new HeightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean mPickMode;
    private FragmentHeightBinding mBinding;
    private HeightContract.Presenter mPresenter;

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
        mBinding = FragmentHeightBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new HeightPresenter(this);
        mBinding.setView(this);
        mBinding.setPickMode(mPickMode);
        mBinding.executePendingBindings();


        mBinding.selector.setAdapter(new WheelAdapter<HeightEntry>() {
            @Override
            public int getItemsCount() {
                return sHeight.size();
            }

            @Override
            public HeightEntry getItem(int index) {
                return sHeight.get(index);
            }

            @Override
            public int indexOf(HeightEntry value) {
                return sHeight.indexOf(value);
            }
        });

        mBinding.selector.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectItem(index);
            }
        });

        int centerItemIndex = sHeight.size() / 2;
        mBinding.selector.setCurrentItem(centerItemIndex);
        selectItem(centerItemIndex);
    }

    private void selectItem(int index) {
        int heightInCm = sHeight.get(index).getHeightInCm();
        mBinding.heightView.setText(getString(R.string.format_height, heightInCm));
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(HeightContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadUserInfoSuccess(@NonNull UserInfo userInfo) {
        LogUtil.i(TAG, "onLoadUserInfoSuccess");

        int currentItem = -1;

        for (HeightEntry entry : sHeight) {
            if (entry.getHeightInCm() == userInfo.getHeight()) {
                currentItem = sHeight.indexOf(entry);
                break;
            }
        }
        if (currentItem != -1) {
            mBinding.selector.setCurrentItem(currentItem);
        }
    }

    @Override
    public void previous() {
        // onBackPressed();
    }

    @Override
    public void commit() {
        int heightInCm = sHeight.get(mBinding.selector.getCurrentItem()).getHeightInCm();
        mPresenter.modifyHeight(heightInCm);
    }

    @Override
    public void onModifyHeightStart() {
        LogUtil.i(TAG, "onModifyHeightStart");
        showLoadingDialog();
    }

    @Override
    public void onModifyHeightSuccess(int heightInCm) {
        LogUtil.i(TAG, "onModifyHeightSuccess:" + heightInCm);
        // goto next
        if (mPickMode) {
            Intent data = new Intent();
            data.putExtra("select_height", heightInCm);
            getActivity().setResult(Activity.RESULT_OK, data);
            getActivity().finish();
            return;
        }
        Bundle args = new Bundle(1);
        translateTo("WeightFragment", args);
    }

    @Override
    public void onModifyHeightFail(int errorCode) {
        LogUtil.w(TAG, "onModifyHeightFail:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onModifyHeightError(Throwable error) {
        LogUtil.e(TAG, "onModifyHeightError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onModifyHeightCompleted() {
        LogUtil.i(TAG, "onModifyHeightCompleted");
        dismissLoadingDialog();
    }
}

