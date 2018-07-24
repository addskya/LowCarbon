package com.mylowcarbon.app.my.wallet.receipt;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Receipt;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter
 */

class ReceiptPresenter implements ReceiptContract.Presenter {
    private static final String TAG = "MainPresenter";
    private ReceiptContract.View mView;
    private ReceiptContract.Model mData;


    ReceiptPresenter(@NonNull ReceiptContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ReceiptModel();

    }

    public void getReceiptInfo() {


        mData.getReceiptInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Receipt>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<Receipt> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getReceiptInfo 成功:  ");
                            mView.onGetReceiptInfoSuc(response.getValue());

                        } else {
                            Log.e(TAG, "getReceiptInfo 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onGetReceiptInfoFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetReceiptInfoFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public void modifyPayInfo(@NonNull CharSequence alipay_account,
                              @NonNull CharSequence wechat_code,
                              @NonNull int card_id, @NonNull int pay_type, @NonNull int show_account) {


        mData.modifyPayInfo(alipay_account, wechat_code, card_id, pay_type, show_account)
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
                            Log.e(TAG, "modifyPayInfo 成功:  ");
                            mView.onModifySuc(response.getMsg());

                        } else {
                            Log.e(TAG, "modifyPayInfo 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onModifyFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onModifyFail(e.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
    }


}
