package com.mylowcarbon.app.my.wallet.transfer;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Wallet;

import java.util.List;

import rx.Observable;

/**
 *  契约类
 */
public interface TransferContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);
        void onQuerySuc(Wallet data);
        void onQueryFail(String msg);
        void onTransferSuc(String msg);
        void onTransferFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void queryByWalletAddress(@NonNull  CharSequence wallet_address);
        void transfer(@NonNull  CharSequence wallet_address,
                      @NonNull CharSequence amount,
                      @NonNull CharSequence pay_pwd);


    }

    interface Model {
        Observable<Response<Wallet>> queryByWalletAddress(@NonNull  CharSequence wallet_address);

        Observable<Response<?>> transfer(@NonNull  CharSequence wallet_address,
                                         @NonNull CharSequence amount,
                                         @NonNull CharSequence pay_pwd);

    }
}
