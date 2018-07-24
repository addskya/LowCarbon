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
 * 计步数
 */

public class WalkView extends SportView {

    private static final String TAG = "WalkView";

    public WalkView(@NonNull Context context) {
        super(context);
    }

    public WalkView(@NonNull Context context,
                    @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WalkView(@NonNull Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSportIcon() {
        return R.drawable.ic_walk_light;
    }

    @Override
    protected int getSportDesc() {
        return R.string.text_walk_desc;
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

        if (key.equalsIgnoreCase(mSource.getStepCounterKey())) {
            refreshSportValue(key);
        }
    }

    private void refreshSportValue(@NonNull String key) {
        if (mPreferenceFile == null) {
            return;
        }

        float value = mPreferenceFile.getFloat(key, 0F);
        setSportValue(
                getResources().getString(R.string.format_walk_value, (int) value)
        );
    }

    @Override
    public void setSportDataSource(SportDataSource source) {
        super.setSportDataSource(source);
        if (source == null) {
            return;
        }
        refreshSportValue(source.getStepCounterKey());
        setEnabled(source.isSupportStepCounter());
    }

}
