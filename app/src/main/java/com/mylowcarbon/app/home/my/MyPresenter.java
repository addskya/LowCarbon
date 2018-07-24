package com.mylowcarbon.app.home.my;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class MyPresenter implements MyContract.Presenter {
    private static final String TAG = "MyContract";
    private MyContract.Model mData;
    private MyContract.View mView;


    MyPresenter(@NonNull MyContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MyModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
