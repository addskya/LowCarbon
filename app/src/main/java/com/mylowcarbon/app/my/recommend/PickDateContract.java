package com.mylowcarbon.app.my.recommend;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface PickDateContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);


    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }
}
