package com.mylowcarbon.app.my.verify;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface VerifyIdentityContract {

    interface View extends BaseView<Presenter> {
        void switchToProblem();

        void switchToPhone();

        void commit();

        void onLoadProblemStart();

        void onLoadProblemSuccess(List<Problem> problems);

        void onLoadProblemFail(int errorCode);

        void onLoadProblemError(Throwable error);

        void onLoadProblemCompleted();


        void onCheckProblemStart();

        void onCheckProblemSuccess();

        void onCheckProblemFail(int errorCode);

        void onCheckProblemError(Throwable error);

        void onCheckProblemCompleted();
    }

    interface Presenter extends BasePresenter {

        void loadProblem();

        void checkProblem(int problemId,
                          @NonNull CharSequence answer);

    }

    interface Model {

        Observable<Response<List<Problem>>> loadProblem();

        Observable<Response<?>> checkProblem(int problemId,
                                             @NonNull CharSequence answer);
    }
}
