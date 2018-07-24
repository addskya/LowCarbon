package com.mylowcarbon.app.ride;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class RidePresenter implements RideContract.Presenter {
    private static final String TAG = "MainPresenter";
    private RideContract.Model mData;
    private RideContract.View mView;


    RidePresenter(@NonNull RideContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new RidetModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
