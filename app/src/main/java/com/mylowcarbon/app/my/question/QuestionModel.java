package com.mylowcarbon.app.my.question;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

class QuestionModel implements QuestionContract.Model {

    private QuestionRequest mRequest;

    QuestionModel() {
        mRequest = new QuestionRequest();
    }

    @Override
    public Observable<Response<List<QuestionAnswer>>> verifyIdentity() {
        return mRequest.verifyIdentity();
    }

    @Override
    public Observable<Response<List<Question>>> getQuestions() {
        return mRequest.getQuestions();
    }

    @Override
    public Observable<Response<?>> modifyAnswer(@NonNull List<Answer> answers) {
        try {
            JSONObject object = new JSONObject();
            for (Answer a : answers) {
                object.put(String.valueOf(a.getQuestionId()),
                        String.valueOf(a.getQuestionAnswer()));
            }
            return mRequest.modifyAnswer(object);
        } catch (JSONException e) {
            return Observable.error(e);
        }
    }
}
