package com.mylowcarbon.app.mine.ride;

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
        mData = new RideModel();
    }


    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
