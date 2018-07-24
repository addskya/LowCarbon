package com.mylowcarbon.app.bracelet.own;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.net.Response;

import java.util.List;

import rx.Observable;

class DevicesModel implements DevicesContract.Model {

    private static final String TAG = "DevicesModel";
    private DevicesRequest mRequest;

    DevicesModel() {
        mRequest = new DevicesRequest();
    }

    @Override
    public Observable<Response<List<Device>>> loadDevices(
            @NonNull CharSequence condition) {
        return mRequest.getDevice(condition);
    }
}
