package com.mylowcarbon.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Orange on 18-3-10.
 * Email:addskya@163.com
 */
public abstract class BaseDialog extends AppCompatDialog {

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.AppThemeDialog);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(getWindowAnimation());
            window.setGravity(getWindowGravity());
        }
        View view = onCreateView(LayoutInflater.from(getContext()));
        setContentView(view);
    }

    protected abstract View onCreateView(LayoutInflater inflater);

    /**
     * get the Dialog display Gravity
     *
     * @return Gravity
     */
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }

    protected int getWindowAnimation() {
        return 0;
    }

    /**
     * Whether or NOT the View is Visible for User,
     *
     * @return for Activity return !isFinish(),
     * for Fragment return isAdded(),
     * for Dialog return isShowing()
     */
    public final boolean isAdded() {
        return isShowing();
    }

    protected final Context getBaseContext() {
        Context context = getContext();
        if (context instanceof ContextThemeWrapper) {
            return ((ContextThemeWrapper) context).getBaseContext();
        } else if (context instanceof ContextWrapper) {
            return ((ContextWrapper) context).getBaseContext();
        }
        return context;
    }


}
