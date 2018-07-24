package com.mylowcarbon.app.my.wallet.scan;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class ScanningPresenter implements ScanningContract.Presenter {
    private static final String TAG = "MainPresenter";
     private ScanningContract.View mView;


    ScanningPresenter(@NonNull ScanningContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
