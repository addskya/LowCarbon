package com.mylowcarbon.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;


/**
 * Created by Orange on 18-03-13
 * Email:addskya@163.com
 * 此对话框显示一个按钮 + 消息内容
 */
public class OkDialog extends BaseDialog {

    public OkDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Display a Ok Dialog,
     *
     * @param context     the dialog context
     * @param message     the message content
     * @param neutralText the button text
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence message,
                                  @Nullable CharSequence neutralText) {
        return intentTo(context, message, neutralText, null);
    }


    /**
     * Display a Ok Dialog,
     *
     * @param context     the dialog context
     * @param message     the message content
     * @param neutralText the button text
     * @param listener    the button listener
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence message,
                                  @Nullable CharSequence neutralText,
                                  @Nullable DialogInterface.OnClickListener listener) {
        OkDialog dialog = new OkDialog(context);
        dialog.setMessage(message);
        dialog.setNeutralText(neutralText);
        dialog.setOnClickListener(listener);
        dialog.show();
        return dialog;
    }
    /**
     * Display a Ok Dialog with Image,
     *
     * @param context     the dialog context
     * @param message     the message content
     * @param neutralText the button text
     * @param listener    the button listener
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @Nullable CharSequence message,
                                  @Nullable int drawableRes,
                                  @Nullable CharSequence neutralText,
                                  @Nullable DialogInterface.OnClickListener listener) {
        Log.e("test","****************** 0 ");
        OkDialog dialog = new OkDialog(context);
        dialog.setMessage(message);
        dialog.setImageRresource(drawableRes);
        dialog.setNeutralText(neutralText);
        dialog.setOnClickListener(listener);
        dialog.show();
        return dialog;
    }

    private TextView mMessageView;
    private TextView mNeutralButton;

    private ImageView mImageView;

    private CharSequence mMessage;
    private int mDrawableRes;

    private CharSequence mNeutralText;

    private DialogInterface.OnClickListener mOnClickListener;

    @Override
    protected View onCreateView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.dialog_ok, null, false);
        initViews(view);
        return view;
    }

    private void initViews(@Nullable View view) {

        if (view == null) {
            return;
        }

        mMessageView = view.findViewById(R.id.message);
        mNeutralButton = view.findViewById(R.id.neutral);
        mImageView = view.findViewById(R.id.image);

        bindView(mMessageView, mMessage);
        bindView(mNeutralButton, mNeutralText);
        bindView(mImageView, mDrawableRes);

        bindListener(mNeutralButton, mOnClickListener);
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

    public void setNeutralText(@Nullable CharSequence neutralText) {
        mNeutralText = neutralText;
        bindView(mNeutralButton, neutralText);
    }

    public void setImageRresource(@Nullable int drawableRes) {

        mDrawableRes = drawableRes;
        bindView(mImageView, drawableRes);
    }

    /**
     * set the positive button listener
     *
     * @param listener the positive button listener
     */
    public void setOnClickListener(@Nullable DialogInterface.OnClickListener listener) {
        mOnClickListener = listener;
        bindListener(mNeutralButton, listener);
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
        if (text == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        view.setText(text);
    }
    /**
     * bind the res into view
     *
     *
     */
    private void bindView(@Nullable ImageView view,
                          @Nullable int res) {

        if (view == null) {
            return;
        }

        if (res == 0) {
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setImageResource(res);
    }

    private void bindListener(@Nullable final View view,
                              @Nullable final DialogInterface.OnClickListener listener) {
        if (view == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(OkDialog.this, DialogInterface.BUTTON_NEUTRAL);
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
