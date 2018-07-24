package com.mylowcarbon.app.register.height;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
public interface HeightContract {

    interface View extends BaseView<Presenter> {

        void previous();

        void commit();

        void onModifyHeightStart();

        void onModifyHeightSuccess(int heightInCm);

        void onModifyHeightFail(int errorCode);

        void onModifyHeightError(Throwable error);

        void onModifyHeightCompleted();
    }


    interface Presenter extends BasePresenter {
        void modifyHeight(int heightInCm);
    }

    interface Model {

        Observable<Response<?>> modifyHeight(int heightInCm);
    }
}
