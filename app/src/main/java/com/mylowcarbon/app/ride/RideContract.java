package com.mylowcarbon.app.ride;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface RideContract {

    interface View extends BaseView<Presenter> {
        void start();

    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }
}
