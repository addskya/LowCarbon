package com.mylowcarbon.app.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.TabHost;

import com.mylowcarbon.app.App;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.JsActionBarActivity;
import com.mylowcarbon.app.databinding.ActivityMainBinding;
import com.mylowcarbon.app.databinding.TabItemBinding;
import com.mylowcarbon.app.home.mine.MineFragment;
import com.mylowcarbon.app.home.my.MyFragment;
import com.mylowcarbon.app.home.trade.TradeFragment;
import com.mylowcarbon.app.javascript.JsCallback;
import com.mylowcarbon.app.login.LoginActivity;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends JsActionBarActivity {
    private static final String TAG = "TransferActivity";

    private static final int REQUEST_CODE_LOGIN = 0x1;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
//    private Class mClass[] = {MineFragment.class, TradeFragment.class, AwardFragment.class, AdvisoryFragment.class, MyFragment.class};
//    private Fragment mFragment[] = {new MineFragment(), new TradeFragment(), new AwardFragment(), new AdvisoryFragment(), new MyFragment()};
    private Class mClass[] = {MineFragment.class, TradeFragment.class, MyFragment.class};
    private Fragment mFragment[] = {new MineFragment(), new TradeFragment(),   new MyFragment()};
    private int mImages[] = {
            R.drawable.tab_mine,
            R.drawable.tab_trade,
//            R.drawable.tab_award,
//            R.drawable.tab_advisory,
            R.drawable.tab_my
    };
    private String mTitles[];
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.executePendingBindings();
        init();
    }

    @Override
    protected int getUiTitle() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gotoLoginIfNeed();
    }

    private void gotoLoginIfNeed() {
        if (!ModelDao.getInstance().isLogin()) {
            // 请求登录
            LoginActivity.intentTo(this, REQUEST_CODE_LOGIN);
        }
    }

    @Override
    public JsCallback getJsCallback() {

        return new JsCallback() {

            @Override
            public void onCallback(int message, String error, Object result) {
                LogUtil.e(TAG, "JsCallback onCallback : " + message);

                switch (message) {
                    case JsCallback.MESSAGE_INITCTOKEN:
                        onWalletInitCompleted();
                        break;

                    case JsCallback.MESSAGE_RATE:
                        if (error == null && result != null) {
                            Float mRate = Float.valueOf(result.toString());
                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_RATE rate: " + mRate);
                            App app = (App) getApplication();
                            app.setRate(mRate);
                        }

                        break;
                    case JsCallback.MESSAGE_BALANCE:
                        if (error == null && result != null) {
                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_BALANCE 余额: " + result);

                            if (callback!=null){
                                callback.onReceiveValue(result.toString());
                            }
                        }

                        break;
                    case JsCallback.MESSAGE_EXCHANGETOKEN:
                        if (error == null && result != null) {
                            LogUtil.e(TAG, "JsCallback onCallback MESSAGE_EXCHANGETOKEN 请求奖励: " + result);
                            if (callback!=null){
                                callback.onReceiveValue(result.toString());
                            }
                        }

                        break;

                }

            }
        };
    }

    @Override
    protected void onWalletInitCompleted() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               final UserInfo userInfo = ModelDao.getInstance().getUserInfo();
               if (userInfo == null){
                   return;
               }
                //获取钱包汇率
                getRate(null);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_LOGIN: {
                if (resultCode != RESULT_OK) {
                    finish();
                    return;
                }
                break;
            }
        }
    }

    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initData() {

    }

    private void initView() {

        mBinding.contentPanel.tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mBinding.contentPanel.tabhost.getTabWidget().setDividerDrawable(null);
        mTitles = getResources().getStringArray(R.array.tab_titles);
        for (int i = 0; i < mFragment.length; i++) {
            TabHost.TabSpec tabSpec = mBinding.contentPanel.tabhost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mBinding.contentPanel.tabhost.addTab(tabSpec, mClass[i], null);
            mFragmentList.add(mFragment[i]);
            mBinding.contentPanel.tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mBinding.contentPanel.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
     }


    private View getTabView(int index) {
        TabItemBinding bindView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.tab_item, null, false);
        bindView.image.setImageResource(mImages[index]);
        bindView.title.setText(mTitles[index]);
        return bindView.getRoot();
    }

    private void initEvent() {

        mBinding.contentPanel.tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mBinding.contentPanel.viewPager.setCurrentItem(mBinding.contentPanel.tabhost.getCurrentTab());
            }
        });

        mBinding.contentPanel.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.contentPanel.tabhost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private ValueCallback<String> callback;
    @Override
    public void balanceOf(@NonNull CharSequence address,@Nullable ValueCallback<String> callback) {
        super.balanceOf(address,  null);
        this.callback = callback;
    }




}
