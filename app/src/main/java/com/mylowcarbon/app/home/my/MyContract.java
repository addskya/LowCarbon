package com.mylowcarbon.app.home.my;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface MyContract {

    interface View extends BaseView<Presenter> {
        void onItemClick(int position);

    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }
}
