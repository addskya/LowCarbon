package com.mylowcarbon.app.splash;

import android.support.annotation.Nullable;

import com.mylowcarbon.app.BasePresenter;
import com.mylowcarbon.app.BaseView;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

/**
 * WalkSubFragment契约类
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void onLoadLogoSuccess(Logo data);

        void onLoadLogoFail(int errorCode);

        void onLoadLogoError(Throwable error);

        /**
         * 获取到上次使用的Logo.有可能为空
         *
         * @param list Logo列表
         */
        void onLoadCacheLogoSuccess(@Nullable List<Logo> list);
    }

    interface Presenter extends BasePresenter {

        void loadCacheLogo();

        void loadLogo();

    }

    interface Model {

        /**
         * 从本地获取上次所有的缓存图,
         *
         * @return 缓存图
         */
        Observable<List<Logo>> getCacheLogo();

        Observable<Response<Logo>> getLogo();
    }
}
