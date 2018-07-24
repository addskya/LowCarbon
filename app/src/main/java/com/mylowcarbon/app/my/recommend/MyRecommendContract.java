package com.mylowcarbon.app.my.recommend;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.MyRecommend;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface MyRecommendContract {

    interface View extends BaseView<Presenter> {
        void exchange();
        void onItemClick(int position);
        void onViewClick(int position);
        void onDataSuc(MyRecommend myWalletInfo,boolean isRefresh);
        void onDataFail(String msg);
        void getMyRecommend( boolean isRefresh);

    }

    interface Presenter extends BasePresenter {
        void getMyRecommend(@NonNull String last_id ,@NonNull boolean isRefresh );

    }

    interface Model {
        Observable<Response<MyRecommend>> getMyRecommend(@NonNull String last_id  );
    }
}
