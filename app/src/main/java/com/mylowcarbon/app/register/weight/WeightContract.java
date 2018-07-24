package com.mylowcarbon.app.register.weight;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
public interface WeightContract {

    interface View extends BaseView<Presenter> {

        void previous();

        void commit();

        void onModifyWeightStart();

        void onModifyWeightSuccess(int weightInKg);

        void onModifyWeightFail(int errorCode);

        void onModifyWeightError(Throwable error);

        void onModifyWeightCompleted();
    }

    interface Presenter extends BasePresenter {

        void modifyWeight(int weightInKg);
    }

    interface Model {
        Observable<Response<?>> modifyWeight(int weightInKg);
    }
}
