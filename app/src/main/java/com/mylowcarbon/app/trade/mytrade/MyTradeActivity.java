package com.mylowcarbon.app.trade.mytrade;

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

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityMytradeBinding;
import com.mylowcarbon.app.net.response.MyTrade;
import com.mylowcarbon.app.utils.ToastUtil;

/**
 * 我的交易
 */
public class MyTradeActivity extends ActionBarActivity implements MyTradeActivityContract.View {
    private static final String TAG = "MyTradeActivity";
    private MyTradeActivityContract.Presenter mPresenter;
    private ActivityMytradeBinding mBinding;
    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;
    private String[] titles;
    private SparseArray<Fragment> fragmenArray = new SparseArray<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mytrade);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new MyTradeActivityPresenter(this);
        initView();
        initData();
    }



    @Override
    protected int getUiTitle() {
        return R.string.title_my_trade;
    }

    public void initView() {
        titles = getResources().getStringArray(R.array.tab_my_trade);
        dm = getResources().getDisplayMetrics();
        mBinding.pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mBinding.tabs.setViewPager(mBinding.pager);
        setTabsValue();
    }

    private void initData() {
        showLoadingDialog();
        mPresenter.getWalletData();
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
    public void setPresenter(MyTradeActivityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDataSuc(MyTrade data) {
        dismissLoadingDialog();
        if(data == null ){
           return;
        }
        mBinding.tvTotalAmount.setText(""+data.total_amount);
        mBinding.tvTodayMarketValue.setText("¥"+data.today_market_value);
        mBinding.tvForbidAmount.setText(""+data.forbid_amount);
        mBinding.tvSurplusAmount.setText(""+data.surplus_amount);


    }

    @Override
    public void onDataFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(this, msg);
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
            Fragment fragment = fragmenArray.get(position, null);
            if (fragment == null) {
                fragment = MyTradeFragment.CreateFragment(position);
                fragmenArray.put(position, fragment);
            }
            return fragment;
         }

    }
}
