package com.mylowcarbon.app.register.gender;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class GenderPresenter implements GenderContract.Presenter {

    private GenderContract.View mView;
    private GenderContract.Model mData;

    GenderPresenter(@NonNull GenderContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new GenderModel();
    }

    @Override
    public void modifySex(final int gender) {
        mData.modifySex(gender)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {

                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyGenderStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onModifyGenderSuccess(gender);
                        } else {
                            mView.onModifyGenderFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyGenderError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyGenderCompleted();
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
