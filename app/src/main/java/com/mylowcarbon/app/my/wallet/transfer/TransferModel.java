package com.mylowcarbon.app.my.wallet.transfer;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Wallet;

import rx.Observable;

/**
 *
 */
class TransferModel implements TransferContract.Model {

    private static final String TAG = "TransferModel";
    private TransferRequest mRequest;

    TransferModel() {
        mRequest = new TransferRequest();
    }

    @Override
    public Observable<Response<Wallet>> queryByWalletAddress(@NonNull  CharSequence wallet_address){

         return mRequest.queryByWalletAddress(wallet_address);
    }
    @Override
    public Observable<Response<?>> transfer(@NonNull final CharSequence wallet_address,
                                            @NonNull CharSequence amount,
                                            @NonNull CharSequence pay_pwd) {
         return mRequest.transfer(wallet_address, amount, pay_pwd);
    }

}
