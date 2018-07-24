package com.mylowcarbon.app.my.complaints;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.About;
import com.mylowcarbon.app.net.response.Complain;

import java.util.List;

import rx.Observable;

/**
 *
 */
class ComplaintsModel implements ComplaintsContract.Model {

    private static final String TAG = "MyWalletModel";
    private ComplaintsRequest mRequest;

    ComplaintsModel() {
        mRequest = new ComplaintsRequest();
    }

    @Override
    public Observable<Response<?>> addComplain(@NonNull final CharSequence user_name,
                                                      @NonNull CharSequence id_num,
                                                      @NonNull CharSequence id_num_imgs) {
        Log.i(TAG,"addComplain:" );
        return mRequest.addComplain(user_name,id_num,id_num_imgs);
    }

    @Override
    public Observable<Response<Complain>> getComplainData() {
        return mRequest.getComplainData();
    }
}
