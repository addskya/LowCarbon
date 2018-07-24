package com.mylowcarbon.app.sport;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mylowcarbon.app.model.SportInfo;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 * <p>
 * 运动数据源,目前有手机,手环
 */

public interface SportDataSource {

    /**
     * 初始化服务
     *
     * @param applicationContext application context
     * @param callback           the callback after setup finished
     */
    void setUp(@NonNull Context applicationContext,
               @Nullable Runnable callback);

    /**
     * 停止服务
     *
     * @param applicationContext application context
     */
    void tearDown(@NonNull Context applicationContext);

    /**
     * 获取运动数据
     *
     * @param padding 相对今天的日期差,如,前天-2,昨天-1,今天0
     * @return 指定日期的运动数据量
     */
    SportInfo getSportInfo(int padding);

    /**
     * 是否支持骑行功能
     *
     * @return 是否支持骑行功能
     */
    boolean isSupportBicycle();

    /**
     * 是否支持步行
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportStepCounter();

    String getRideKey();


    String getStepCounterKey();

    /**
     * 是否支持卡路里计算
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportCalorie();

    String getCalorieKey();

    /**
     * 是否支持能力值
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportPower();

    String getPowerKey();

    /**
     * 是否支持心率
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportHeartRate();

    String getHeartRateKey();

    /**
     * 是否支持血压
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportBloodPressure();

    String getBloodPressureKey();

    /**
     * 是否支持睡眠
     *
     * @return true, 支持, false 不支持
     */
    boolean isSupportSleep();

    String getSleepKey();

    @DrawableRes
    int getSourceIcon();

    @NonNull
    String getSourceName();
}
