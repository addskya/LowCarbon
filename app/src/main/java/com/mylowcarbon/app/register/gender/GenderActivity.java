package com.mylowcarbon.app.register.gender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.AbstractSettingsActivity;

/**
 * Created by Orange on 18-4-10.
 * Email:addskya@163.com
 */
public class GenderActivity extends AbstractSettingsActivity {

    private static final String TAG = "GenderActivity";


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
        Intent intent = new Intent(activity, GenderActivity.class);
        packArgs(intent, userInfo);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        UserInfo userInfo = getArgsUserInfo();
        Fragment fragment = GenderFragment.getArgsFragment(userInfo, true);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, fragment, TAG).commit();
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_gender;
    }
}
