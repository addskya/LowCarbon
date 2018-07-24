package com.mylowcarbon.app.mine.walk;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class WalkPresenter implements WalkContract.Presenter {
    private static final String TAG = "MainPresenter";
    private WalkContract.Model mData;
    private WalkContract.View mView;


    WalkPresenter(@NonNull WalkContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new WalkModel();
    }


    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
