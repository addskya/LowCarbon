package com.mylowcarbon.app.trade.order;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class TradeRulePresenter implements TradeRuleContract.Presenter {
    private static final String TAG = "MainPresenter";
    private TradeRuleContract.Model mData;
    private TradeRuleContract.View mView;


    TradeRulePresenter(@NonNull TradeRuleContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new TradeRuleModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
