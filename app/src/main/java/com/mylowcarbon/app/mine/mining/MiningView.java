package com.mylowcarbon.app.mine.mining;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

import greendao.SportInfoDao;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 * 显示今天总能量的View
 */

public class MiningView extends LinearLayout implements MiningContract.View {
    private static final String TAG = "MiningView";

    public MiningView(@NonNull Context context) {
        this(context, null);
    }

    public MiningView(@NonNull Context context,
                      @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateView(context);
    }

    public MiningView(@NonNull Context context,
                      @Nullable AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
    }

    private TextView mTotalPower;
    private TextView mTotalLcl;

    private void inflateView(@NonNull Context context) {
        LogUtil.i(TAG, "inflateView");
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.mining_view, this, true);
        mTotalPower = findViewById(R.id.total_power);
        mTotalLcl = findViewById(R.id.total_lcl);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        new MiningPresenter(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtil.i(TAG, "onAttachedToWindow");

        Sport data = new Sport();

        SportInfoDao dao = ModelDao.getInstance()
                .getDaoSession()
                .getSportInfoDao();
        String date = DateUtil.getCalendar(0);
        List<SportInfo> sportInfos = dao.queryBuilder()
                .where(SportInfoDao.Properties.Date.eq(date))
                .list();
        String imei = null;
        if (sportInfos != null && sportInfos.size() > 0) {
            SportInfo sportInfo = sportInfos.get(0);
            data.setSteps(sportInfo.getSteps());
            data.setBurn((int) sportInfo.getBurn());
            data.setClycle(sportInfo.getClycle());
            imei = sportInfo.getImei();
        }

        data.setDate(date);

        if (!TextUtils.isEmpty(imei)) {
            mPresenter.loadTodayMining(imei, data);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.i(TAG, "onDetachedFromWindow");
        mPresenter.destroy();
        mPresenter = null;
    }

    private MiningContract.Presenter mPresenter;

    @Override
    public void setPresenter(MiningContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isAdded() {
        return getParent() != null;
    }

    @Override
    public void onLoadTodayMiningStart() {
        LogUtil.i(TAG, "onLoadTodayMiningStart");
    }

    @Override
    public void onLoadTodayMiningSuccess(@Nullable Mining data) {
        LogUtil.i(TAG, "onLoadTodayMiningSuccess,data:" + data);

        mTotalPower.setText(String.valueOf(data == null ? 0 : data.getTotal_energy()));

        mTotalLcl.setText(
                getResources().getString(
                        R.string.format_total_lcl, data == null ? 0 : data.getTotal_lcl()));

    }

    @Override
    public void onLoadTodayMiningFail(int errorCode) {
        LogUtil.w(TAG, "onLoadTodayMiningFail,errorCode:" + errorCode);
    }

    @Override
    public void onLoadTodayMiningError(Throwable error) {
        LogUtil.e(TAG, "onLoadTodayMiningError", error);
    }

    @Override
    public void onLoadTodayMiningCompleted() {
        LogUtil.i(TAG, "onLoadTodayMiningCompleted");
    }
}
