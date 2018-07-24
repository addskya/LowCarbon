package com.mylowcarbon.app.my.authentication;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface AuthenticationContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);
        void onAuthSuc(String msg);
        void onAuthFail(String msg);


    }

    interface Presenter extends BasePresenter {
        void identityAuth(@NonNull final CharSequence user_name,
                          @NonNull CharSequence id_num,
                          @NonNull List<String> id_num_imgs);


    }

    interface Model {
        Observable<Response<?>> identityAuth(@NonNull final CharSequence user_name,
                                                   @NonNull CharSequence id_num,
                                                   @NonNull CharSequence id_num_imgs);

    }
}
