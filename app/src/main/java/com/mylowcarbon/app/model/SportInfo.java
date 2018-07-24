package com.mylowcarbon.app.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.BR;
import com.mylowcarbon.app.utils.DateUtil;
import com.yc.pedometer.info.BPVOneDayInfo;
import com.yc.pedometer.info.RateOneDayInfo;
import com.yc.pedometer.info.SleepTimeInfo;
import com.yc.pedometer.info.StepInfo;

import org.greenrobot.greendao.annotation.Entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Orange on 18-4-20.
 * Email:addskya@163.com
 * <p>
 * steps          当天步行数量
 * distances      当天步行里程
 * calories       当天步行消耗卡路里
 * clycle         当天骑行里程
 * burn           当天骑行消耗卡路里
 * heart_rate     当天平均心率
 * blood_1        当天平均血压收缩压
 * blood_2        当天平均血压舒张压
 * sleep          当天睡眠总时间(小时)
 * date           当天日期
 * <p>
 */

@Entity
public class SportInfo extends BaseObservable implements Parcelable {

    /**
     * 合并运动数据
     *
     * @param imei              设备唯一地址
     * @param stepInfo          步行数据
     * @param rateInfo          心率数据
     * @param bloodPressureInfo 血压数据
     * @param sleepInfo         睡眠数据
     * @return 合并后的运动数据
     */
    public static SportInfo from(@NonNull String imei,
                                 @Nullable StepInfo stepInfo,
                                 @Nullable RateOneDayInfo rateInfo,
                                 @Nullable List<BPVOneDayInfo> bloodPressureInfo,
                                 @Nullable SleepTimeInfo sleepInfo,
                                 int padding) {
        final SportInfo sport = new SportInfo();
        sport.setImei(imei);
        if (stepInfo != null) {
            sport.setSteps(stepInfo.getStep());
            sport.setDistances(stepInfo.getDistance());
            sport.setCalories(stepInfo.getCalories());
        }

        // 无骑行数据
        // 无骑行数据
        // 无骑行数据

        if (rateInfo != null) {
            sport.setHeart_rate(rateInfo.getVerageRate());
        }

        if (bloodPressureInfo != null && bloodPressureInfo.size() > 0) {
            // Demo上是取的最后一次测试数据.
            BPVOneDayInfo lastInfo = bloodPressureInfo.get(bloodPressureInfo.size() - 1);
            // 此处与API接口文档不符合,
            //  * blood_1        当天平均血压收缩压
            //  * blood_2        当天平均血压舒张压
            sport.setBlood_1(lastInfo.getHightBloodPressure());
            sport.setBlood_2(lastInfo.getLowBloodPressure());
        }

        if (sleepInfo != null) {
            sport.setSleep(sleepInfo.getSleepTotalTime());
        }

        sport.setDate(DateUtil.getCalendar(padding));

        return sport;
    }

    @Id
    private Long id;

    private String imei;
    private int steps;
    private float distances;
    private long calories;
    private float clycle;
    private long burn;
    private int heart_rate;
    private int blood_1;
    private int blood_2;
    private float sleep;
    private String date;

    private SportInfo() {

    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Bindable
    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        notifyPropertyChanged(BR.steps);
    }

    @Bindable
    public float getDistances() {
        return distances;
    }

    public void setDistances(float distances) {
        this.distances = distances;
        notifyPropertyChanged(BR.distances);
    }

    @Bindable
    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
        notifyPropertyChanged(BR.calories);
    }

    @Bindable
    public float getClycle() {
        return clycle;
    }

    public void setClycle(float clycle) {
        this.clycle = clycle;
        notifyPropertyChanged(BR.clycle);
    }

    @Bindable
    public long getBurn() {
        return burn;
    }

    public void setBurn(long burn) {
        this.burn = burn;
        notifyPropertyChanged(BR.burn);
    }

    @Bindable
    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
        notifyPropertyChanged(BR.heart_rate);
    }

    @Bindable
    public int getBlood_1() {
        return blood_1;
    }

    public void setBlood_1(int blood_1) {
        this.blood_1 = blood_1;
        notifyPropertyChanged(BR.blood_1);
    }

    @Bindable
    public int getBlood_2() {
        return blood_2;
    }

    public void setBlood_2(int blood_2) {
        this.blood_2 = blood_2;
        notifyPropertyChanged(BR.blood_2);
    }

    @Bindable
    public float getSleep() {
        return sleep;
    }

    public void setSleep(float sleep) {
        this.sleep = sleep;
        notifyPropertyChanged(BR.sleep);
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SportInfo{" +
                "id=" + id +
                ", imei='" + imei + '\'' +
                ", steps=" + steps +
                ", distances=" + distances +
                ", calories=" + calories +
                ", clycle=" + clycle +
                ", burn=" + burn +
                ", heart_rate=" + heart_rate +
                ", blood_1=" + blood_1 +
                ", blood_2=" + blood_2 +
                ", sleep=" + sleep +
                ", date='" + date + '\'' +
                '}';
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.imei);
        dest.writeInt(this.steps);
        dest.writeFloat(this.distances);
        dest.writeLong(this.calories);
        dest.writeFloat(this.clycle);
        dest.writeLong(this.burn);
        dest.writeInt(this.heart_rate);
        dest.writeInt(this.blood_1);
        dest.writeInt(this.blood_2);
        dest.writeFloat(this.sleep);
        dest.writeString(this.date);
    }

    protected SportInfo(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.imei = in.readString();
        this.steps = in.readInt();
        this.distances = in.readFloat();
        this.calories = in.readLong();
        this.clycle = in.readFloat();
        this.burn = in.readLong();
        this.heart_rate = in.readInt();
        this.blood_1 = in.readInt();
        this.blood_2 = in.readInt();
        this.sleep = in.readFloat();
        this.date = in.readString();
    }

    @Generated(hash = 922608797)
    public SportInfo(Long id, String imei, int steps, float distances, long calories,
            float clycle, long burn, int heart_rate, int blood_1, int blood_2, float sleep,
            String date) {
        this.id = id;
        this.imei = imei;
        this.steps = steps;
        this.distances = distances;
        this.calories = calories;
        this.clycle = clycle;
        this.burn = burn;
        this.heart_rate = heart_rate;
        this.blood_1 = blood_1;
        this.blood_2 = blood_2;
        this.sleep = sleep;
        this.date = date;
    }

    public static final Creator<SportInfo> CREATOR = new Creator<SportInfo>() {
        @Override
        public SportInfo createFromParcel(Parcel source) {
            return new SportInfo(source);
        }

        @Override
        public SportInfo[] newArray(int size) {
            return new SportInfo[size];
        }
    };
}
