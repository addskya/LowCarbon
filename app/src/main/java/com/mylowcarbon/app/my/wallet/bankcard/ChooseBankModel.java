package com.mylowcarbon.app.my.wallet.bankcard;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.BankType;

import rx.Observable;

/**
 *
 */
class ChooseBankModel implements ChooseBankContract.Model {

    private static final String TAG = "ChooseBankModel";
    private ChooseBankRequest mRequest;

    ChooseBankModel() {
        mRequest = new ChooseBankRequest();
    }

    @Override
    public Observable<Response<BankType>> queryByBankNum(@NonNull CharSequence bankNum){

         return mRequest.queryByBankNum(bankNum);
    }
    @Override
    public Observable<Response<?>> addCard(@NonNull  CharSequence user_name,
                                           @NonNull CharSequence card_number,
                                           @NonNull CharSequence card_type,
                                           @NonNull CharSequence card_mobile) {
         return mRequest.addCard(user_name, card_number, card_type , card_mobile);
    }
    @Override
    public Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile) {
         return mRequest.verifyPhoneNumber(mobile);
    }

}
