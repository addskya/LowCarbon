package com.mylowcarbon.app.register.gender;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-3.
 * Email:addskya@163.com
 */

public interface GenderContract {

    interface View extends BaseView<Presenter> {

        void commit();

        void onModifyGenderStart();

        void onModifyGenderSuccess(int gender);

        void onModifyGenderFail(int errorCode);

        void onModifyGenderError(Throwable error);

        void onModifyGenderCompleted();
    }

    interface Presenter extends BasePresenter {

        /**
         * 修改性别
         * @param gender 性别（0：未知，1：男，2：女）
         */
        void modifySex(int gender);
    }

    interface Model {

        Observable<Response<?>> modifySex(int sex);
    }
}
