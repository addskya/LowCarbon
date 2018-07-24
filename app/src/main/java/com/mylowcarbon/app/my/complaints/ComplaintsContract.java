package com.mylowcarbon.app.my.complaints;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Complain;

import java.util.List;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface ComplaintsContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);
        void onAuthSuc(String msg);
        void onAuthFail(String msg);
         void onDataFail(String msg);
        void onGetDataSuc(Complain data);
     }

    interface Presenter extends BasePresenter {
        void addComplain(@NonNull final CharSequence user_name,
                          @NonNull CharSequence id_num,
                          @NonNull List<String> id_num_imgs);
         void getComplainData();

    }

    interface Model {
        Observable<Response<?>> addComplain(@NonNull final CharSequence user_name,
                                             @NonNull CharSequence id_num,
                                             @NonNull CharSequence id_num_imgs);
         Observable<Response<Complain>> getComplainData();

    }
}
