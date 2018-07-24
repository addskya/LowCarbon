package com.mylowcarbon.app.register.step;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-5.
 * Email:addskya@163.com
 */
public interface StepContract {

    interface View extends BaseView<Presenter> {

        void commit();

        void onModifyStepStart();

        void onModifyStepSuccess(int stepInCm);

        void onModifyStepFail(int errorCode);

        void onModifyStepError(Throwable error);

        void onModifyStepCompleted();
    }

    interface Presenter extends BasePresenter {

        void modifyStep(int stepInCm);
    }

    interface Model {
        Observable<Response<?>> modifyStep(int stepInCm);
    }
}
