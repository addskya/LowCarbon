package com.mylowcarbon.app.register.step;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class StepPresenter implements StepContract.Presenter {

    private StepContract.View mView;
    private StepContract.Model mData;

    StepPresenter(@NonNull StepContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new StepModel();
    }

    @Override
    public void modifyStep(final int stepInCm) {
        mData.modifyStep(stepInCm)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyStepStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onModifyStepSuccess(stepInCm);
                        } else {
                            mView.onModifyStepFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyStepError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyStepCompleted();
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
