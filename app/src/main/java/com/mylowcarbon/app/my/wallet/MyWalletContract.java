package com.mylowcarbon.app.my.wallet;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface MyWalletContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);
        void onDataSuc(MyWallet myWalletInfo,boolean isRefresh);
        void onDataFail(String msg);
        void getMyWallet( boolean isRefresh);


    }

    interface Presenter extends BasePresenter {
        void getMyWallet(@NonNull String start_time,
                         @NonNull String end_time,
                         @NonNull String last_id ,
                         @NonNull boolean isRefresh );


    }

    interface Model {
        Observable<Response<MyWallet>> getMyWallet(@NonNull String start_time,
                                                   @NonNull String end_time,
                                                   @NonNull String last_id);

    }
}
