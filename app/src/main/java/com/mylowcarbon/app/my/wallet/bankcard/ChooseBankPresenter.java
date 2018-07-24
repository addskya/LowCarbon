package com.mylowcarbon.app.my.wallet.bankcard;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.BankType;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class ChooseBankPresenter implements ChooseBankContract.Presenter {
    private static final String TAG = "MainPresenter";
    private ChooseBankContract.View mView;
    private ChooseBankContract.Model mData;


    ChooseBankPresenter(@NonNull ChooseBankContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ChooseBankModel();

    }
    public void queryByBankNum(@NonNull  CharSequence bankNum) {


        mData.queryByBankNum(bankNum)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<BankType>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<BankType> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "queryByBankNum 成功:  "  );
                            mView.onQuerySuc(response.getValue());

                        } else {
                            Log.e(TAG, "queryByBankNum 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onQueryFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onQueryFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
    public void addCard(@NonNull  CharSequence user_name,
                                @NonNull CharSequence card_number,
                                @NonNull CharSequence card_type,
                                @NonNull CharSequence card_mobile) {


        mData.addCard(user_name, card_number, card_type , card_mobile)
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
                            mView.onAddSuc(response.getMsg());

                        } else {
                            Log.e(TAG, "transfer 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onAddFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onAddFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
    public void verifyPhoneNumber(@NonNull CharSequence mobile) {

        mData.verifyPhoneNumber(mobile)
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
                            mView.onVerifySuc(response.getMsg());

                        } else {
                            Log.e(TAG, "transfer 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onVerifyFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onVerifyFail(e.getMessage());

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
