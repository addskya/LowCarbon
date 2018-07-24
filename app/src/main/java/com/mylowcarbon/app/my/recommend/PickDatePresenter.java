package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class PickDatePresenter implements PickDateContract.Presenter {
    private static final String TAG = "MainPresenter";
     private PickDateContract.View mView;


    PickDatePresenter(@NonNull PickDateContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
