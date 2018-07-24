package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

/**
 *  WalkSubFragment的Presenter
 */

class RecommendPresenter implements RecommendContract.Presenter {
    private static final String TAG = "MainPresenter";
    private RecommendContract.Model mData;
    private RecommendContract.View mView;


    RecommendPresenter(@NonNull RecommendContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new RecommendModel();
    }



    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
