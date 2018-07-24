package com.mylowcarbon.app.my.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * WalkSubFragment契约类
 */
public interface QuestionContract {

    interface View extends BaseView<Presenter> {

        void onAnswerChanged(@Nullable Question question,
                             @Nullable Editable editable);

        /**
         * 提交密保答案
         */
        void commit();


        void onVerifyIdentityStart();

        void onVerifyIdentitySuccess(@Nullable List<QuestionAnswer> answers);

        void onVerifyIdentityError(Throwable error);

        void onVerifyIdentityCompleted();

        void onLoadQuestionStart();

        void onLoadQuestionSuccess(@Nullable List<Question> questions);

        void onLoadQuestionFail(int errorCode);

        void onLoadQuestionError(Throwable error);

        void onLoadQuestionCompleted();

        void onModifyAnswerStart();

        void onModifyAnswerSuccess();

        void onModifyAnswerFail(int errorCode);

        void onModifyAnswerError(Throwable error);

        void onModifyAnswerCompleted();
    }

    interface Presenter extends BasePresenter {

        /**
         * 判断用户是否已经设置了密保
         */
        void verifyIdentity();

        /**
         * 获取系统预设的密保问题
         */
        void loadQuestions();

        /**
         * 设置用户密保问题答案
         */
        void modifyAnswer(@NonNull List<Answer> answers);
    }

    interface Model {

        /**
         * 查看用户是否已经设置了密保问题
         *
         * @return 是否已经设置了密保问题
         */
        Observable<Response<List<QuestionAnswer>>> verifyIdentity();

        Observable<Response<List<Question>>> getQuestions();

        Observable<Response<?>> modifyAnswer(@NonNull List<Answer> answers);
    }
}
