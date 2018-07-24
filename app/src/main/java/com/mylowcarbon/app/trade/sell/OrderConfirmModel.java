package com.mylowcarbon.app.trade.sell;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *
 */
class OrderConfirmModel implements OrderConfirmContract.Model {
     private OrderConfirmRequest mRequest;

    OrderConfirmModel() {
        mRequest = new OrderConfirmRequest();
    }


    @Override
    public  Observable<Response<?>> comfirm(@NonNull  float number,
                                            @NonNull int price,
                                            @NonNull CharSequence set_today_time,
                                            @NonNull CharSequence set_second_time,
                                            @NonNull CharSequence set_third_time,
                                            @NonNull CharSequence set_fourth_time,
                                            @NonNull CharSequence set_fifth_time,
                                            @NonNull CharSequence set_sixth_time,
                                            @NonNull CharSequence set_seventh_time){
        return mRequest.comfirm(number, price, set_today_time ,set_second_time ,set_third_time,set_fourth_time,set_fifth_time,set_sixth_time,set_seventh_time);
    }

}
