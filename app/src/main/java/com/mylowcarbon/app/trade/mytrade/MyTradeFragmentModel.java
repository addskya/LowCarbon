package com.mylowcarbon.app.trade.mytrade;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import rx.Observable;

/**
 *
 */
class MyTradeFragmentModel implements MyTradeFragmentContract.Model {

    private MyTradeFragmentRequest mRequest;

    MyTradeFragmentModel() {
        mRequest = new MyTradeFragmentRequest();
    }


    @Override
    public Observable<Response<MyTradeDetail>> getSellData(@NonNull int last_time){
        return mRequest.getSellData(last_time);
    }
    @Override
    public Observable<Response<MyTradeDetail>> getBuyData(@NonNull int last_time){
        return mRequest.getBuyData(last_time);
    }
    @Override
    public Observable<Response<MyTradeDetail>> getCompleteData(@NonNull int last_time){
        return mRequest.getCompleteData(last_time);
    }
    @Override
    public Observable<Response<?>> cancelOrder(@NonNull  int coin_id ){
        return mRequest.cancelOrder(coin_id );
    }
}
