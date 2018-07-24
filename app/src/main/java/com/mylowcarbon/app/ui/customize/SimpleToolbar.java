package com.mylowcarbon.app.ui.customize;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.utils.LogUtil;


public class SimpleToolbar extends Toolbar {

    private static final String TAG = "SimpleToolbar";

    /**
     * 中间Title
     */
    private TextView mTitle;
    /**
     * 右侧Title
     */
    private TextView mMore;

    public SimpleToolbar(@NonNull Context context) {
        this(context, null);
    }

    public SimpleToolbar(@NonNull Context context,
                         @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleToolbar(@NonNull Context context,
                         @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = findViewById(R.id.title);
        mMore = findViewById(R.id.more);
    }

    @Override
    public void setTitle(int resId) {
        //Nothing
    }

    @Override
    public void setTitle(CharSequence title) {
        //Nothing
    }

    public void setActionBarTitle(@StringRes int resId) {
        LogUtil.i(TAG, "setTitle:" + resId);
        if (resId <= 0) {
            return;
        }
        setActionBarTitle(getResources().getString(resId));
    }

    public void setActionBarTitle(CharSequence title) {
        LogUtil.i(TAG, "setTitle:" + title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    //设置中间title的内容文字的颜色
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setTitleColor(ColorStateList colors) {
        mTitle.setTextColor(colors);
    }

    public void setOperateText(@StringRes int resId) {
        if (resId <= 0) {
            return;
        }
        setOperateText(getResources().getString(resId));
    }

    public void setOperateText(String text) {

        mMore.setVisibility(View.VISIBLE);
        mMore.setText(text);
    }

    //设置title右边文字颜色
    public void setOperateColor(int color) {
        mMore.setTextColor(color);
    }

    public void setOperateColor(ColorStateList colors) {
        mMore.setTextColor(colors);
    }

    //设置title右边图标
    public void setOperateIcon(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mMore.setCompoundDrawables(null, null, dwRight, null);
    }

    //设置title右边点击事件
    public void setOperateAction(@Nullable OnClickListener onClickListener) {
        mMore.setOnClickListener(onClickListener);
    }
}
