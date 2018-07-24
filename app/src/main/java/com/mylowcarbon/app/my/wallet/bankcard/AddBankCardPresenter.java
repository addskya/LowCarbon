package com.mylowcarbon.app.my.wallet.bankcard;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class AddBankCardPresenter implements AddBankCardContract.Presenter {
    private static final String TAG = "MainPresenter";
     private AddBankCardContract.View mView;


    AddBankCardPresenter(@NonNull AddBankCardContract.View view) {
        view.setPresenter(this);
        mView = view;
     }



    @Override
    public void destroy() {
        mView = null;
     }


}
