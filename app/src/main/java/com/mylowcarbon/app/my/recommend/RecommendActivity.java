package com.mylowcarbon.app.my.recommend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.own.DevicesActivity;
import com.mylowcarbon.app.databinding.ActivityRecommendBinding;
import com.mylowcarbon.app.bracelet.link.LinkBraceletActivity;

/**
 * 推荐有奖
 */
public class RecommendActivity extends ActionBarActivity implements RecommendContract.View {
    private static final String TAG = "RecommendActivity";
    private RecommendContract.Presenter mPresenter;
    private ActivityRecommendBinding mBinding;
    private MyRecommendFragment mMyRecommendFragment;
    private RecommendRankingFragment mRecommendRankingFragment;
    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;
    private String[] titles;
    private SparseArray<Fragment> fragmenArray = new SparseArray<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        setOperateText(R.string.txt_device_list);
        setOperateOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, DevicesActivity.class);
                startActivity(intent);
            }
        });
        new RecommendPresenter(this);
        initView();
    }



    @Override
    protected int getUiTitle() {
         return R.string.title_recommend;
    }

    public void initView() {
        titles = getResources().getStringArray(R.array.tab_recommend);
        dm = getResources().getDisplayMetrics();
        mBinding.pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mBinding.tabs.setViewPager(mBinding.pager);
        setTabsValue();
    }


    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
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
//		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));// #45c01a
        mBinding.tabs.setSelectedTextColor(getResources().getColor(R.color.orange));// #45c01a
        // 设置字体景色
//		tabs.setTextColor(R.drawable.selector_txt_tab_mine);
        mBinding.tabs.setTextColor(getResources().getColor(R.color.black));
//		tabs.setTextColor(getResources().getColor(R.drawable.selector_tab_toptitle_color));// #45c01a


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
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
                    if (null == mMyRecommendFragment) {
                        mMyRecommendFragment = new MyRecommendFragment();
                    }
                    return mMyRecommendFragment;

                case 1:
                    if (null == mRecommendRankingFragment) {
                        mRecommendRankingFragment = new RecommendRankingFragment();
                    }
                    return mRecommendRankingFragment;

                default:
                    return null;
            }
        }

    }

    public void devicesList() {
        Intent intent = new Intent(this, LinkBraceletActivity.class);
        startActivity(intent);
    }

}
