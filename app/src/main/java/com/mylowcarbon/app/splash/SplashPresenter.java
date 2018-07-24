package com.mylowcarbon.app.splash;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Splash
 */

class SplashPresenter implements SplashContract.Presenter {
    private static final String TAG = "MainPresenter";
    private SplashContract.View mView;
    private SplashContract.Model mData;


    SplashPresenter(@NonNull SplashContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new SplashModel();
    }

    @Override
    public void loadCacheLogo() {
        mData.getCacheLogo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Logo>>() {
                    @Override
                    public void onNext(List<Logo> response) {
                        if (mView.isAdded()) {
                            mView.onLoadCacheLogoSuccess(response);
                        }
                    }
                });
    }

    @Override
    public void loadLogo() {
        mData.getLogo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Logo>>() {

                    @Override
                    public void onNext(Response<Logo> response) {
                        if (!mView.isAdded()) {
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onLoadLogoSuccess(response.getValue());
                        } else {
                            mView.onLoadLogoFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadLogoError(e);
                    }

                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
