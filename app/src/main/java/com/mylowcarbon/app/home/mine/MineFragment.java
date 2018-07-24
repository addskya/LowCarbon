package com.mylowcarbon.app.home.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.link.LinkBraceletActivity;
import com.mylowcarbon.app.databinding.FragmentMineBinding;
import com.mylowcarbon.app.exchange.ExchangesActivity;
import com.mylowcarbon.app.mine.ride.RideSubFragment;
import com.mylowcarbon.app.mine.walk.WalkSubFragment;
import com.mylowcarbon.app.sport.phone.PhoneDataSource;
import com.mylowcarbon.app.sport.SportDataSource;

/**
 * 主页-挖矿
 */
public class MineFragment extends BaseFragment implements MineContract.View {

    private static final String TAG = "MineFragment";


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


    private MineContract.Presenter mPresenter;
    private FragmentMineBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMineBinding.inflate(inflater, container, false);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mBinding.tabs.setViewPager(mBinding.pager);
        setTabsValue();
        createMenu();

    }

    @Override
    public void onResume() {
        super.onResume();
        bindDataSource();

        if (mWalkFragment != null) {
            mWalkFragment.setSportDataSource(mSportSource);
        }
        if (mRideFragment != null) {
            mRideFragment.setSportDataSource(mSportSource);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPopupMenu = null;
        mSportSource.tearDown(getContext());
    }

    private PopupMenu mPopupMenu;

    private void createMenu() {
        View anchor = mBinding.mineTop.dataSource;
        mPopupMenu = new PopupMenu(getContext(), anchor);
        mPopupMenu.getMenuInflater().inflate(
                R.menu.menu_sport_source, mPopupMenu.getMenu());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sport_source_phone: {

                return true;
            }
            case R.id.sport_source_bracelet: {

                return true;
            }
        }
        return false;
    }

    @Override
    public void showMenu() {
        // 暂时不显示选择 数据源
        // mPopupMenu.show();
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 设置Tab是自动填充满屏幕的
        mBinding.tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mBinding.tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mBinding.tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        mBinding.tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));// 4
        // 设置Tab标题文字的大小
        mBinding.tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm)); // 16
        // 设置Tab Indicator的颜色
        mBinding.tabs.setIndicatorColor(getResources().getColor(R.color.orange));// #45c01a
        // 设置选中Tab文字的颜色
//        tabs.setSelectedTextColor(Color.parseColor("#45c01a"));// #45c01a
        mBinding.tabs.setSelectedTextColor(getResources().getColor(R.color.orange));// #45c01a
        // 设置字体景色
//        tabs.setTextColor(R.drawable.selector_txt_tab_mine);
        mBinding.tabs.setTextColor(Color.parseColor("#ffffff"));
//        tabs.setTextColor(getResources().getColor(R.drawable.selector_tab_toptitle_color));// #45c01a


    }

    private SportDataSource mSportSource = new PhoneDataSource();

    private void bindDataSource() {
        mSportSource.setUp(getContext(), new Runnable() {
            @Override
            public void run() {
                mBinding.setDataSource(mSportSource);
                mBinding.executePendingBindings();

                if (mWalkFragment != null) {
                    mWalkFragment.setSportDataSource(mSportSource);
                }
                if (mRideFragment != null) {
                    mRideFragment.setSportDataSource(mSportSource);
                }
            }
        });
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private WalkSubFragment mWalkFragment;
    private RideSubFragment mRideFragment;

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;

        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
            titles = getResources().getStringArray(R.array.tab_mine);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (null == mWalkFragment) {
                        mWalkFragment = new WalkSubFragment();
                    }
                    return mWalkFragment;

                case 1:
                    if (null == mRideFragment) {
                        mRideFragment = new RideSubFragment();
                    }
                    return mRideFragment;

                default:
                    return null;
            }
        }

    }

    @Override
    public void linkBracelet() {
        Intent intent = new Intent(getContext(), LinkBraceletActivity.class);
        startActivity(intent);
    }

    @Override
    public void exchange() {
        Intent intent = new Intent(getContext(), ExchangesActivity.class);
        startActivity(intent);
    }

}

