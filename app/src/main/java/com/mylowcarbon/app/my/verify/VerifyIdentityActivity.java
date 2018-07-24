package com.mylowcarbon.app.my.verify;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityVerifyIdentityBinding;

import java.util.List;

/**
 * 验证身份
 */
public class VerifyIdentityActivity extends ActionBarActivity
        implements VerifyIdentityContract.View {

    private static final String TAG = "VerifyIdentityActivity";


    public static void intentTo(@NonNull Activity activity,
                                int requestCode) {
        Intent intent = new Intent(activity, VerifyIdentityActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    private VerifyIdentityContract.Presenter mPresenter;
    private ActivityVerifyIdentityBinding mBinding;
    private ProblemAdapter mProblemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_verify_identity);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        mProblemAdapter = new ProblemAdapter(LayoutInflater.from(this));
        mBinding.problemView.setAdapter(mProblemAdapter);

        new VerifyIdentityPresenter(this);
        mPresenter.loadProblem();
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_verify_identity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(VerifyIdentityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void switchToProblem() {
        //通过密保验证
        mBinding.phone.setVisibility(View.GONE);
        mBinding.questions.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchToPhone() {
        //通过手机号验证
        mBinding.phone.setVisibility(View.VISIBLE);
        mBinding.questions.setVisibility(View.GONE);
    }

    @Override
    public void commit() {
        //验证
        Object selectedItem = mBinding.problemView.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        int problemId = ((Problem)selectedItem).getId();
        CharSequence answer = mBinding.answer.getText();
        mPresenter.checkProblem(problemId, answer);
    }

    @Override
    public void onLoadProblemStart() {
        showLoadingDialog();
    }

    @Override
    public void onLoadProblemSuccess(List<Problem> problems) {
        mProblemAdapter.setData(problems);
    }

    @Override
    public void onLoadProblemFail(int errorCode) {
        showCode(errorCode);
    }

    @Override
    public void onLoadProblemError(Throwable error) {
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onLoadProblemCompleted() {
        dismissLoadingDialog();
    }

    @Override
    public void onCheckProblemStart() {
        showLoadingDialog();
    }

    @Override
    public void onCheckProblemSuccess() {
        setResult(RESULT_OK);
        dismissLoadingDialog();
        finish();
    }

    @Override
    public void onCheckProblemFail(int errorCode) {
        showCode(errorCode);
    }

    @Override
    public void onCheckProblemError(Throwable error) {
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onCheckProblemCompleted() {
        dismissLoadingDialog();
    }
}
