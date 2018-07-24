package com.mylowcarbon.app.register.height;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
class HeightPresenter implements HeightContract.Presenter {

    private HeightContract.View mView;
    private HeightContract.Model mData;

    HeightPresenter(@NonNull HeightContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new HeightModel();
    }

    @Override
    public void modifyHeight(final int heightInCm) {
        mData.modifyHeight(heightInCm)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyHeightStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onModifyHeightSuccess(heightInCm);
                        } else {
                            mView.onModifyHeightFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyHeightError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyHeightCompleted();
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
