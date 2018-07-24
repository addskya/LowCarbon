package com.mylowcarbon.app.my.order;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class MyOrdersPresenter implements MyOrdersContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MyOrdersContract.Model mData;
    private MyOrdersContract.View mView;


    MyOrdersPresenter(@NonNull MyOrdersContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyOrdersModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
