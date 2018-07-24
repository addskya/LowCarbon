package com.mylowcarbon.app.login;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mylowcarbon.app.R;

import java.util.Random;

/**
 * Created by Orange on 18-3-13.
 * Email:addskya@163.com
 * 显示及产生验证码View
 * 自己处理用户点击刷新验证码事件
 */

public class VerifyCodeView extends AppCompatTextView {

    private static final String TAG = "VerifyCodeView";

    public VerifyCodeView(@NonNull Context context) {
        super(context);
    }

    public VerifyCodeView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerifyCodeView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private CharSequence mVerifyCode;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshVerifyCode();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshVerifyCode();
    }

    /**
     * 重新产生一个验证码
     */
    private void refreshVerifyCode() {
        Resources res = getResources();
        String verifyCodeCharset = res.getString(R.string.verify_code_charset);
        int verifyCodeLength = res.getInteger(R.integer.verify_code_length);
        StringBuilder builder = new StringBuilder(verifyCodeLength);
        Random random = new Random();
        for (int index = 0; index < verifyCodeLength; ++index) {
            builder.append(verifyCodeCharset.charAt(
                    random.nextInt(verifyCodeCharset.length())));
        }

        mVerifyCode = builder.toString();

        // 验证码已经产生, 开始着色处理
        setText(mVerifyCode);
    }

    /**
     * 验证输入的验证码是否与显示的一致
     *
     * @param code 输入的验证码
     * @return true, 如果输入与实际显示一致,
     */
    public boolean verifyCode(@Nullable CharSequence code) {
        return TextUtils.equals(mVerifyCode, code);
    }

//    @Override
//    public final CharSequence getText() {
//        throw new UnsupportedOperationException("Can't getText from VerifyCodeView");
//    }
//
//    @Override
//    public final Editable getEditableText() {
//        throw new UnsupportedOperationException("Can't getEditableText from VerifyCodeView");
//    }
}
