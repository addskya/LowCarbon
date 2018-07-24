package com.mylowcarbon.app.home.trade;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.net.response.Trade;
import com.mylowcarbon.app.net.response.Wallet;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface TradeContract {

    interface View extends BaseView<Presenter> {
        void showTradeRule();
        void sell();
        void onItemClick(int position);
        void onDataSuc(Trade datas, boolean isRefresh);
        void onDataFail(String msg);
        void getTradeData(boolean isRefresh);

    }

    interface Presenter extends BasePresenter {

       void getTradeData(@NonNull CharSequence mobile,
                     @NonNull boolean is_register,
                     @NonNull int last_id,@NonNull boolean isRefresh);
    }

    interface Model {
        Observable<Response<Trade>> getTradeData(@NonNull CharSequence mobile,
                                                  @NonNull boolean is_register,
                                                  @NonNull int last_id);
    }
}
