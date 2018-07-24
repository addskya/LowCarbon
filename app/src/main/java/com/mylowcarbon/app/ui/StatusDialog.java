package com.mylowcarbon.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;


/**
 * Created by Orange on 18-3-31.
 * Email:addskya@163.com
 */

public class StatusDialog extends BaseDialog {

    public StatusDialog(@NonNull Context context,
                        @StringRes int resId) {
        this(context, context.getString(resId));
    }

    public StatusDialog(@NonNull Context context,
                        @Nullable final CharSequence message) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateStatusMessage(message);
            }
        });
    }

    private TextView mStatusView;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_status,
                null, false);
        mStatusView = view.findViewById(R.id.status);
        return view;
    }

    public void updateStatusMessage(int message) {
        mStatusView.setText(message);
        mStatusView.setVisibility(View.VISIBLE);
    }

    public void updateStatusMessage(@Nullable CharSequence message) {
        mStatusView.setText(message);
        mStatusView.setVisibility(View.VISIBLE);
    }
}
