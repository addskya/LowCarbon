package com.mylowcarbon.app.mine.walk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.sport.SportDataSource;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 * 卡路里
 */

public class CalorieView extends SportView {

    private static final String TAG = "CalorieView";

    public CalorieView(@NonNull Context context) {
        super(context);
    }

    public CalorieView(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalorieView(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSportIcon() {
        return R.drawable.ic_cal_light;
    }

    @Override
    protected int getSportDesc() {
        return R.string.calorie;
    }

    @Override
    protected boolean isSportDescVisible() {
        return true;
    }

    @Override
    protected void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        LogUtil.i(TAG, "onPreferenceChanged,key:" + key);
        if (mSource == null) {
            return;
        }

        if (key.equalsIgnoreCase(mSource.getCalorieKey())) {
            refreshSportValue(key);
        }
    }

    private void refreshSportValue(@NonNull String key) {
        if (mPreferenceFile == null) {
            return;
        }

        float value = mPreferenceFile.getFloat(key,0F);
        setSportValue(
                getResources().getString(R.string.format_calorie_value, (int) value)
        );
    }

    @Override
    public void setSportDataSource(SportDataSource source) {
        super.setSportDataSource(source);
        if (source == null) {
            return;
        }
        refreshSportValue(source.getCalorieKey());
        setEnabled(source.isSupportCalorie());
    }
}
