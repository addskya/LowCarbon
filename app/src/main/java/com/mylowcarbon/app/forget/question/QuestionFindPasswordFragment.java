package com.mylowcarbon.app.forget.question;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentQuestionFindPasswordBinding;
import com.mylowcarbon.app.forget.ForgetPasswordBaseFragment;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * 通过问题找回密码界面
 */

public class QuestionFindPasswordFragment extends ForgetPasswordBaseFragment
        implements QuestionFindPasswordContract.View {

    private static final String TAG = "QuestionFindPassword";
    private FragmentQuestionFindPasswordBinding mBinding;
    private QuestionFindPasswordContract.Presenter mPresenter;

    private QuestionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentQuestionFindPasswordBinding.inflate(inflater, container, false);

        mAdapter = new QuestionAdapter(inflater);
        mBinding.questions.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new QuestionFindPasswordPresenter(this);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        // CharSequence mobile = getArgsMobile(getArguments());
        CharSequence mobile = "123456789";
        mPresenter.loadQuestions(mobile);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.text_question);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(QuestionFindPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadQuestionStart() {
        LogUtil.i(TAG, "onLoadQuestionStart");
        showLoadingDialog();
    }

    @Override
    public void onLoadQuestionSuccess(@Nullable List<Question> questions) {
        LogUtil.i(TAG, "onLoadQuestionSuccess");
        mAdapter.setData(questions);
    }

    @Override
    public void onLoadQuestionFail(int errorCode) {
        LogUtil.i(TAG, "onLoadQuestionFail,errorCode:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onLoadQuestionError(Throwable error) {
        Log.e(TAG, "onLoadQuestionError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onLoadQuestionCompleted() {
        LogUtil.i(TAG, "onLoadQuestionCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void onVerifyQuestionStart() {
        LogUtil.i(TAG, "onVerifyQuestionStart");
        showLoadingDialog();
    }

    @Override
    public void onVerifyQuestionSuccess(@NonNull CharSequence mobile,
                                        @Nullable QuestionResp resp) {
        LogUtil.i(TAG, "onVerifyQuestionSuccess");
        dismissLoadingDialog();
        Bundle args = getArgsBundle(mobile);
        translateTo("ResetPasswordFragment", args);
    }

    @Override
    public void onVerifyQuestionFail(int errorCode) {
        LogUtil.w(TAG, "onVerifyQuestionFail,errorCode:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onVerifyQuestionError(Throwable error) {
        LogUtil.e(TAG, "onVerifyQuestionError", error);
        dismissLoadingDialog();
    }

    @Override
    public void onVerifyQuestionCompleted() {
        LogUtil.i(TAG, "onVerifyQuestionCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void verifyQuestion() {
        LogUtil.i(TAG, "verifyQuestion");
        CharSequence mobile = mBinding.mobile.getText();
        long questionId = mBinding.questions.getSelectedItemId();
        CharSequence answer = mBinding.answer.getText();
        mPresenter.verifyQuestion(mobile, (int) questionId, answer);
    }

    @Override
    public void findPasswordByAccount() {
        translateTo("AccountFindPasswordFragment", null);
    }
}
