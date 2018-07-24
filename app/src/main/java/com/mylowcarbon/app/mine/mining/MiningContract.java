package com.mylowcarbon.app.mine.mining;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

public interface MiningContract {

    interface View extends BaseView<Presenter> {

        void onLoadTodayMiningStart();

        void onLoadTodayMiningSuccess(@Nullable Mining data);

        void onLoadTodayMiningFail(int errorCode);

        void onLoadTodayMiningError(Throwable error);

        void onLoadTodayMiningCompleted();
    }

    interface Presenter extends BasePresenter {

        void loadTodayMining(@NonNull CharSequence imei,
                             @NonNull Sport data);

    }

    interface Model {
        Observable<Response<Mining>> getTodayMining(@NonNull CharSequence imei,
                                                    @NonNull Sport data);
    }
}
