package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.RecommendRank;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 */
class RecommendRankingModel implements RecommendRankingContract.Model {
    private static final String TAG = "RecommendRankingModel";
    private RecommendRankingRequest mRequest;

    RecommendRankingModel() {
        mRequest = new RecommendRankingRequest();
    }

    @Override
    public Observable<Response<RecommendRank>> getRecommendRank(@NonNull String start_time,
                                                              @NonNull String end_time,
                                                              @NonNull String last_id) {
         return mRequest.getRecommendRank(start_time,end_time,last_id)
                .doOnNext(new Action1<Response<RecommendRank>>() {
                    @Override
                    public void call(Response<RecommendRank> response) {
                        if (response.isSuccess()) {

                        }
                    }
                });
    }


}
