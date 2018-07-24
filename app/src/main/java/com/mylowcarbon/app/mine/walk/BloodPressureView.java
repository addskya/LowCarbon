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
 * 血压计
 */

public class BloodPressureView extends SportView {

    private static final String TAG = "BloodPressureView";

    public BloodPressureView(@NonNull Context context) {
        super(context);
    }

    public BloodPressureView(@NonNull Context context,
                             @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BloodPressureView(@NonNull Context context,
                             @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSportIcon() {
        return R.drawable.ic_blood_light;
    }

    @Override
    protected int getSportDesc() {
        return R.string.text_blood_pressure_desc;
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

        if (key.equalsIgnoreCase(mSource.getBloodPressureKey())) {
            refreshSportValue(key);
        }
    }

    private void refreshSportValue(@NonNull String key) {
        if (mPreferenceFile == null) {
            return;
        }

        float value = mPreferenceFile.getFloat(key,0F);
        setSportValue(
                getResources().getString(R.string.format_blood_pressure_value, (int) value)
        );
    }

    @Override
    public void setSportDataSource(SportDataSource source) {
        super.setSportDataSource(source);
        if (source == null) {
            return;
        }
        refreshSportValue(source.getBloodPressureKey());
        setEnabled(source.isSupportBloodPressure());
    }
}
