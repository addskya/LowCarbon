package com.mylowcarbon.app.my.wallet.bankcard;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;

/**
 *  WalkSubFragment契约类
 */
public interface AddBankCardContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);


    }

    interface Presenter extends BasePresenter {

    }

    interface Model {
     }
}
