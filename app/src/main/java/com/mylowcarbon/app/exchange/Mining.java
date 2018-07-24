package com.mylowcarbon.app.exchange;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
public class Mining implements Parcelable {
    /**
     * imei : BDAF6B4D-5DC0-4AEF-BCF8-6C7EFC94DE97
     * nickname :
     * device_type : 1
     * steps : 0
     * clycle : 0
     * total_energy : 0
     * total_lcl : 0
     * lcl_status : 0
     */

    private String imei;
    private String nickname;
    private int device_type;
    private int steps;
    private int clycle;
    private int total_energy;
    private int total_lcl;
    private int lcl_status;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getClycle() {
        return clycle;
    }

    public void setClycle(int clycle) {
        this.clycle = clycle;
    }

    public int getTotal_energy() {
        return total_energy;
    }

    public void setTotal_energy(int total_energy) {
        this.total_energy = total_energy;
    }

    public int getTotal_lcl() {
        return total_lcl;
    }

    public void setTotal_lcl(int total_lcl) {
        this.total_lcl = total_lcl;
    }

    public int getLcl_status() {
        return lcl_status;
    }

    public void setLcl_status(int lcl_status) {
        this.lcl_status = lcl_status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imei);
        dest.writeString(this.nickname);
        dest.writeInt(this.device_type);
        dest.writeInt(this.steps);
        dest.writeInt(this.clycle);
        dest.writeInt(this.total_energy);
        dest.writeInt(this.total_lcl);
        dest.writeInt(this.lcl_status);
    }

    public Mining() {
    }

    protected Mining(Parcel in) {
        this.imei = in.readString();
        this.nickname = in.readString();
        this.device_type = in.readInt();
        this.steps = in.readInt();
        this.clycle = in.readInt();
        this.total_energy = in.readInt();
        this.total_lcl = in.readInt();
        this.lcl_status = in.readInt();
    }

    public static final Parcelable.Creator<Mining> CREATOR = new Parcelable.Creator<Mining>() {
        @Override
        public Mining createFromParcel(Parcel source) {
            return new Mining(source);
        }

        @Override
        public Mining[] newArray(int size) {
            return new Mining[size];
        }
    };
}