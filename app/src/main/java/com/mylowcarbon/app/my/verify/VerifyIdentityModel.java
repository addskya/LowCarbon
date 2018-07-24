package com.mylowcarbon.app.my.verify;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */
class VerifyIdentityModel implements VerifyIdentityContract.Model {

    private VerifyIdentityRequest mRequest;

    VerifyIdentityModel() {
        mRequest = new VerifyIdentityRequest();
    }

    @Override
    public Observable<Response<List<Problem>>> loadProblem() {
        return mRequest.loadProblem();
    }

    @Override
    public Observable<Response<?>> checkProblem(int problemId,
                                                @NonNull CharSequence answer) {
        return mRequest.checkProblem(problemId, answer);
    }
}
