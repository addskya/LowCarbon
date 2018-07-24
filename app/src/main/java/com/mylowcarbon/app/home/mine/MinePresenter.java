package com.mylowcarbon.app.home.mine;

import android.support.annotation.NonNull;

/**
 *
 */

class MinePresenter implements MineContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MineContract.Model mData;
    private MineContract.View mView;


    MinePresenter(@NonNull MineContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new MineModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
