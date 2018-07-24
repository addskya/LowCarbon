package com.mylowcarbon.app.my.about;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityAboutBinding;
import com.mylowcarbon.app.net.response.About;
import com.mylowcarbon.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于
 */
public class AboutActivity extends ActionBarActivity implements AboutContract.View {
    private static final String TAG = "SplashActivity";

    private AboutContract.Presenter mPresenter;
    private ActivityAboutBinding mBinding;
    private AboutAdapter mAdapter;
    private About mAbout;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new AboutPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_about;
    }

    public void initView() {
        mDatas = new ArrayList<String>();

        mAdapter = new AboutAdapter(this, mDatas);
        mAdapter.setView(this);
        mBinding.rvContent.setLayoutManager(new GridLayoutManager(this, 4));

        // 设置Item增加、移除动画
        mBinding.rvContent.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvContent.setAdapter(mAdapter);

    }

    public void initData() {
        getAboutData();

    }


    @Override
    public void getAboutData() {


        mPresenter.getAboutData();

    }

    @Override
    public void onDataSuc(About mAbout) {
        Log.e(TAG, "onDataSuc   " + mAbout.toString());

        this.mAbout = mAbout;
        mBinding.tvQq.setText(String.format(getResources().getString(R.string.format_contact_qq), mAbout.contact.qq));
        mBinding.tvWechat.setText(String.format(getResources().getString(R.string.format_contact_wechat), mAbout.contact.wechat));
        mBinding.tvWeibo.setText(String.format(getResources().getString(R.string.format_contact_weibo), mAbout.contact.weibo));
        mDatas.clear();
        mDatas.addAll(mAbout.partner);
        mAdapter.notifyDataSetChanged();
        mBinding.setAbout(mAbout);
        mBinding.executePendingBindings();
    }

    @Override
    public void onDataFail(String msg) {
        ToastUtil.showShort(this, msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://检查版本更新
                break;


            default:
                break;
        }
    }

}

