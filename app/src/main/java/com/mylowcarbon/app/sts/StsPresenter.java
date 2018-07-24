package com.mylowcarbon.app.sts;

import android.util.Log;

import com.mylowcarbon.app.App;
import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

public class StsPresenter implements StsContract.Presenter {
    private static final String TAG = "StsPresenter";
    private StsContract.Model mData;


    public StsPresenter() {

        mData = new StsModel();
    }

    public void getSts(){
        mData.getSts()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<StsInfo>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<StsInfo> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getSts 成功:  " +response.getValue());
                            try {
                                StsUtil.mStsInfo = response.getValue();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                          } else {
                            Log.e(TAG, "getSts 失败:  " +response.getCode() + " : "+ response.getMsg());

                         }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    };

    @Override
    public void destroy() {
         mData = null;
    }


}
