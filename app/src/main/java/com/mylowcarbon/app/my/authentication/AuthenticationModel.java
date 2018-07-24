package com.mylowcarbon.app.my.authentication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *
 */
class AuthenticationModel implements AuthenticationContract.Model {

    private static final String TAG = "MyWalletModel";
    private AuthenticationRequest mRequest;

    AuthenticationModel() {
        mRequest = new AuthenticationRequest();
    }

    @Override
    public Observable<Response<?>> identityAuth(@NonNull final CharSequence user_name,
                                                      @NonNull CharSequence id_num,
                                                      @NonNull CharSequence id_num_imgs) {
        Log.i(TAG,"identityAuth:" );
        return mRequest.identityAuth(user_name,id_num,id_num_imgs);
    }

}
