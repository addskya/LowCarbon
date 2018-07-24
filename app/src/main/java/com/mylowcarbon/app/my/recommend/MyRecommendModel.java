package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyRecommend;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 */
class MyRecommendModel implements MyRecommendContract.Model {

    private static final String TAG = "MyRecommendModel";
    private MyRecommendRequest mRequest;

    MyRecommendModel() {
        mRequest = new MyRecommendRequest();
    }

    @Override
    public Observable<Response<MyRecommend>> getMyRecommend(@NonNull String last_id) {
         return mRequest.getMyRecommend(last_id)
                .doOnNext(new Action1<Response<MyRecommend>>() {
                    @Override
                    public void call(Response<MyRecommend> response) {
                        if (response.isSuccess()) {

                        }
                    }
                });
    }

}
