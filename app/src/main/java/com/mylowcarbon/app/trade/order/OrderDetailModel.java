package com.mylowcarbon.app.trade.order;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.Observable;

/**
 *
 */
class OrderDetailModel implements OrderDetailContract.Model {

    private OrderDetailRequest mRequest;

    OrderDetailModel() {
        mRequest = new OrderDetailRequest();
    }


    @Override
    public Observable<Response<?>> updateOrderStatus(@NonNull  String order_sn,@NonNull int order_status){
        return mRequest.updateOrderStatus(order_sn, order_status );
    }

    @Override
    public Observable<Response<?>> comment(@NonNull  String order_sn,@NonNull int comment_type, @NonNull String remark,@NonNull  int role_type){
        return mRequest.comment(order_sn, comment_type , remark,  role_type);
    }
    @Override
    public Observable<Response<OrderDetail>> getDetailData(@NonNull  int order_id ,@NonNull  int role_type){
        return mRequest.getDetailData(order_id,  role_type);
    }

}
