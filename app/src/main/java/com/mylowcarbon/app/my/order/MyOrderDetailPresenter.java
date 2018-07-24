package com.mylowcarbon.app.my.order;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class MyOrderDetailPresenter implements MyOrderDetailContract.Presenter {
    private static final String TAG = "MainPresenter";
     private MyOrderDetailContract.View mView;


    MyOrderDetailPresenter(@NonNull MyOrderDetailContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
