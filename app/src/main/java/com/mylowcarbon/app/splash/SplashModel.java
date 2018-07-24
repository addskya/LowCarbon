package com.mylowcarbon.app.splash;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import java.util.List;

import greendao.LogoDao;
import rx.Observable;
import rx.functions.Action1;

/**
 * 获取/读取启动图
 */
class SplashModel implements SplashContract.Model {

    private static final String TAG = "SplashModel";
    private SplashRequest mRequest;

    SplashModel() {
        mRequest = new SplashRequest();
    }

    @Override
    public Observable<List<Logo>> getCacheLogo() {
        return ModelDao.getInstance()
                .getDaoSession()
                .getLogoDao()
                .rx()
                .loadAll();
    }

    @Override
    public Observable<Response<Logo>> getLogo() {
        return mRequest.getLogo()
                .doOnNext(new Action1<Response<Logo>>() {
                    @Override
                    public void call(Response<Logo> response) {
                        if (response.isSuccess()) {
                            Logo log = response.getValue();
                            LogoDao dao = ModelDao.getInstance()
                                    .getDaoSession()
                                    .getLogoDao();

                            dao.deleteAll();
                            dao.insert(log);
                        }
                    }
                });
    }
}
