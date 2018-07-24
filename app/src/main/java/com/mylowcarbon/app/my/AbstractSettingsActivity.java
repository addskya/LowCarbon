package com.mylowcarbon.app.my;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.model.UserInfo;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

public abstract class AbstractSettingsActivity extends ActionBarActivity {

    private static final String EXTRA_USER_INFO =
            "com.mylowcarbon.app.EXTRA_USER_INFO";

    @NonNull
    protected static Intent packArgs(@NonNull Intent base,
                                     @Nullable UserInfo userInfo) {
        base.putExtra(EXTRA_USER_INFO, userInfo);
        return base;
    }

    @Nullable
    protected UserInfo getArgsUserInfo() {
        Intent lunchIntent = getIntent();
        if (lunchIntent != null && lunchIntent.hasExtra(EXTRA_USER_INFO)) {
            return lunchIntent.getParcelableExtra(EXTRA_USER_INFO);
        }
        return null;
    }
}
