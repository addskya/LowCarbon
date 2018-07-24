package com.mylowcarbon.app;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.mylowcarbon.app.ui.LoadingDialog;
import com.mylowcarbon.app.ui.StatusDialog;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 * 最牛逼的提示类
 * 用于:
 * 提示服务器的失败码
 * 提示消息
 * 提示网络错误信息
 * 没有了.
 */
public abstract class PromptActivity extends PermissionsActivity {

    private static final String TAG = "PromptActivity";

    protected void showError(Throwable error) {
        showPrompt("Error:" + error);
    }

    protected void showCode(int errorCode) {
        // showPrompt("errorCode:" + errorCode);
        String message = ErrorMapper.getErrorMessage(this, errorCode);
        showPrompt(message);
    }

    protected void toastMessage(@StringRes int message) {
        showPrompt(getString(message));
    }

    private void showPrompt(@Nullable CharSequence message) {
        LogUtil.i(TAG, "showPrompt:" + message);
        if (TextUtils.isEmpty(message)) {
            return;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private BaseDialog mLoadingDialog;

    /**
     * 显示一个转圈的等待对话框
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    /**
     * 显示一个转圈及文字说明的对话框
     *
     * @param message 文字说明
     */
    protected void showLoadingDialog(@StringRes int message) {
        showLoadingDialog(getString(message));
    }

    /**
     * 显示一个转圈及文字说明的对话框
     *
     * @param message 文字说明
     */
    protected void showLoadingDialog(@Nullable CharSequence message) {
        if (mLoadingDialog instanceof StatusDialog) {
            ((StatusDialog) mLoadingDialog).updateStatusMessage(message);
        } else {
            dismissLoadingDialog();
            mLoadingDialog = new StatusDialog(this, message);
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏并销毁等待对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
    }
}
