package com.mylowcarbon.app.mine.walk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.sport.SportDataSource;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 * 距离目标还差 100 步
 */

public class CountDownSimpleWalkView extends SimpleWalkView {

    private static final int TARGET_STEPS = 15000;

    public CountDownSimpleWalkView(@NonNull Context context) {
        super(context);
    }

    public CountDownSimpleWalkView(@NonNull Context context,
                                   @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownSimpleWalkView(@NonNull Context context,
                                   @Nullable AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void refreshSportValue(@NonNull String key) {
        if (mPreferenceFile == null) {
            return;
        }
        float value = mPreferenceFile.getFloat(key, 0F);
        int seek = TARGET_STEPS - (int) value;
        setText(
                getResources().getString(R.string.format_count_down_walk_value, seek)
        );
    }

    @Override
    public void setSportDataSource(SportDataSource source) {
        super.setSportDataSource(source);
        if (source != null) {
            setEnabled(source.isSupportStepCounter());
            setAlpha(isEnabled() ? 1.0F : 0.5F);
        }
    }
}
