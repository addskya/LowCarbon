package com.mylowcarbon.app.mine.ride;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.mine.walk.SportView;
import com.mylowcarbon.app.sport.SportDataSource;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 * 骑行记录
 */

public class RideView extends SportView {

    private static final String TAG = "WalkView";

    public RideView(@NonNull Context context) {
        super(context);
    }

    public RideView(@NonNull Context context,
                    @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RideView(@NonNull Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSportIcon() {
        return R.drawable.ic_ride_light;
    }

    @Override
    protected int getSportDesc() {
        return R.string.text_ride_desc;
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

        if (key.equalsIgnoreCase(mSource.getRideKey())) {
            refreshSportValue(key);
        }
    }

    private void refreshSportValue(@Nullable String key) {
        int value = 0;
        if (TextUtils.isEmpty(key)) {
            // what's up!
        } else if (mPreferenceFile == null) {
            // what's up!
        } else {
            value = (int)mPreferenceFile.getFloat(key, 0F);
        }

        setSportValue(
                getResources().getString(R.string.format_ride_value, (int) value)
        );
    }

    @Override
    public void setSportDataSource(@Nullable SportDataSource source) {
        super.setSportDataSource(source);

        refreshSportValue(source != null ? source.getRideKey() : null);
        setEnabled(source != null && source.isSupportBicycle());
    }

}
