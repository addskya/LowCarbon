package com.mylowcarbon.app.register.code;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;


/**
 * 手机号验证
 */
public class CodeVerifyActivity extends ActionBarActivity {

    private static final String EXTRA_MOBILE =
            "com.mylowcarbon.app.EXTRA_MOBILE";

    private static final String EXTRA_SUMMARY =
            "com.mylowcarbon.app.EXTRA_TITLE";

    private static final String EXTRA_REGISTER =
            "com.mylowcarbon.app.EXTRA_REGISTER";

    /**
     * 以startActivityForResult方式启用验证码
     *
     * @param activity    host activity
     * @param mobile      the mobile
     * @param summary     the fragment title
     * @param requestCode the requestCode
     */
    public static void intentTo(@NonNull Activity activity,
                                @NonNull CharSequence mobile,
                                @Nullable CharSequence summary,
                                int requestCode) {
        Intent intent = new Intent(activity, CodeVerifyActivity.class);
        intent.putExtra(EXTRA_MOBILE, mobile);
        intent.putExtra(EXTRA_SUMMARY, summary);
        intent.putExtra(EXTRA_REGISTER, false);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_verify_code;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verify);

        Intent lunchIntent = getIntent();
        CharSequence summary = lunchIntent.getCharSequenceExtra(EXTRA_SUMMARY);
        CharSequence mobile = lunchIntent.getCharSequenceExtra(EXTRA_MOBILE);
        boolean isRegister = lunchIntent.getBooleanExtra(EXTRA_REGISTER, false);

        Fragment fragment = CodeVerifyFragment.getArgsFragment(mobile, summary, isRegister);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, fragment, null)
                .commit();
    }
}
