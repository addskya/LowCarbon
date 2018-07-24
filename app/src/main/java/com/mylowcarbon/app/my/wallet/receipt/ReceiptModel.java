package com.mylowcarbon.app.my.wallet.receipt;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Receipt;

import rx.Observable;

/**
 *
 */
class ReceiptModel implements ReceiptContract.Model {

    private static final String TAG = "ReceiptModel";
    private ReceiptRequest mRequest;

    ReceiptModel() {
        mRequest = new ReceiptRequest();
    }

    @Override
    public Observable<Response<Receipt>> getReceiptInfo(){

         return mRequest.getReceiptInfo();
    }
    @Override
    public Observable<Response<?>> modifyPayInfo(@NonNull  CharSequence alipay_account,
                                                 @NonNull CharSequence wechat_code,
                                                 @NonNull int card_id,@NonNull int pay_type,@NonNull int show_account) {
         return mRequest.modifyPayInfo(alipay_account, wechat_code, card_id,pay_type,show_account);
    }

}
