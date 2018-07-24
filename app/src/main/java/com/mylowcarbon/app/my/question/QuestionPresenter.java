package com.mylowcarbon.app.my.question;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class QuestionPresenter implements QuestionContract.Presenter {
    private static final String TAG = "MainPresenter";
    private QuestionContract.View mView;
    private QuestionContract.Model mData;


    QuestionPresenter(@NonNull QuestionContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new QuestionModel();
    }

    @Override
    public void verifyIdentity() {
        mData.verifyIdentity()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<List<QuestionAnswer>>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onVerifyIdentityStart();
                        }
                    }

                    @Override
                    public void onNext(Response<List<QuestionAnswer>> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        mView.onVerifyIdentitySuccess(response.getValue());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onVerifyIdentityError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onVerifyIdentityCompleted();
                        }
                    }
                });
    }

    @Override
    public void loadQuestions() {
        mData.getQuestions()
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
    public void modifyAnswer(@NonNull List<Answer> answers) {
        mData.modifyAnswer(answers)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyAnswerStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onModifyAnswerSuccess();
                        } else {
                            mView.onModifyAnswerFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyAnswerError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyAnswerCompleted();
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
