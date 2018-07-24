package com.mylowcarbon.app.trade.buy;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Buy;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.Observable;

/**
 *  契约类
 */
public interface BuyContract {

    interface View extends BaseView<Presenter> {
        void confirm();
        void onGetDataSuc(Buy data);
        void onGetDataFail(String msg);
        void onConfirmSuc(OrderDetail data);
        void onConfirmFail(String msg);
    }

    interface Presenter extends BasePresenter {
        void getDetailData(@NonNull int coin_id);
        void comfirm(@NonNull int coin_id ,int number);

    }

    interface Model {
        Observable<Response<Buy>> getDetailData(@NonNull int coin_id);
        Observable<Response<OrderDetail>> comfirm(@NonNull int coin_id, int number);
    }
}
