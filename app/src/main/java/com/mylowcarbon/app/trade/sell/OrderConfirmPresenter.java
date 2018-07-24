package com.mylowcarbon.app.trade.sell;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * WalkSubFragment的Presenter
 */

class OrderConfirmPresenter implements OrderConfirmContract.Presenter {
    private static final String TAG = "MainPresenter";
    private OrderConfirmContract.Model mData;
    private OrderConfirmContract.View mView;


    OrderConfirmPresenter(@NonNull OrderConfirmContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new OrderConfirmModel();
    }


    @Override
    public void comfirm(@NonNull float number, @NonNull int price, @NonNull CharSequence set_today_time, @NonNull CharSequence set_second_time, @NonNull CharSequence set_third_time, @NonNull CharSequence set_fourth_time, @NonNull CharSequence set_fifth_time, @NonNull CharSequence set_sixth_time, @NonNull CharSequence set_seventh_time) {
        mData.comfirm(number, price, set_today_time ,set_second_time ,set_third_time,set_fourth_time,set_fifth_time,set_sixth_time,set_seventh_time).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<?> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "comfirm 成功:  "  );
                            mView.onConfirmSuc(response.getMsg());

                        } else {
                            Log.e(TAG, "comfirm 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onConfirmFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onConfirmFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
