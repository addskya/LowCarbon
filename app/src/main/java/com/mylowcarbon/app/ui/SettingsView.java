package com.mylowcarbon.app.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylowcarbon.app.R;
/* 使用实例
<com.mylowcarbon.app.ui.SettingsView
    style="@style/SettingsView"
    android:onClick="@{() -> view.onViewClick(3)}"
    app:name="绑定电子邮件" />
*/

/**
 * Created by Orange on 18-4-14.
 * Email:addskya@163.com
 */
public class SettingsView extends RelativeLayout {

    public SettingsView(@NonNull Context context) {
        this(context, null);
    }

    public SettingsView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingsView);
        if (array.hasValue(R.styleable.SettingsView_name)) {
            setName(array.getString(R.styleable.SettingsView_name));
        }

        setThumb(array.getDrawable(R.styleable.SettingsView_thumb));

        if (array.hasValue(R.styleable.SettingsView_arrow)) {
            setArrowIconVisible(array.getBoolean(R.styleable.SettingsView_arrow, true));
        }
        array.recycle();
    }

    private ImageView mThumbView;
    private TextView mSettingsName;
    private View mArrowIcon;

    private void initViews() {
        View view = View.inflate(getContext(), getSettingsViewLayout(), this);
        initView(view);
    }

    protected int getSettingsViewLayout() {
        return R.layout.settings_view;
    }

    protected void initView(View view) {
        mThumbView = view.findViewById(R.id.thumb);
        mSettingsName = view.findViewById(R.id.settings_name);
        mArrowIcon = view.findViewById(R.id.settings_arrow);
    }

    public void setName(@Nullable CharSequence name) {
        if (mSettingsName != null) {
            mSettingsName.setText(name);
        }
    }

    public void setThumb(@Nullable Drawable icon) {
        if (mThumbView != null) {
            mThumbView.setImageDrawable(icon);
            mThumbView.setVisibility(icon == null ? GONE : VISIBLE);
        }
    }

    public void setArrowIconVisible(boolean visible) {
        if (mArrowIcon != null) {
            mArrowIcon.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}
