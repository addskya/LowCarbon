package com.mylowcarbon.app.my.activity;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class EditDeviceNamePresenter implements EditDeviceNameContract.Presenter {
    private static final String TAG = "MainPresenter";
     private EditDeviceNameContract.View mView;


    EditDeviceNamePresenter(@NonNull EditDeviceNameContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
