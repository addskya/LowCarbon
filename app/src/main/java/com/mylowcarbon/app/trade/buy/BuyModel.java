package com.mylowcarbon.app.trade.buy;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Buy;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.Observable;

/**
 *
 */
class BuyModel implements BuyContract.Model {

    private static final String TAG = "BuyModel";
    private BuyRequest mRequest;

    BuyModel() {
        mRequest = new BuyRequest();
    }

    @Override
    public Observable<Response<Buy>> getDetailData(@NonNull int coin_id){

        return mRequest.getDetailData(coin_id);
    }
    @Override
    public Observable<Response<OrderDetail>> comfirm(@NonNull int coin_id,@NonNull int number){

        return mRequest.comfirm(coin_id,number);
    }


}
