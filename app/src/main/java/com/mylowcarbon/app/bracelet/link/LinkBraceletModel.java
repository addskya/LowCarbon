package com.mylowcarbon.app.bracelet.link;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.net.Response;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 从数据库中读取已经连接过的手环数据
 */
class LinkBraceletModel implements LinkBraceletContract.Model {

    private UploadRequest mRequest;

    LinkBraceletModel() {
        mRequest = new UploadRequest();
    }

    @Override
    public Observable<List<BleDevices>> loadBracelet() {
        List<BleDevices> devices =
                ModelDao.getInstance()
                        .getDaoSession()
                        .getBleDevicesDao()
                        .loadAll();
        return Observable.just(devices);
    }

    @Override
    public Observable<Response<?>> uploadSportData(@NonNull BleDevices device) {
        CharSequence imei = device.getAddress();
        List<SportInfo> data = new ArrayList<>(3);
        data.add(device.getBeforeYesterday());
        data.add(device.getYesterday());
        data.add(device.getToday());
        return mRequest.upload(imei, data);
    }
}
