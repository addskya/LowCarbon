package com.mylowcarbon.app.my.order;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface MyOrdersContract {

    interface View extends BaseView<Presenter> {
        void remind(int position);
        void view(int position);
        void confirm(int position);
        void exchange(int position);
        void onItemClick(int position);

    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }
}
