package com.mylowcarbon.app.my.wallet;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class MyWalletPresenter implements MyWalletContract.Presenter {
    private static final String TAG = "MyWalletPresenter";
     private MyWalletContract.View mView;
     private MyWalletContract.Model mData;


    MyWalletPresenter(@NonNull MyWalletContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyWalletModel();

    }



    @Override
    public void getMyWallet(@NonNull String start_time,
                            @NonNull String end_time,
                            @NonNull String last_id,
                            final @NonNull boolean isRefresh) {
        mData.getMyWallet(start_time,end_time,last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyWallet>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyWallet> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getMyWallet 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getMyWallet 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
     }

    @Override
    public void destroy() {
        mView = null;
        mData = null;

    }


}
