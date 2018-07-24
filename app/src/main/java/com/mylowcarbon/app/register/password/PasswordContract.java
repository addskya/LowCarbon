package com.mylowcarbon.app.register.password;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.login.DeviceParameters;
import com.mylowcarbon.app.model.RegisterInfo;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 */

public interface PasswordContract {

    interface View extends BaseView<Presenter> {

        void onCommitStart();

        void onCommitSuccess(@NonNull CharSequence mobile,
                             @NonNull CharSequence password,
                             @NonNull RegisterInfo regInfo);

        void onCommitFail(int responseCode);

        void onCommitError(Throwable error);

        void onCommitCompleted();

        void commit();
    }

    interface Presenter extends BasePresenter {

        void commit(@NonNull CharSequence mobile,
                    @NonNull CharSequence loginPassword,
                    @NonNull CharSequence dealPassword,
                    @NonNull CharSequence walletAddress,
                    @NonNull CharSequence keystore,
                    @Nullable CharSequence recommendCode,
                    @NonNull DeviceParameters deviceParameters);
    }

    interface Model {
        Observable<Response<RegisterInfo>> commit(
                @NonNull CharSequence mobile,
                @NonNull CharSequence loginPassword,
                @NonNull CharSequence dealPassword,
                @NonNull CharSequence walletAddress,
                @NonNull CharSequence keystore,
                @Nullable CharSequence recommendCode,
                @NonNull DeviceParameters deviceParameters);
    }
}
