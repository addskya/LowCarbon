package com.mylowcarbon.app.my.question;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

class Answer {

    @SerializedName("id")
    private int mQuestionId;

    @SerializedName("answer")
    private CharSequence mQuestionAnswer;

    void setQuestionId(int id) {
        mQuestionId = id;
    }

    void setQuestionAnswer(@Nullable CharSequence answer) {
        mQuestionAnswer = answer;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public CharSequence getQuestionAnswer() {
        return mQuestionAnswer;
    }

    String toJson() {
        return "[" +
                mQuestionId +
                ":" +
                String.valueOf(mQuestionAnswer) +
                "]";
    }
}
