package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.net.response.RecommendRank;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface RecommendRankingContract {

    interface View extends BaseView<Presenter> {
        void exchange();
        void onItemClick(int position);
        void onViewClick(int position);
        void onDataSuc(RecommendRank myWalletInfo, boolean isRefresh);
        void onDataFail(String msg);
        void getRecommendRank( boolean isRefresh);
    }

    interface Presenter extends BasePresenter {
        void getRecommendRank(@NonNull String start_time,
                         @NonNull String end_time,
                         @NonNull String last_id ,
                         @NonNull boolean isRefresh );

    }

    interface Model {
        Observable<Response<RecommendRank>> getRecommendRank(@NonNull String start_time,
                                                        @NonNull String end_time,
                                                        @NonNull String last_id);

    }
}
