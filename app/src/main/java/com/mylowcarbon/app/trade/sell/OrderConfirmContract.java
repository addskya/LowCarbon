package com.mylowcarbon.app.trade.sell;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface OrderConfirmContract {

    interface View extends BaseView<Presenter> {
        void updateReceivingAccount();
        void confirm();
        void onClickDay(int day);
        void onConfirmSuc(String msg);
        void onConfirmFail(String msg);
    }

    interface Presenter extends BasePresenter {
        void comfirm(@NonNull  float number,
                                        @NonNull int price,
                                        @NonNull CharSequence set_today_time,
                                        @NonNull CharSequence set_second_time,
                                        @NonNull CharSequence set_third_time,
                                        @NonNull CharSequence set_fourth_time,
                                        @NonNull CharSequence set_fifth_time,
                                        @NonNull CharSequence set_sixth_time,
                                        @NonNull CharSequence set_seventh_time);

    }

    interface Model {
        Observable<Response<?>> comfirm(@NonNull  float number,
                                        @NonNull int price,
                                        @NonNull CharSequence set_today_time,
                                        @NonNull CharSequence set_second_time,
                                        @NonNull CharSequence set_third_time,
                                        @NonNull CharSequence set_fourth_time,
                                        @NonNull CharSequence set_fifth_time,
                                        @NonNull CharSequence set_sixth_time,
                                        @NonNull CharSequence set_seventh_time);
    }
}
