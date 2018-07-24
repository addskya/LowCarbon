package com.mylowcarbon.app.mine.walk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.sport.SportDataSource;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 */

public abstract class SportView extends LinearLayout {

    public SportView(@NonNull Context context) {
        super(context);
        inflateView(context);
    }

    public SportView(@NonNull Context context,
                     @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateView(context);
    }

    public SportView(@NonNull Context context,
                     @Nullable AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
    }

    private TextView mSportValue;
    private TextView mSportDesc;

    private void inflateView(@NonNull Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);

        inflater.inflate(R.layout.item_sport_view, this, true);
        ImageView sportIcon = findViewById(R.id.sport_icon);
        mSportValue = findViewById(R.id.sport_value);
        mSportDesc = findViewById(R.id.sport_desc);

        sportIcon.setImageResource(getSportIcon());
        mSportDesc.setVisibility(isSportDescVisible() ? VISIBLE : GONE);
        mSportDesc.setText(getSportDesc());
    }

    @DrawableRes
    protected abstract int getSportIcon();

    @StringRes
    protected abstract int getSportDesc();

    protected void setSportValue(@NonNull CharSequence text) {
        mSportValue.setText(text);
    }

    protected void setSportDesc(@NonNull CharSequence desc) {
        mSportDesc.setText(desc);
    }

    protected abstract boolean isSportDescVisible();

    private SharedPreferences.OnSharedPreferenceChangeListener mOnChangedListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    onPreferenceChanged(sharedPreferences, key);
                }
            };

    protected void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    protected SharedPreferences mPreferenceFile;

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

    protected SportDataSource mSource;

    public void setSportDataSource(SportDataSource source) {
        mSource = source;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int index = 0; index < childCount; index++) {
                getChildAt(index).setEnabled(enabled);
            }
        }

        setAlpha(enabled ? 1.0F : 0.5F);
    }
}
