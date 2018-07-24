package com.mylowcarbon.app.trade.mytrade.childorder;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyChildOrder;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface ChildOrderContract {

    interface View extends BaseView<Presenter> {
        void onItemClick(int position);
        void onDataSuc(MyChildOrder data , boolean isRefresh);
        void onDataFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void getListData(@NonNull int coin_id ,@NonNull int last_id ,  @NonNull boolean isRefresh);


    }

    interface Model {
        Observable<Response<MyChildOrder>> getListData(@NonNull int coin_id , @NonNull int last_id);
    }
}
