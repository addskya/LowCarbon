package com.mylowcarbon.app;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    /**
     * Whether or NOT the View has destroyed,
     *
     * @return for Activity return !isFinish(),
     * for Fragment return isAdded(),
     * for Dialog return isShowing()
     */
    boolean isAdded();
}
