package com.mylowcarbon.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.mylowcarbon.app.ui.customize.SimpleToolbar;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-3-15.
 * Email:addskya@163.com
 * <p>
 * 带有自定义标题栏风格的Activity.
 */

public abstract class ActionBarActivity extends BaseActivity {

    private static final String TAG = "ActionBarActivity";
    private SimpleToolbar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        LogUtil.i(TAG, "onContentChanged");
        mActionBar = findViewById(R.id.toolbar);

        if (mActionBar == null) {
//            throw new IllegalStateException("You must include the action_bar view.");
            return;
        }

        setSupportActionBar(mActionBar);
        int titleId = getUiTitle();
        setUiTitle(titleId);
        setOperateText(getOperateText());
        setOperateOnClickListener(getOperateOnClickListener());
    }

    /**
     * 返回当前Activity的标题
     *
     * @return 当前Activity的标题
     */
    protected abstract int getUiTitle();

    /**
     * 设置当前Activity标题
     *
     * @param resId res id.
     */
    public void setUiTitle(@StringRes int resId) {

        LogUtil.i(TAG, "setUiTitle:" + resId);
        if (mActionBar != null) {
            mActionBar.setActionBarTitle(resId);
        }
    }

    /**
     * 设置当前Activity标题
     *
     * @param text string.
     */
    public void setUiTitle(@Nullable String text) {

        LogUtil.i(TAG, "setUiTitle:" + text);
        if (mActionBar != null) {
            mActionBar.setActionBarTitle(text);
        }
    }

    /**
     * 返回当前Activity标题上右边按钮文本
     *
     * @return 当前Activity标题上右边按钮文本
     */
    protected int getOperateText() {
        return 0;
    }

    /**
     * 设置当前Activity右侧按钮文本
     *
     * @param resId res id
     */
    protected void setOperateText(@StringRes int resId) {
        if (mActionBar != null) {
            mActionBar.setOperateText(resId);
        }
    }

    /**
     * 返回当前Activity右边按钮点击事件
     *
     * @return 当前Activity右边按钮点击事件
     */
    @Nullable
    protected View.OnClickListener getOperateOnClickListener() {
        return null;
    }

    /**
     * 设置当前Activity右侧按钮事件
     *
     * @param listener OnClickListener
     */
    protected void setOperateOnClickListener(@Nullable View.OnClickListener listener) {
        if (mActionBar != null) {
            mActionBar.setOperateAction(listener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
