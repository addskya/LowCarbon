package com.mylowcarbon.app.forget.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public interface QuestionFindPasswordContract {

    interface View extends BaseView<Presenter> {

        void onLoadQuestionStart();

        void onLoadQuestionSuccess(@Nullable List<Question> questions);

        void onLoadQuestionFail(int errorCode);

        void onLoadQuestionError(Throwable error);

        void onLoadQuestionCompleted();

        void onVerifyQuestionStart();

        void onVerifyQuestionSuccess(@NonNull CharSequence mobile,
                                     @Nullable QuestionResp resp);

        void onVerifyQuestionFail(int errorCode);

        void onVerifyQuestionError(Throwable error);

        void onVerifyQuestionCompleted();

        void verifyQuestion();

        void findPasswordByAccount();
    }


    interface Presenter extends BasePresenter {

        /**
         * 加载之前设置的密保问题列表
         *
         * @param mobile 账号
         */
        void loadQuestions(@NonNull CharSequence mobile);


        void verifyQuestion(@NonNull CharSequence mobile,
                            int questionId,
                            @Nullable CharSequence answer);

    }

    interface Model {

        Observable<Response<List<Question>>> loadQuestions(@NonNull CharSequence mobile);

        Observable<Response<QuestionResp>> verifyQuestion(@NonNull CharSequence mobile,
                                               int questionId,
                                               @Nullable CharSequence answer);

    }

}
