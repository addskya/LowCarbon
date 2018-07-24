package com.mylowcarbon.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;


/**
 * Created by Orange on 18-03-13
 * Email:addskya@163.com
 * 此对话框显示两个按钮 + 标题 + 消息内容
 */

public class ConfirmDialog extends BaseDialog {

    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Display a Dialog with Title & Message
     *
     * @param context the dialog context
     * @param title   the Dialog title
     * @param message the Dialog message content
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence title,
                                  @Nullable CharSequence message) {
        return intentTo(context, title, message, null);
    }

    /**
     * Display a Dialog with Title & Message & the Button Listener
     *
     * @param context  the dialog context
     * @param title    the Dialog title
     * @param message  the Dialog message content
     * @param listener the positive listener when the Positive Button Clicked
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence title,
                                  @Nullable CharSequence message,
                                  @Nullable DialogInterface.OnClickListener listener) {
        return intentTo(context, title, message, null, null, listener);
    }

    /**
     * Display a Dialog with Title & Message & the Button Listener
     *
     * @param context      the dialog context
     * @param title        the Dialog title
     * @param message      the Dialog message content
     * @param positiveText the Positive Button Text
     * @param negativeText the Negative Button Text
     * @param listener     the positive listener when the Positive Button Clicked
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence title,
                                  @Nullable CharSequence message,
                                  @Nullable CharSequence positiveText,
                                  @Nullable CharSequence negativeText,
                                  @Nullable DialogInterface.OnClickListener listener) {
        ConfirmDialog dialog = new ConfirmDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveText(positiveText);
        dialog.setNegativeText(negativeText);
        dialog.setOnClickListener(listener);
        dialog.show();
        return dialog;
    }

    private CharSequence mTitle;
    private CharSequence mMessage;
    private CharSequence mPositiveText;
    private CharSequence mNegativeText;
    private DialogInterface.OnClickListener onClickListener;

    private TextView mTitleView;
    private TextView mMessageView;
    private TextView mPositiveButton;
    private TextView mNegativeButton;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_confirm, null, false);
        initViews(view);
        return view;
    }

    @Override
    protected int getWindowAnimation() {
        return 0;
    }

    private void initViews(@Nullable View view) {
        if (view == null) {
            return;
        }
        mTitleView = view.findViewById(R.id.title);
        mMessageView = view.findViewById(R.id.message);
        mPositiveButton = view.findViewById(R.id.positive);
        mNegativeButton = view.findViewById(R.id.negative);

        bindView(mTitleView, mTitle);
        bindView(mMessageView, mMessage);
        bindView(mPositiveButton, mPositiveText);
        bindView(mNegativeButton, mNegativeText);

        bindListener(mPositiveButton, onClickListener);
        bindListener(mNegativeButton, onClickListener);
    }

    /**
     * Set the Dialog title
     *
     * @param title the dialog title
     */
    @Override
    public final void setTitle(@Nullable CharSequence title) {
        mTitle = title;
        bindView(mTitleView, title);
    }

    /**
     * Set the Dialog message
     *
     * @param message the dialog message content
     */
    public void setMessage(@Nullable CharSequence message) {
        mMessage = message;
        bindView(mMessageView, message);
    }

    /**
     * set the positive button text
     *
     * @param positiveText the positive button text
     */
    public void setPositiveText(@Nullable CharSequence positiveText) {
        mPositiveText = positiveText;
        bindView(mPositiveButton, positiveText);
    }

    /**
     * set the negative button text
     *
     * @param negativeText the negative button text
     */
    public void setNegativeText(@Nullable CharSequence negativeText) {
        mNegativeText = negativeText;
        bindView(mNegativeButton, negativeText);
    }

    /**
     * set the positive button listener
     *
     * @param listener the positive button listener
     */
    public void setOnClickListener(@Nullable DialogInterface.OnClickListener listener) {
        onClickListener = listener;
        bindListener(mPositiveButton, listener);
        bindListener(mNegativeButton, listener);
    }

    /**
     * bind the text into view
     *
     * @param view the view which will set text
     * @param text the text content
     */
    private void bindView(@Nullable TextView view,
                          @Nullable CharSequence text) {
        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(View.GONE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        view.setText(text);
    }

    private void bindListener(@Nullable final View view,
                              @Nullable final DialogInterface.OnClickListener listener) {
        if (view == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int whichId = view == mPositiveButton ?
                        DialogInterface.BUTTON_POSITIVE : DialogInterface.BUTTON_NEGATIVE;
                if (listener != null) {
                    listener.onClick(ConfirmDialog.this, whichId);
                }
                dismiss();
            }
        });
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}
