package com.mylowcarbon.app.register.weight;

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
import com.mylowcarbon.app.databinding.FragmentWeightBinding;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.register.basic.UserFragment;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
public class WeightFragment extends UserFragment implements WeightContract.View {

    private static final String TAG = "WeightFragment";
    private static final String EXTRA_MODE =
            "com.mylowcarbon.app.EXTRA_PICK_MODE";

    private static final List<WeightEntry> sWeight = new ArrayList<>(1);

    static {
        sWeight.add(new WeightEntry("40Kg", 40));
        sWeight.add(new WeightEntry("50Kg", 50));
        sWeight.add(new WeightEntry("60Kg", 60));
        sWeight.add(new WeightEntry("70Kg", 70));
        sWeight.add(new WeightEntry("80Kg", 80));
        sWeight.add(new WeightEntry("90Kg", 90));
    }

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
        Fragment fragment = new WeightFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private boolean mPickMode;
    private FragmentWeightBinding mBinding;
    private WeightContract.Presenter mPresenter;

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
        mBinding = FragmentWeightBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new WeightPresenter(this);
        mBinding.setView(this);
        mBinding.setPickMode(mPickMode);
        mBinding.executePendingBindings();

        mBinding.selector.setAdapter(new WheelAdapter<WeightEntry>() {
            @Override
            public int getItemsCount() {
                return sWeight.size();
            }

            @Override
            public WeightEntry getItem(int index) {
                return sWeight.get(index);
            }

            @Override
            public int indexOf(WeightEntry o) {
                return sWeight.indexOf(o);
            }
        });

        mBinding.selector.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectItem(index);
            }
        });

        int centerItemIndex = sWeight.size() / 2;
        mBinding.selector.setCurrentItem(centerItemIndex);
        selectItem(centerItemIndex);
    }

    private void selectItem(int index) {
        int weightInKg = sWeight.get(index).getWeightInKg();
        mBinding.weightView.setText(getString(R.string.format_weight, weightInKg));
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_weight);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void onLoadUserInfoSuccess(@NonNull UserInfo userInfo) {
        //TODO 显示当前的体重信息
        int currentItem = -1;

        for (WeightEntry entry : sWeight) {
            if (entry.getWeightInKg() == userInfo.getWeight()) {
                currentItem = sWeight.indexOf(entry);
                break;
            }
        }
        if (currentItem != -1) {
            mBinding.selector.setCurrentItem(currentItem);
        }
    }

    @Override
    public void previous() {
        getActivity().onBackPressed();
    }

    @Override
    public void setPresenter(WeightContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void commit() {
        int weightInKg = sWeight.get(mBinding.selector.getCurrentItem()).getWeightInKg();
        mPresenter.modifyWeight(weightInKg);
    }

    @Override
    public void onModifyWeightStart() {
        showLoadingDialog();
    }

    @Override
    public void onModifyWeightSuccess(int weightInKg) {
        Intent data = new Intent();
        data.putExtra("select_weight", weightInKg);
        getActivity().setResult(Activity.RESULT_OK, data);

        if (mPickMode) {
            getActivity().finish();
        } else {
            finish();
        }
    }

    @Override
    public void onModifyWeightFail(int errorCode) {
        LogUtil.w(TAG, "onModifyWeightFail:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onModifyWeightError(Throwable error) {
        LogUtil.e(TAG, "onModifyWeightError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onModifyWeightCompleted() {
        LogUtil.i(TAG, "onModifyWeightCompleted");
        dismissLoadingDialog();
    }
}
