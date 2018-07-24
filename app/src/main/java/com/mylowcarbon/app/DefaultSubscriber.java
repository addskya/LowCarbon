package com.mylowcarbon.app;

import rx.Subscriber;

/**
 * Created by Orange on 18-3-3.
 * Email:addskya@163.com
 */
public class DefaultSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
    }

    @Override
    public void onNext(T response) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onCompleted() {
    }
}
