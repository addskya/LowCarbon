package com.mylowcarbon.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * 评价弹框
 */
public class AppraiseDialog extends BaseDialog {
    private View mView;
    private CharSequence mTitle;
    private TextView mTitleView;
    private AppraiseDialogOnClickListener onClickListener;
    private CharSequence mPositiveText;
    private CharSequence mNegativeText;
    private TextView mPositiveButton;
    private TextView mNegativeButton;
    private RadioGroup mcommentRg;
    private EditText mRemarkEt;

    public AppraiseDialog(@NonNull Context context) {
        super(context);
    }


    public static Dialog intentTo(@NonNull Context context, @Nullable AppraiseDialogOnClickListener listener) {
        AppraiseDialog dialog = new AppraiseDialog(context);
        dialog.setOnClickListener(listener);
        dialog.show();
        return dialog;
    }


    @Override
    protected View onCreateView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.dialog_appraise, null, false);
        initViews(mView);
        return mView;
    }

    private void initViews(@Nullable View view) {
        if (view == null) {
            return;
        }
        mTitleView = view.findViewById(R.id.title);
        mPositiveButton = view.findViewById(R.id.positive);
        mNegativeButton = view.findViewById(R.id.negative);
        mcommentRg = view.findViewById(R.id.comment);
        mRemarkEt = view.findViewById(R.id.remark);
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
    public void setOnClickListener(@Nullable AppraiseDialogOnClickListener listener) {
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
                              @Nullable final AppraiseDialogOnClickListener listener) {
         if (view == null) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int whichId = view == mPositiveButton ?
                        DialogInterface.BUTTON_POSITIVE : DialogInterface.BUTTON_NEGATIVE;
                int comment_type = 1 ;
                 switch (mcommentRg.getCheckedRadioButtonId()) {

                    case R.id.comment_type1://
                        comment_type = 1 ;
                        break;
                    case R.id.comment_type2://
                        comment_type = 2 ;
                        break;
                    case R.id.comment_type3://
                        comment_type = 3 ;
                        break;
                    default:
                        break;
                }
                String remark = mRemarkEt.getText().toString();
                if (listener != null) {
                    listener.onClick(AppraiseDialog.this, whichId ,comment_type,remark);
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

    public interface AppraiseDialogOnClickListener{
        void onClick(DialogInterface dialog, int which ,int type ,String remark);
    }
}
