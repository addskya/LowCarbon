package com.mylowcarbon.app.sts;

import android.util.Log;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 */
class StsModel implements StsContract.Model {

    private static final String TAG = "StsModel";
    private StsRequest mRequest;

    StsModel() {
        mRequest = new StsRequest();
    }

    @Override
    public Observable<Response<StsInfo>> getSts() {
        Log.i(TAG,"getSts:" );
         return mRequest.getSts()
                .doOnNext(new Action1<Response<StsInfo>>() {
                    @Override
                    public void call(Response<StsInfo> response) {
                        if (response.isSuccess()) {
                            StsInfo stsInfo = response.getValue();
                            ModelDao.getInstance().updateStsInfo(response.getValue());
                        }
                    }
                });
    }

}
