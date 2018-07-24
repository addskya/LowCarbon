package com.mylowcarbon.app.trade.buy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Buy;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class BuyPresenter implements BuyContract.Presenter {
    private static final String TAG = "MainPresenter";
    private BuyContract.Model mData;
    private BuyContract.View mView;


    BuyPresenter(@NonNull BuyContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new BuyModel();
    }

    @Override
    public void getDetailData(@NonNull int coin_id){
        mData.getDetailData(coin_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Buy>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<Buy> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getDetailData 成功:  "  );
                            mView.onGetDataSuc(response.getValue());

                        } else {
                            Log.e(TAG, "getDetailData 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onGetDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetDataFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void comfirm(@NonNull int coin_id,@NonNull int number){
        mData.comfirm(coin_id , number)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<OrderDetail>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<OrderDetail> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "comfirm 成功:  "  );
                            mView.onConfirmSuc(response.getValue());

                        } else {
                            Log.e(TAG, "comfirm 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onConfirmFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onConfirmFail(e.getMessage());

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
