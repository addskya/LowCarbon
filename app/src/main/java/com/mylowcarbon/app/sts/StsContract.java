package com.mylowcarbon.app.sts;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  STSß契约类
 */
public interface StsContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);


    }

    interface Presenter extends BasePresenter {
        void getSts();


    }

    interface Model {
        Observable<Response<StsInfo>> getSts();

    }
}
