package com.mylowcarbon.app.home.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.model.SportInfo;

/**
 * 运动挖矿
 */
public interface MineContract {

    interface View extends BaseView<Presenter> {

        void showMenu();

        void linkBracelet();

        void exchange();
    }

    interface Presenter extends BasePresenter {


    }

    interface Model {

    }

}
