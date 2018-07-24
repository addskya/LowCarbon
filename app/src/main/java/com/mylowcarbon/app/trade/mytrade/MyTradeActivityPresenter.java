package com.mylowcarbon.app.trade.mytrade;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTrade;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class MyTradeActivityPresenter implements MyTradeActivityContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MyTradeActivityContract.Model mData;
    private MyTradeActivityContract.View mView;


    MyTradeActivityPresenter(@NonNull MyTradeActivityContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyTradeActivityModel();
    }

    @Override
    public void getWalletData(){
        mData.getWalletData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyTrade>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyTrade> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getWalletData 成功:  "  );
                            mView.onDataSuc(response.getValue());

                        } else {
                            Log.e(TAG, "getWalletData 失败:  " + response.getCode() + " : " + response.getMsg());
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
