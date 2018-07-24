package com.mylowcarbon.app.home.trade;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Trade;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class TradePresenter implements TradeContract.Presenter {
    private static final String TAG = "TradePresenter";
    private TradeContract.Model mData;
    private TradeContract.View mView;


    TradePresenter(@NonNull TradeContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new TradeModel();
    }
    @Override
    public void getTradeData(@NonNull CharSequence mobile,
                                         @NonNull boolean is_register,
                                         @NonNull int last_id,final @NonNull boolean isRefresh) {
        mData.getTradeData(mobile,is_register,last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Trade>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<Trade> response) {

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
