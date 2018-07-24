package com.mylowcarbon.app.trade.mytrade.childorder;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyChildOrder;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */

class ChildOrderPresenter implements ChildOrderContract.Presenter {
    private static final String TAG = "MainPresenter";
    private ChildOrderContract.Model mData;
    private ChildOrderContract.View mView;


    ChildOrderPresenter(@NonNull ChildOrderContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new ChildOrderModel();
    }

    @Override
    public void getListData(@NonNull int coin_id , @NonNull int last_id ,  @NonNull final boolean isRefresh ) {
        mData.getListData(coin_id,last_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<MyChildOrder>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<MyChildOrder> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getListData 成功:  "  );
                            mView.onDataSuc(response.getValue(),isRefresh);

                        } else {
                            Log.e(TAG, "getListData 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.onDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {



                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }


}
