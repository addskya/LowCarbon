package com.mylowcarbon.app.my.wallet.bankcard;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.BankType;
import com.mylowcarbon.app.net.response.Wallet;

import rx.Observable;

/**
 *  WalkSubFragment契约类
 */
public interface ChooseBankContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);

        void onQuerySuc(BankType data);
        void onQueryFail(String msg);
        void onAddSuc(String msg);
        void onAddFail(String msg);
        void onVerifySuc(String msg);
        void onVerifyFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void queryByBankNum(@NonNull CharSequence bankNum);
        void addCard(@NonNull  CharSequence user_name,
                      @NonNull CharSequence card_number,
                      @NonNull CharSequence card_type,
                      @NonNull CharSequence card_mobile);
        void verifyPhoneNumber(@NonNull CharSequence mobile);

    }

    interface Model {
        Observable<Response<BankType>> queryByBankNum(@NonNull CharSequence bankNum);
        Observable<Response<?>> addCard(@NonNull  CharSequence user_name,
                     @NonNull CharSequence card_number,
                     @NonNull CharSequence card_type,
                     @NonNull CharSequence card_mobile);
        Observable<Response<?>> verifyPhoneNumber(@NonNull CharSequence mobile);


    }
}
