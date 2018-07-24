package com.mylowcarbon.app;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mylowcarbon.app.ui.LoadingDialog;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * <p>
 * 可以显示一个带加载动画的Fragment,单独提取出来是为了减少
 * LoadingDialog显示及隐藏的重复代码
 */

public abstract class BaseLoadingFragment extends BaseFragment {

    private BaseDialog mLoadingDialog;


    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
    }

    /**
     * 显示"正在加载"对话框
     */
    protected void showLoadingDialog() {
        dismissLoadingDialog();
        mLoadingDialog = new LoadingDialog(getContext());
        mLoadingDialog.show();
    }

    /**
     * 隐藏"正在加载"对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 根据错误码显示提示,目前暂定用Toast,后续UI统一
     *
     * @param errorCode 服务器返回的错误码
     */
    protected void showErrorCode(int errorCode) {
        Toast.makeText(getContext(), "errorCode:" + errorCode, Toast.LENGTH_SHORT).show();
    }

    /**
     * 统一处理异常提示
     *
     * @param error the error
     */
    protected void showError(@Nullable Throwable error) {
        Toast.makeText(getContext(), "error:" + error, Toast.LENGTH_SHORT).show();
    }
}
