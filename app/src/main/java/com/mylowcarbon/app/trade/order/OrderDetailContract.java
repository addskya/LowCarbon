package com.mylowcarbon.app.trade.order;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.OrderDetail;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface OrderDetailContract {

    interface View extends BaseView<Presenter> {
        void cancelDeal();
        void mark();
        void appraise();
        void updateOrderSuc(String msg,int status);
        void updateOrderFail(String msg);
        void getDataSuc(OrderDetail data );
        void getDataFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void updateOrderStatus(@NonNull  String order_sn,@NonNull int order_status);
        void comment(@NonNull String order_sn, @NonNull int comment_type, @NonNull String remark,@NonNull  int role_type);
        void getDetailData(@NonNull  int order_id,@NonNull  int role_type );
    }

    interface Model {
        Observable<Response<?>> updateOrderStatus(@NonNull String order_sn, @NonNull int order_status);
        Observable<Response<?>> comment(@NonNull String order_sn, @NonNull int comment_type, @NonNull String remark,@NonNull  int role_type);
        Observable<Response<OrderDetail>> getDetailData(@NonNull  int order_id,@NonNull  int role_type );
    }
}
