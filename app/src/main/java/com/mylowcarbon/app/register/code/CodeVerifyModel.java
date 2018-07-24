package com.mylowcarbon.app.register.code;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

class CodeVerifyModel implements CodeVerifyContract.Model {
    private static final String TAG = "CodeVerifyModel";
    private CodeVerifyRequest mRequest;

    CodeVerifyModel() {
        mRequest = new CodeVerifyRequest();
    }

    @Override
    public Observable<Response<?>> verifyCode(@NonNull CharSequence mobile,
                                              @NonNull CharSequence code,
                                              @NonNull boolean is_register) {
        Log.i(TAG,"verifyCode:" + mobile + ",code:" + code + ",is_register:" + is_register);
        return mRequest.verifyCode(mobile, code, is_register);
    }
}
