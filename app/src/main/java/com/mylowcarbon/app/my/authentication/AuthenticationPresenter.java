package com.mylowcarbon.app.my.authentication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  WalkSubFragment的Presenter
 */

class AuthenticationPresenter implements AuthenticationContract.Presenter {
    private static final String TAG = "MainPresenter";
    private AuthenticationContract.View mView;
    private AuthenticationContract.Model mData;


    AuthenticationPresenter(@NonNull AuthenticationContract.View view) {
        view.setPresenter(this);
        mView = view;
        mData = new AuthenticationModel();

    }

    public void identityAuth(@NonNull  CharSequence user_name,
                             @NonNull CharSequence id_num,
                             @NonNull List<String> id_num_imgs){

        String imgs = listToString(id_num_imgs ,",");
        mData.identityAuth(user_name,id_num,imgs)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(Response<?> response) {

                        if (response.isSuccess()) {
                            Log.e(TAG, "identityAuth 成功:  " +response.getValue());
                            mView.onAuthSuc(response.getMsg());

                        } else {
                            Log.e(TAG, "identityAuth 失败:  " +response.getCode() + " : "+ response.getMsg());
                            mView.onAuthFail(response.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onAuthFail(e.getMessage());

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Override
    public void destroy() {
        mView = null;
        mData = null;

    }


}
