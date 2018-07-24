package com.mylowcarbon.app.trade.mytrade.childorder;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyChildOrder;

import rx.Observable;

/**
 *
 */
class ChildOrderModel implements ChildOrderContract.Model {

    private ChildOrderRequest mRequest;

    ChildOrderModel() {
        mRequest = new ChildOrderRequest();
    }


    @Override
    public Observable<Response<MyChildOrder>> getListData(@NonNull int coin_id , @NonNull int last_id){
        return mRequest.getListData(coin_id,last_id );
    }

}
