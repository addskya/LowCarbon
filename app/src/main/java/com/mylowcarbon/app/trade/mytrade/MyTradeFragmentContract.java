package com.mylowcarbon.app.trade.mytrade;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyTradeDetail;

import rx.Observable;

/**
 *   契约类
 */
public interface MyTradeFragmentContract {

    interface View extends BaseView<Presenter> {
        void onItemClick(int position);
        void onItemViewClick(int position);
        void onDataSuc(MyTradeDetail data , boolean isRefresh);
        void onDataFail(String msg);
        void onCancelOrderSuc(String msg);
        void onCancelOrderFail(String msg);

    }

    interface Presenter extends BasePresenter {

        void getSellData(@NonNull int last_time, final @NonNull boolean isRefresh);
        void getBuyData(@NonNull int last_id,  final @NonNull boolean isRefresh);
        void getCompleteData(@NonNull int last_time,  final @NonNull boolean isRefresh);
        void cancelOrder(@NonNull  int coin_id);

    }

    interface Model {
        Observable<Response<MyTradeDetail>> getSellData(@NonNull int last_time );
        Observable<Response<MyTradeDetail>> getBuyData(@NonNull int last_id );
        Observable<Response<MyTradeDetail>> getCompleteData(@NonNull int last_time );
        Observable<Response<?>> cancelOrder(@NonNull  int coin_id );

    }
}
