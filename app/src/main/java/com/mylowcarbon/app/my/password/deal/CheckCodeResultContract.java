package com.mylowcarbon.app.my.password.deal;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface CheckCodeResultContract {

    interface View extends BaseView<Presenter> {

        void onQueryDealCodeStart();

        void onQueryDealCodeSuccess(@NonNull PayEntry entry);

        void onQueryDealCodeFail(int errorCode);

        void onQueryDealCodeError(Throwable error);

        void onQueryDealCodeCompleted();
    }

    interface Presenter extends BasePresenter {

        void queryPayPwd();

    }

    interface Model {
        Observable<Response<PayEntry>> queryPayPwd();
    }
}
