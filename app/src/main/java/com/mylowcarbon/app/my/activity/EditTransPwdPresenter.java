package com.mylowcarbon.app.my.activity;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class EditTransPwdPresenter implements EditTransPwdContract.Presenter {
    private static final String TAG = "MainPresenter";
     private EditTransPwdContract.View mView;


    EditTransPwdPresenter(@NonNull EditTransPwdContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
