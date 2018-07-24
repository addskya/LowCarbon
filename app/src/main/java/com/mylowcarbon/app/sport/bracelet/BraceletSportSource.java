package com.mylowcarbon.app.sport.bracelet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.sport.SportDataSource;

/**
 * Created by Orange on 18-4-27.
 * Email:addskya@163.com
 * 手环数据,主要读取数据库
 */

public class BraceletSportSource implements SportDataSource{


    @Override
    public void setUp(@NonNull Context applicationContext,
                      @Nullable Runnable callback) {

    }

    @Override
    public void tearDown(@NonNull Context applicationContext) {

    }

    @Override
    public SportInfo getSportInfo(int padding) {
        return null;
    }

    @Override
    public boolean isSupportBicycle() {
        return true;
    }

    @Override
    public boolean isSupportStepCounter() {
        return true;
    }

    @Override
    public String getRideKey() {
        return null;
    }

    @Override
    public String getStepCounterKey() {
        return null;
    }

    @Override
    public boolean isSupportCalorie() {
        return false;
    }

    @Override
    public String getCalorieKey() {
        return null;
    }

    @Override
    public boolean isSupportPower() {
        return false;
    }

    @Override
    public String getPowerKey() {
        return null;
    }

    @Override
    public boolean isSupportHeartRate() {
        return false;
    }

    @Override
    public String getHeartRateKey() {
        return null;
    }

    @Override
    public boolean isSupportBloodPressure() {
        return false;
    }

    @Override
    public String getBloodPressureKey() {
        return null;
    }

    @Override
    public boolean isSupportSleep() {
        return false;
    }

    @Override
    public String getSleepKey() {
        return null;
    }

    @Override
    public int getSourceIcon() {
        return 0;
    }

    @NonNull
    @Override
    public String getSourceName() {
        return null;
    }
}
