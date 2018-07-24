package com.mylowcarbon.app.mine.walk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mylowcarbon.app.sport.SportDataSource;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 * 显示今日步数View,要根据数据源不同而改变
 */

public class SimpleWalkView extends AppCompatTextView {

    private static final String TAG = "SimpleWalkView";

    public SimpleWalkView(@NonNull Context context) {
        super(context);
    }

    public SimpleWalkView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleWalkView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected SharedPreferences mPreferenceFile;

    private SharedPreferences.OnSharedPreferenceChangeListener mOnChangedListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    onPreferenceChanged(sharedPreferences, key);
                }
            };

    protected void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        LogUtil.i(TAG, "onPreferenceChanged,key:" + key);
        if (mSource == null) {
            return;
        }

        if (key.equalsIgnoreCase(mSource.getStepCounterKey())) {
            refreshSportValue(key);
        }
    }

    protected void refreshSportValue(@NonNull String key) {
        if (mPreferenceFile == null) {
            return;
        }

        float value = mPreferenceFile.getFloat(key, 0F);
        setText(String.valueOf((int) value));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPreferenceFile == null) {
            mPreferenceFile = getContext().getSharedPreferences("sports.xml", Context.MODE_PRIVATE);
        }
        mPreferenceFile.registerOnSharedPreferenceChangeListener(mOnChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPreferenceFile != null) {
            mPreferenceFile.unregisterOnSharedPreferenceChangeListener(mOnChangedListener);
        }
        mPreferenceFile = null;
    }

    // 运动数据源
    protected SportDataSource mSource;

    /**
     * 设置运动数据源, DataBinding 方式调用
     *
     * @param source 动数据源
     */
    public void setSportDataSource(SportDataSource source) {
        mSource = source;
        if (source == null) {
            return;
        }
        refreshSportValue(source.getStepCounterKey());
        setEnabled(source.isSupportStepCounter());
        setAlpha(isEnabled() ? 1.0F : 0.5F);
    }
}
