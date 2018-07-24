package com.mylowcarbon.app.trade.mytrade;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class MyTradeFragmentPresenter implements MyTradeFragmentContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MyTradeFragmentContract.Model mData;
    private MyTradeFragmentContract.View mView;


    MyTradeFragmentPresenter(@NonNull MyTradeFragmentContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyTradeFragmentModel();
    }

    @Override
    public void getSellData(@NonNull int last_time,  final @NonNull boolean isRefresh) {
        mData.getSellData(last_time)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyTradeDetail>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyTradeDetail> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getSellData 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getSellData 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void getBuyData(@NonNull int last_id,  final @NonNull boolean isRefresh) {
        mData.getBuyData(last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyTradeDetail>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyTradeDetail> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getBuyData 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getBuyData 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void getCompleteData(@NonNull int last_time,  final @NonNull boolean isRefresh) {
        mData.getCompleteData(last_time)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyTradeDetail>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyTradeDetail> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getCompleteData 成功:  " +response.getValue());
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getCompleteData 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
    @Override
    public void cancelOrder(@NonNull  int coin_id ){
        mData.cancelOrder(coin_id  )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<?> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "updateOrderStatus 成功:  "  );
                            mView.onCancelOrderSuc(response.getMsg());

                        } else {
                            Log.e(TAG, "updateOrderStatus 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onCancelOrderFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onCancelOrderFail(e.getMessage());

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
