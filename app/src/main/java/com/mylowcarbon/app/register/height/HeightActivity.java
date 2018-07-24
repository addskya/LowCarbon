package com.mylowcarbon.app.register.height;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.AbstractSettingsActivity;
import com.mylowcarbon.app.register.gender.GenderActivity;


/**
 * Created by Orange on 18-4-10.
 * Email:addskya@163.com
 */
public class HeightActivity extends AbstractSettingsActivity {


    /**
     * 显示修改昵称UI
     *
     * @param activity    the Host activity
     * @param userInfo    the origin userInfo
     * @param requestCode the requestCode
     */
    public static void intentTo(@NonNull Activity activity,
                                @NonNull UserInfo userInfo,
                                int requestCode) {
        Intent intent = new Intent(activity, HeightActivity.class);
        packArgs(intent, userInfo);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        UserInfo userInfo = getArgsUserInfo();
        Fragment fragment = HeightFragment.getArgsFragment(userInfo, true);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, fragment, null)
                .commit();
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_height;
    }
}
