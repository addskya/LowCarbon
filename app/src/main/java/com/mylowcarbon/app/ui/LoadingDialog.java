package com.mylowcarbon.app.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;

/**
 * Created by Orange on 18-3-13.
 * Email:addskya@163.com
 */

public class LoadingDialog extends BaseDialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_loading,
                null, false);
    }
}
