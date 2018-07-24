package com.mylowcarbon.app.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mylowcarbon.app.R;

/**
 * Created by Orange on 18-3-15.
 * Email:addskya@163.com
 * 密码输入View,带右边的眼睛按钮
 */

public class PasswordView extends FrameLayout {
    private static final String TAG = "PasswordView";

    private EditText mEditText;

    public PasswordView(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public PasswordView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
        initAttrs(context, attrs);
    }

    public PasswordView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
        initAttrs(context, attrs);
    }

    private void initViews(@NonNull Context context) {
        View.inflate(context, R.layout.password_view, this);
        mEditText = findViewById(R.id.password);
        CheckBox visible = findViewById(R.id.password_visible);
        if (visible != null) {
            visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    toggle(isChecked);
                }
            });
            // 与Check状态同步
            toggle(visible.isChecked());
        }
    }

    private void initAttrs(@NonNull Context context,
                           @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
        if (array.hasValue(R.styleable.PasswordView_hint)) {
            String hint = array.getString(R.styleable.PasswordView_hint);
            mEditText.setHint(hint);
        }
        array.recycle();
    }

    /**
     * 获取输入的密码文本
     *
     * @return 输入的密码文本
     */
    public CharSequence getText() {
        return mEditText == null ? "" : mEditText.getText();
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (mEditText != null) {
            mEditText.addTextChangedListener(textWatcher);
        }
    }

    private void toggle(boolean checked) {
        if (mEditText == null) {
            return;
        }
        mEditText.setInputType(
                InputType.TYPE_CLASS_TEXT | (checked ?
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                        InputType.TYPE_TEXT_VARIATION_PASSWORD));

        // 将光标移到文本最后
        int length = mEditText.getText().length();
        mEditText.setSelection(length);
    }
}
