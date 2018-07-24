package com.mylowcarbon.app.my.wallet.transfer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Wallet;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter
 */

class TransferPresenter implements TransferContract.Presenter {
    private static final String TAG = "TransferPresenter";
    private TransferContract.View mView;
    private TransferContract.Model mData;


    TransferPresenter(@NonNull TransferContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new TransferModel();

    }

    public void queryByWalletAddress(@NonNull  CharSequence wallet_address) {


        mData.queryByWalletAddress(wallet_address)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Wallet>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<Wallet> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "queryByWalletAddress 成功:  "  );
                            mView.onQuerySuc(response.getValue());

                        } else {
                            Log.e(TAG, "queryByWalletAddress 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onQueryFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onTransferFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
    public void transfer(@NonNull  CharSequence wallet_address,
                         @NonNull CharSequence amount,
                         @NonNull CharSequence pay_pwd) {


        mData.transfer(wallet_address, amount, pay_pwd)
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
                            Log.e(TAG, "transfer 成功:  "  );
                            mView.onTransferSuc(response.getMsg());

                        } else {
                            Log.e(TAG, "transfer 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onTransferFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onTransferFail(e.getMessage());

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
