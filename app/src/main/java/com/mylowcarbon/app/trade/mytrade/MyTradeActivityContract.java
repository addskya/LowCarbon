package com.mylowcarbon.app.trade.mytrade;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTrade;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface MyTradeActivityContract {

    interface View extends BaseView<Presenter> {
        void onDataSuc(MyTrade data);
        void onDataFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void getWalletData();

    }

    interface Model {
        Observable<Response<MyTrade>> getWalletData();
    }
}
