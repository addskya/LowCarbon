package com.mylowcarbon.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mylowcarbon.app.ui.LoadingDialog;
import com.mylowcarbon.app.utils.LogUtil;
/**
 * 基类fragment
 * @Author: Echo
 * @Date: 18-3-1.
 */
public abstract class BaseFragment extends Fragment {

    public FragmentActivity mActivity;
    private BaseDialog mLoadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 子类实现此抽象方法返回View进行展示
     *
     * @return
     */
    public abstract View initView();

    /**
     * 子类在此方法中实现数据的初始化
     */
    public  abstract void initData() ;

    /**
     * 子类可以复写此方法初始化事件
     */
    public abstract void initEvent() ;



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
