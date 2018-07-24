package com.mylowcarbon.app.forget.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:${EMAIL}
 */

class QuestionFindPasswordModel implements QuestionFindPasswordContract.Model {

    private QuestionFindPasswordRequest mRequest;

    QuestionFindPasswordModel() {
        mRequest = new QuestionFindPasswordRequest();
    }

    @Override
    public Observable<Response<List<Question>>> loadQuestions(@NonNull CharSequence mobile) {
        return mRequest.loadQuestions(mobile);
    }

    @Override
    public Observable<Response<QuestionResp>> verifyQuestion(@NonNull CharSequence mobile,
                                                  int questionId,
                                                  @Nullable CharSequence answer) {
        return mRequest.verifyQuestion(mobile, questionId, answer);
    }
}
