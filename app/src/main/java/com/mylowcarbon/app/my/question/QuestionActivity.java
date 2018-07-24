package com.mylowcarbon.app.my.question;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.SparseArray;
import android.view.LayoutInflater;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityQuestionBinding;
import com.mylowcarbon.app.my.verify.VerifyIdentityActivity;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 密保问题
 * 设置密保界面,如果用户之前设置密保,则需要先验证身份
 */
public class QuestionActivity extends ActionBarActivity
        implements QuestionContract.View {
    private static final String TAG = "QuestionActivity";
    private static final int REQUEST_CODE_IDENTITY = 0x60;

    public static void intentTo(@NonNull Context context) {
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }

    private QuestionContract.Presenter mPresenter;
    private ActivityQuestionBinding mBinding;
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_question);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        LayoutInflater inflater = getLayoutInflater();
        mAdapter = new QuestionAdapter(inflater, this);
        mBinding.questions.setAdapter(mAdapter);

        new QuestionPresenter(this);
        mPresenter.verifyIdentity();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_question;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_IDENTITY: {
                if (resultCode == RESULT_OK) {
                    mPresenter.loadQuestions();
                } else {
                    finish();
                }
                break;
            }
            default: {
                LogUtil.w(TAG, "Unknown requestCode:" + requestCode);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(QuestionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private SparseArray<Editable> mAnswerMapArray = new SparseArray<>(1);

    @Override
    public void onAnswerChanged(@Nullable Question question,
                                @Nullable Editable editable) {
        LogUtil.i(TAG, "onAnswerChanged:" + editable);
        if (question == null) {
            return;
        }

        Integer key = question.getId();

        if (mAnswerMapArray.indexOfKey(key) >= 0) {
            mAnswerMapArray.remove(key);
        }
        mAnswerMapArray.put(key, editable);
    }

    @Override
    public void commit() {
        int size = mAnswerMapArray.size();
        if (size > 0) {
            List<Answer> answers = new ArrayList<>(1);
            for (int index = 0; index < size; index++) {
                Integer key = mAnswerMapArray.keyAt(index);
                Editable value = mAnswerMapArray.valueAt(index);
                Answer answer = new Answer();
                answer.setQuestionId(key);
                answer.setQuestionAnswer(value);
                answers.add(answer);
            }
            mPresenter.modifyAnswer(answers);
        }
    }

    @Override
    public void onVerifyIdentityStart() {
        LogUtil.i(TAG, "onVerifyIdentityStart");
        showLoadingDialog();
        mQuestionAnswers = null;
    }

    // 记下所有答案,避免再次请求
    private List<QuestionAnswer> mQuestionAnswers;

    @Override
    public void onVerifyIdentitySuccess(@Nullable List<QuestionAnswer> answers) {
        LogUtil.i(TAG, "onVerifyIdentitySuccess");
        if (answers != null && answers.size() > 0) {
            VerifyIdentityActivity.intentTo(this, REQUEST_CODE_IDENTITY);
        } else {
            mPresenter.loadQuestions();
        }
        mQuestionAnswers = answers;
    }

    @Override
    public void onVerifyIdentityError(Throwable error) {
        LogUtil.e(TAG, "onVerifyIdentityError", error);
        showError(error);
        dismissLoadingDialog();
        mQuestionAnswers = null;
    }

    @Override
    public void onVerifyIdentityCompleted() {
        LogUtil.i(TAG, "onVerifyIdentityCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void onLoadQuestionStart() {
        LogUtil.i(TAG, "onLoadQuestionStart");
        showLoadingDialog();
    }

    @Override
    public void onLoadQuestionSuccess(@Nullable List<Question> questions) {
        LogUtil.i(TAG, "onLoadQuestionSuccess");

        // 这是要做啥? 把答案与问题关联起来
        if (questions != null && questions.size() > 0 &&
                mQuestionAnswers != null && mQuestionAnswers.size() > 0) {
            for (Question q : questions) {
                for (QuestionAnswer answer : mQuestionAnswers) {
                    if (answer.getProblem_id() == q.getId()) {
                        q.setAnswer(answer.getAnswer());
                    }
                }
            }
        }

        mAdapter.setData(questions);
    }

    @Override
    public void onLoadQuestionFail(int errorCode) {
        LogUtil.w(TAG, "onLoadQuestionFail,errorCode:" + errorCode);
    }

    @Override
    public void onLoadQuestionError(Throwable error) {
        LogUtil.e(TAG, "onLoadQuestionError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onLoadQuestionCompleted() {
        LogUtil.i(TAG, "onLoadQuestionCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void onModifyAnswerStart() {
        LogUtil.i(TAG, "onModifyAnswerStart");
        showLoadingDialog();
    }

    // 修改密保问题是否成功,成功后需要退出当前的Activity
    private boolean mCommitSuccess;

    @Override
    public void onModifyAnswerSuccess() {
        LogUtil.i(TAG, "onModifyAnswerSuccess");
        mCommitSuccess = true;
    }

    @Override
    public void onModifyAnswerFail(int errorCode) {
        LogUtil.w(TAG, "onModifyAnswerFail,errorCode:" + errorCode);
        showCode(errorCode);
        mCommitSuccess = false;
    }

    @Override
    public void onModifyAnswerError(Throwable error) {
        LogUtil.e(TAG, "onModifyAnswerError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onModifyAnswerCompleted() {
        LogUtil.i(TAG, "onModifyAnswerCompleted");
        dismissLoadingDialog();
        if (mCommitSuccess) {
            finish();
        }
    }
}
