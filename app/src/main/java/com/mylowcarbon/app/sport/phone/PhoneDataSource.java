package com.mylowcarbon.app.sport.phone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.remote.PhoneSportApi;
import com.mylowcarbon.app.sport.SportDataSource;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 */

public class PhoneDataSource implements SportDataSource {
    private static final String TAG = "PhoneDataSource";
    private PhoneSportApi mApi;
    private Context mContext;
    private Runnable mCallback;

    @Override
    public void setUp(@NonNull Context applicationContext,
                      @Nullable Runnable callback) {
        mCallback = callback;
        mContext = applicationContext;
        Intent service = new Intent(applicationContext, PhoneSportService.class);
        applicationContext.bindService(service, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void tearDown(@NonNull Context applicationContext) {
        applicationContext.unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mApi = PhoneSportApi.Stub.asInterface(service);
            if (mCallback != null) {
                mCallback.run();
                mCallback = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mApi = null;
        }
    };

    @Override
    public SportInfo getSportInfo(int padding) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(mApi.getSportInfo(padding), SportInfo.class);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportBicycle() {
        return false;
    }

    @Override
    public String getRideKey() {
        return null;
    }

    @Override
    public boolean isSupportStepCounter() {
        try {
            return mApi.isSupportStepCounter();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getStepCounterKey() {
        try {
            return mApi.getStepCounterKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportCalorie() {
        try {
            return mApi.isSupportCalorie();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getCalorieKey() {
        try {
            return mApi.getCalorieKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportPower() {
        try {
            return mApi.isSupportPower();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPowerKey() {
        try {
            return mApi.getPowerKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportHeartRate() {
        try {
            return mApi.isSupportHeartRate();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getHeartRateKey() {
        try {
            return mApi.getHeartRateKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportBloodPressure() {
        try {
            return mApi.isSupportBloodPressure();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getBloodPressureKey() {
        try {
            return mApi.getBloodPressureKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSupportSleep() {
        try {
            return mApi.isSupportSleep();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getSleepKey() {
        try {
            return mApi.getSleepKey();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DrawableRes
    @Override
    public int getSourceIcon() {
        return R.drawable.ic_phone_white;
    }


    @NonNull
    @Override
    public String getSourceName() {
        return mContext != null ? mContext.getString(R.string.text_sport_source_phone) : "";
    }
}
