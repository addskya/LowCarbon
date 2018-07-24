package com.mylowcarbon.app.my.nickname;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


class EditNicknamePresenter implements EditNicknameContract.Presenter {
    private static final String TAG = "MainPresenter";
    private EditNicknameContract.View mView;
    private EditNicknameContract.Model mData;


    EditNicknamePresenter(@NonNull EditNicknameContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new EditNicknameModel();
    }

    @Override
    public void modifyNickname(@NonNull final CharSequence nickname) {
        mData.modifyNickname(nickname)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        if (mView.isAdded()) {
                            mView.onModifyNicknameStart();
                        }
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        if (!mView.isAdded()) {
                            return;
                        }
                        if (response.isSuccess()) {
                            mView.onModifyNicknameSuccess(nickname);
                        } else {
                            mView.onModifyNicknameFail(Response.getResponseCode(response));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isAdded()) {
                            mView.onModifyNicknameError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (mView.isAdded()) {
                            mView.onModifyNicknameCompleted();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;
    }
}
