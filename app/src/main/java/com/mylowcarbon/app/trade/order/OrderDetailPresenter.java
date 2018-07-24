package com.mylowcarbon.app.trade.order;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class OrderDetailPresenter implements OrderDetailContract.Presenter {
    private static final String TAG = "MainPresenter";
    private OrderDetailContract.Model mData;
    private OrderDetailContract.View mView;


    OrderDetailPresenter(@NonNull OrderDetailContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new OrderDetailModel();
    }

    @Override
    public void updateOrderStatus(@NonNull  String order_sn,@NonNull final int order_status){
        mData.updateOrderStatus(order_sn , order_status)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<?> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "updateOrderStatus 成功:  "  );
                            mView.updateOrderSuc(response.getMsg(),order_status);

                        } else {
                            Log.e(TAG, "updateOrderStatus 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.updateOrderFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.updateOrderFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void comment(@NonNull  String order_sn,@NonNull int comment_type,@NonNull String remark,@NonNull  int role_type){
        mData.comment(order_sn , comment_type ,remark,  role_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<?> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "comment 成功:  "  );
                            mView.updateOrderSuc(response.getMsg(),4);

                        } else {
                            Log.e(TAG, "comment 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.updateOrderFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.updateOrderFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
    @Override
    public void getDetailData(@NonNull  int order_id ,@NonNull  int role_type){
        mData.getDetailData(order_id ,role_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<OrderDetail>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<OrderDetail> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "getDetailData 成功:  "  );
                            mView.getDataSuc(response.getValue());

                        } else {
                            Log.e(TAG, "getDetailData 失败:  " + response.getCode() + " : " + response.getMsg());
                            mView.getDataFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.updateOrderFail(e.getMessage());

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
