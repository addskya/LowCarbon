package com.mylowcarbon.app.trade.mytrade;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTrade;

import rx.Observable;

/**
 *
 */
class MyTradeActivityModel implements MyTradeActivityContract.Model {

    private MyTradeActivityRequest mRequest;

    MyTradeActivityModel() {
        mRequest = new MyTradeActivityRequest();
    }


    @Override
    public Observable<Response<MyTrade>> getWalletData(){
        return mRequest.getWalletData();
    }



}
