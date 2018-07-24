package com.mylowcarbon.app.forget.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class QuestionFindPasswordPresenter implements QuestionFindPasswordContract.Presenter {

    private static final String TAG = "QuestionFindPasswordPresenter";

    private QuestionFindPasswordContract.View mView;
    private QuestionFindPasswordContract.Model mData;

    QuestionFindPasswordPresenter(@NonNull QuestionFindPasswordContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new QuestionFindPasswordModel();
    }


    @Override
    public void loadQuestions(@NonNull CharSequence mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }

        mData.loadQuestions(mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<List<Question>>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadQuestionStart();
                        }
                    }

                    @Override
                    public void onNext(Response<List<Question>> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoadQuestionSuccess(response.getValue());
                        } else {
                            mView.onLoadQuestionFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadQuestionError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadQuestionCompleted();
                        }
                    }
                });
    }

    @Override
    public void verifyQuestion(@NonNull final CharSequence mobile,
                               int questionId,
                               @Nullable CharSequence answer) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }

        mData.verifyQuestion(mobile, questionId, answer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<QuestionResp>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onVerifyQuestionStart();
                        }
                    }

                    @Override
                    public void onNext(Response<QuestionResp> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onVerifyQuestionSuccess(mobile, response.getValue());
                        } else {
                            mView.onVerifyQuestionFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onVerifyQuestionError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onVerifyQuestionCompleted();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
