package com.mylowcarbon.app.my.wallet;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyWallet;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 */
class MyWalletModel implements MyWalletContract.Model {

    private static final String TAG = "MyWalletModel";
    private MyWalletRequest mRequest;

    MyWalletModel() {
        mRequest = new MyWalletRequest();
    }

    @Override
    public Observable<Response<MyWallet>> getMyWallet(@NonNull String start_time,
                                                      @NonNull String end_time,
                                                      @NonNull String last_id) {
          return mRequest.getMyWallet(start_time,end_time,last_id)
                .doOnNext(new Action1<Response<MyWallet>>() {
                    @Override
                    public void call(Response<MyWallet> response) {
                        if (response.isSuccess()) {

                        }
                    }
                });
    }

}
