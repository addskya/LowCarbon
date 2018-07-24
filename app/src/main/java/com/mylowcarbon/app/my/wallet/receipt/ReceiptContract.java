package com.mylowcarbon.app.my.wallet.receipt;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.Receipt;

import rx.Observable;

/**
 * WalkSubFragment契约类
 */
public interface ReceiptContract {

    interface View extends BaseView<Presenter> {
        void onViewClick(int position);

        void onItemClick(int position);

        void onGetReceiptInfoSuc(Receipt data);

        void onGetReceiptInfoFail(String msg);

        void onModifySuc(String msg);

        void onModifyFail(String msg);

    }

    interface Presenter extends BasePresenter {
        void getReceiptInfo();
        void modifyPayInfo(@NonNull final CharSequence alipay_account,
                           @NonNull CharSequence wechat_code,
                           @NonNull int card_id, @NonNull int pay_type, @NonNull int show_account);
    }

    interface Model {
        Observable<Response<Receipt>> getReceiptInfo();

        Observable<Response<?>> modifyPayInfo(@NonNull CharSequence alipay_account,
                                              @NonNull CharSequence wechat_code,
                                              @NonNull int card_id, @NonNull int pay_type, @NonNull int show_account);
    }
}
