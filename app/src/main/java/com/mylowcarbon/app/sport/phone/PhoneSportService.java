package com.mylowcarbon.app.sport.phone;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.remote.PhoneSportApi;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

import greendao.SportInfoDao;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 * <p>
 * 手机运动数据记录服务
 * 目前要解决一个问题, 今天数据和明天的数据如何分开存储
 */

public class PhoneSportService extends Service {

    private static final String TAG = "PhoneSportService";
    private static final String SPORT_PREF = "sports.xml";

    private static final String KEY_PHONE_STEP_COUNT = "phone_step_count";
    private static final String KEY_PHONE_CALORIE = "phone_calorie";
    private static final String KEY_PHONE_POWER = "phone_power";
    private static final String KEY_PHONE_HEART_RATE = "phone_heart_rate";
    private static final String KEY_PHONE_BLOOD_PRESSURE = "phone_blood_pressure";
    private static final String KEY_PHONE_SLEEP = "phone_sleep";

    private final PhoneSportApi.Stub mApi = new PhoneSportApi.Stub() {

        @Override
        public String getSportInfo(int padding) throws RemoteException {
            return getJsonSportInfo(padding);
        }

        @Override
        public boolean isSupportStepCounter() throws RemoteException {
            return isSupportStepCounterImpl();
        }

        @Override
        public String getStepCounterKey() throws RemoteException {
            return KEY_PHONE_STEP_COUNT;
        }

        @Override
        public boolean isSupportCalorie() throws RemoteException {
            return isSupportCalorieImpl();
        }

        @Override
        public String getCalorieKey() throws RemoteException {
            return KEY_PHONE_CALORIE;
        }

        @Override
        public boolean isSupportPower() throws RemoteException {
            return isSupportPowerImpl();
        }

        @Override
        public String getPowerKey() throws RemoteException {
            return KEY_PHONE_POWER;
        }

        @Override
        public boolean isSupportHeartRate() throws RemoteException {
            return false;
        }

        @Override
        public String getHeartRateKey() throws RemoteException {
            return KEY_PHONE_HEART_RATE;
        }

        @Override
        public boolean isSupportBloodPressure() throws RemoteException {
            return isSupportBloodPressureImpl();
        }

        @Override
        public String getBloodPressureKey() throws RemoteException {
            return KEY_PHONE_BLOOD_PRESSURE;
        }

        @Override
        public boolean isSupportSleep() throws RemoteException {
            return isSupportSleepImpl();
        }

        @Override
        public String getSleepKey() throws RemoteException {
            return KEY_PHONE_SLEEP;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mApi;
    }

    private SensorManager mSensorManager;
    private SharedPreferences mPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
        mPreference = getSharedPreferences(SPORT_PREF, MODE_PRIVATE);
        setUp();
    }

    private void setUp() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (isSupportStepCounterImpl()) {
            Sensor stepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(
                    mStepCounterListener,
                    stepCounterSensor,
                    SensorManager.SENSOR_DELAY_UI);
        }

        if (isSupportHeartRateImpl()) {
            Sensor heartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            mSensorManager.registerListener(mHeartRateListener,
                    heartRateSensor,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    private String getDeviceImei() {
        return Settings.Secure.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private SensorEventListener mStepCounterListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            LogUtil.i(TAG, "stepCounter,onSensorChanged");
            // values[0] = 51.0
            String date = DateUtil.getCalendar(0);

            // 为什么不直接使用 values[0], 因为: Android的计步器只有在重启Android后才会清零.

            SportInfoDao dao = ModelDao.getInstance().getDaoSession().getSportInfoDao();
            List<SportInfo> sportInfos = dao.queryBuilder()
                    .where(SportInfoDao.Properties.Date.eq(date))
                    .list();

            // sportInfos.size == 1
            if (sportInfos != null && sportInfos.size() > 0) {
                for (SportInfo info : sportInfos) {
                    info.setSteps(info.getSteps() + 1);
                }
                dao.deleteInTx(sportInfos);
                dao.insertInTx(sportInfos);
                mPreference.edit()
                        .putFloat(KEY_PHONE_STEP_COUNT, sportInfos.get(0).getSteps())
                        .apply();
            } else { // 如果从数据库没有查询到,表明可能是新的一天了,
                SportInfo sportInfo = SportInfo.from(getDeviceImei(),null, null,
                        null, null, 0);
                mSensorManager.unregisterListener(this);
                Sensor stepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                mSensorManager.registerListener(
                        mStepCounterListener,
                        stepCounterSensor,
                        SensorManager.SENSOR_DELAY_UI);
                sportInfo.setSteps(1);
                dao.insert(sportInfo);
                mPreference.edit()
                        .putFloat(KEY_PHONE_STEP_COUNT, 1.0F)
                        .apply();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            LogUtil.i(TAG, "stepCounter,onAccuracyChanged:" + accuracy);
        }
    };


    private SensorEventListener mHeartRateListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            LogUtil.i(TAG, "heartRate,onSensorChanged:" + event);
            mPreference.edit()
                    .putFloat(KEY_PHONE_HEART_RATE, event.values[0])
                    .apply();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            LogUtil.i(TAG, "heartRate,onAccuracyChanged:" + accuracy);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mStepCounterListener);
        mSensorManager.unregisterListener(mHeartRateListener);

        mSensorManager = null;

        mPreference = null;
    }

    private boolean isSupportStepCounterImpl() {
        return mSensorManager != null &&
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null;
    }

    private boolean isSupportCalorieImpl() {
        return false;
    }

    private boolean isSupportPowerImpl() {
        return false;
    }

    private boolean isSupportHeartRateImpl() {
        return mSensorManager != null &&
                mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null;
    }

    private boolean isSupportBloodPressureImpl() {
        return false;
    }

    private boolean isSupportSleepImpl() {
        return false;
    }

    /**
     * 获取运动数据json
     *
     * @param padding 相对今天的日期, -2是前天, -1,昨天 0,今天
     * @return 运动数据json
     */
    private String getJsonSportInfo(int padding) {
        return null;
    }
}
