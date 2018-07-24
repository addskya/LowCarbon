package com.mylowcarbon.app.my.order;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface MyOrderDetailContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);


    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }
}
