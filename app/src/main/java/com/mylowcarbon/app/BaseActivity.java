package com.mylowcarbon.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.mylowcarbon.app.ui.LoadingDialog;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 * 基Activity
 */

public abstract class BaseActivity extends PromptActivity {

    private static final String TAG = "BaseActivity";

    /**
     * Whether or NOT the Activity has finished.
     *
     * @return true if the Activity is NOT finish
     */
    public boolean isAdded() {
        return !isFinishing();
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
