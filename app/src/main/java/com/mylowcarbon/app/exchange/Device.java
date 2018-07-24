package com.mylowcarbon.app.exchange;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 *
 * 这是按日期取设备的数据体, 只用于Exchange中
 */
public class Device implements Parcelable {

    /**
     * date : 2018-04-11
     * date_totoal_lcl : 22.71200903
     * date_totoal_energy : 42032
     * mining : [{"imei":"BDAF6B4D-5DC0-4AEF-BCF8-6C7EFC94DE97","nickname":"","device_type":1,"steps":0,"clycle":0,"total_energy":0,"total_lcl":0,"lcl_status":0}]
     */

    private String date;
    private double date_totoal_lcl;
    private int date_totoal_energy;
    private List<Mining> mining;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDate_totoal_lcl() {
        return date_totoal_lcl;
    }

    public void setDate_totoal_lcl(double date_totoal_lcl) {
        this.date_totoal_lcl = date_totoal_lcl;
    }

    public int getDate_totoal_energy() {
        return date_totoal_energy;
    }

    public void setDate_totoal_energy(int date_totoal_energy) {
        this.date_totoal_energy = date_totoal_energy;
    }

    public List<Mining> getMining() {
        return mining;
    }

    public void setMining(List<Mining> mining) {
        this.mining = mining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeDouble(this.date_totoal_lcl);
        dest.writeInt(this.date_totoal_energy);
        dest.writeTypedList(this.mining);
    }

    public Device() {
    }

    protected Device(Parcel in) {
        this.date = in.readString();
        this.date_totoal_lcl = in.readDouble();
        this.date_totoal_energy = in.readInt();
        this.mining = in.createTypedArrayList(Mining.CREATOR);
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
