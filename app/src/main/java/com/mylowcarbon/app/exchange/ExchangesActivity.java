package com.mylowcarbon.app.exchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.exchange.fragment.ExchangeFragment;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换LCL页面
 */
public class ExchangesActivity extends ActionBarActivity
        implements ExchangesContract.View {
    private static final String TAG = "ExchangesActivity";

    private ExchangesContract.Presenter mPresenter;
    private ExchangesPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        final ViewPager pager = findViewById(R.id.pages);
        mAdapter = new ExchangesPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);

        final View indicator = findViewById(R.id.indicator);
        final RadioGroup radioGroup = findViewById(R.id.group);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final ViewGroup container = radioGroup;
                final int pageWidth = container.getMeasuredWidth();
                final int childCount = radioGroup.getChildCount();
                if (childCount <= 0) {
                    return;
                }
                final View firstView = container.getChildAt(0);
                if (firstView == null) {
                    return;
                }
                final int childWidth = firstView.getMeasuredWidth();
                float offset = (pageWidth * 1.0f - childWidth * childCount) / (childCount - 1) + childWidth;
                indicator.setTranslationX((position + positionOffset) * offset);
            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int checkedIndex = group.indexOfChild(checkedView);
                pager.setCurrentItem(checkedIndex);
            }
        });

        new ExchangesPresenter(this);
        mPresenter.loadDevice();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_exchange;
    }

    @Override
    public void setPresenter(ExchangesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadDeviceStart() {
        LogUtil.i(TAG, "onLoadDeviceStart");
        showLoadingDialog();
    }

    @Override
    public void onLoadDeviceSuccess(@Nullable List<Device> devices) {
        LogUtil.i(TAG, "onLoadDeviceSuccess");
        mAdapter.setDevices(devices);
        if (devices != null && devices.size() > 0) {
            RadioGroup radioGroup = findViewById(R.id.group);
            int size = devices.size();
            LayoutInflater inflater = getLayoutInflater();
            for (int i = 0; i < size; i++) {
                inflater.inflate(R.layout.item_radio_button, radioGroup, true);
            }
            for (int i = 0; i < size; i++) {
                View child = radioGroup.getChildAt(i);
                if (child instanceof RadioButton) {
                    ((RadioButton) child).setText(devices.get(i).getDate());
                }
            }
            setIndicatorWidth(size);
        }
    }

    private void setIndicatorWidth(int size) {
        View indicator = findViewById(R.id.indicator);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / size;
        indicator.setLayoutParams(layoutParams);
    }

    @Override
    public void onLoadDeviceFail(int errorCode) {
        LogUtil.w(TAG, "onLoadDeviceFail,errorCode:" + errorCode);
    }

    @Override
    public void onLoadDeviceError(Throwable error) {
        LogUtil.e(TAG, "onLoadDeviceError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onLoadDeviceCompleted() {
        LogUtil.i(TAG, "onLoadDeviceCompleted");
        dismissLoadingDialog();
    }

    private class ExchangesPagerAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Fragment> fragments = new SparseArray<Fragment>();
        private final List<Device> mDevices;

        ExchangesPagerAdapter(FragmentManager fm) {
            super(fm);
            mDevices = new ArrayList<>(1);
            mDevices.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDevices.get(position).getDate();
        }

        @Override
        public int getCount() {
            return mDevices.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(position, null);
            if (fragment == null) {
                Device device = mDevices.get(position);
                fragment = ExchangeFragment.getArgsFragment(device);
                fragments.put(position, fragment);
            }
            return fragment;
        }

        void setDevices(@Nullable List<Device> devices) {
            mDevices.clear();
            if (devices != null && devices.size() > 0) {
                mDevices.addAll(devices);
                notifyDataSetChanged();
            }
        }
    }
}
