package com.mylowcarbon.app.my.nickname;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import rx.Observable;

/**
 *  Modify nickname 契约类
 */
public interface EditNicknameContract {

    interface View extends BaseView<Presenter> {

        void onTextChanged(@NonNull Editable text);

        void commit();

        void onModifyNicknameStart();

        void onModifyNicknameSuccess(@NonNull CharSequence nickname);

        void onModifyNicknameFail(int errorCode);

        void onModifyNicknameError(Throwable error);

        void onModifyNicknameCompleted();
    }

    interface Presenter extends BasePresenter {
        void modifyNickname(@NonNull CharSequence nickname);
    }

    interface Model {
        Observable<Response<?>> modifyNickname(@NonNull CharSequence nickname);
    }
}
