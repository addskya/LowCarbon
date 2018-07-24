package com.mylowcarbon.app.forget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.mylowcarbon.app.BaseLoadingFragment;
import com.mylowcarbon.app.FragmentTransfer;

/**
 * Created by Orange on 18-3-30.
 * Email:addskya@163.com
 */

public class ForgetPasswordBaseFragment extends BaseLoadingFragment {

    @Override
    public final View initView() {
        return null;
    }

    @Override
    public final void initData() {

    }

    @Override
    public final void initEvent() {

    }

    protected static final String EXTRA_MOBILE =
            "com.mylowcarbon.app.EXTRA_MOBILE";

    /**
     * 读取参数中的mobile字段
     *
     * @param args 启动参数
     * @return mobile
     */
    protected CharSequence getArgsMobile(@Nullable Bundle args) {
        CharSequence mobile = args != null ? args.getCharSequence(EXTRA_MOBILE) : null;
        if (TextUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("Can't lunch the Fragment with Empty mobile.");
        }
        return mobile;
    }

    /**
     * 参数打包
     *
     * @param mobile 当前使用的手机号
     * @return 启动参数
     */
    protected static Bundle getArgsBundle(@NonNull CharSequence mobile) {
        Bundle args = new Bundle(1);
        args.putCharSequence(EXTRA_MOBILE, mobile);
        return args;
    }

    protected final void translateTo(@NonNull String tag,
                                     @Nullable Bundle args) {
        ((FragmentTransfer) getActivity()).onTranslateTo(tag, args);
    }

    protected final void finish() {
        ((FragmentTransfer) getActivity()).onFinish();
    }

    protected final void setTitle(@StringRes int resId) {
        ((FragmentTransfer) getActivity()).setUiTitle(resId);
    }
}
