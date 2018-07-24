package com.mylowcarbon.app.my.about;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.About;
import com.mylowcarbon.app.net.response.MyWallet;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface AboutContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);
        void onDataSuc(About myWalletInfo);
        void onDataFail(String msg);
        void getAboutData();

    }

    interface Presenter extends BasePresenter {
       void getAboutData();

    }

    interface Model {
        Observable<Response<About>> getAboutData();
    }
}
