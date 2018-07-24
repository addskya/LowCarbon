package com.mylowcarbon.app.home.trade;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Trade;

import rx.Observable;

/**
 *
 */
class TradeModel implements TradeContract.Model {

    private static final String TAG = "TransferModel";
    private TradeRequest mRequest;

    TradeModel() {
        mRequest = new TradeRequest();
    }

    @Override
    public Observable<Response<Trade>> getTradeData(@NonNull CharSequence mobile,
                                                    @NonNull boolean is_register,
                                                    @NonNull int last_id){

        return mRequest.getTradeData(mobile,is_register,last_id);
    }


}
