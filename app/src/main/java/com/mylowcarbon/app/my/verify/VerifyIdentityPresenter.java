package com.mylowcarbon.app.my.verify;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */

class VerifyIdentityPresenter implements VerifyIdentityContract.Presenter {
    private static final String TAG = "MainPresenter";
    private VerifyIdentityContract.View mView;
    private VerifyIdentityContract.Model mData;


    VerifyIdentityPresenter(@NonNull VerifyIdentityContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new VerifyIdentityModel();
    }

    @Override
    public void loadProblem() {
        mData.loadProblem()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<List<Problem>>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onLoadProblemStart();
                        }
                    }

                    @Override
                    public void onNext(Response<List<Problem>> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoadProblemSuccess(response.getValue());
                        } else {
                            mView.onLoadProblemFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onLoadProblemError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onLoadProblemCompleted();
                        }
                    }
                });
    }

    @Override
    public void checkProblem(int problemId, @NonNull CharSequence answer) {
        mData.checkProblem(problemId, answer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onCheckProblemStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onCheckProblemSuccess();
                        } else {
                            mView.onCheckProblemFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onCheckProblemError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onCheckProblemCompleted();
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
