package com.mylowcarbon.app.remote;

interface PhoneSportApi {
    // 获取运动数据,以 String json数据返回
    String getSportInfo(int padding);

    // 是否支持步行
    boolean isSupportStepCounter();

    String getStepCounterKey();

    // 是否支持卡路里计算
    boolean isSupportCalorie();

    String getCalorieKey();

    // 是否支持能力值
    boolean isSupportPower();

    String getPowerKey();

    // 是否支持心率
    boolean isSupportHeartRate();

    String getHeartRateKey();

    // 是否支持血压
    boolean isSupportBloodPressure();

    String getBloodPressureKey();

    // 是否支持睡眠
    boolean isSupportSleep();

    String getSleepKey();
}
