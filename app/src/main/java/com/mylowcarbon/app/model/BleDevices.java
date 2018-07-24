package com.mylowcarbon.app.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.mylowcarbon.app.BR;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BleDevices extends BaseObservable {
    private String name;

    @Id
    private String address;
    private int rssi;

    private transient boolean isOnline;
    private transient SportInfo beforeYesterday;
    private transient SportInfo yesterday;
    private transient SportInfo today;

    public BleDevices() {
    }

    @Generated(hash = 546137004)
    public BleDevices(String name, String address, int rssi) {
        this.name = name;
        this.address = address;
        this.rssi = rssi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Bindable
    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
        notifyPropertyChanged(BR.isOnline);
    }

    @Override
    public String toString() {
        return "BleDevices{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rssi=" + rssi +
                '}';
    }

    public String toDebugString() {
        return "BleDevices{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rssi=" + rssi +
                ", isOnline=" + isOnline +
                ", beforeYesterday=" + beforeYesterday +
                ", yesterday=" + yesterday +
                ", today=" + today +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BleDevices)) return false;

        BleDevices that = (BleDevices) o;

        return address != null ? address.equals(that.address) : that.address == null;
    }

    public String getDisplay() {
        if (TextUtils.isEmpty(name)) {
            return address;
        }
        return name + "(" + address + ")";
    }

    @Bindable
    public SportInfo getBeforeYesterday() {
        return beforeYesterday;
    }

    public void setBeforeYesterday(SportInfo beforeYesterday) {
        this.beforeYesterday = beforeYesterday;
        notifyPropertyChanged(BR.beforeYesterday);
    }

    @Bindable
    public SportInfo getYesterday() {
        return yesterday;
    }

    public void setYesterday(SportInfo yesterday) {
        this.yesterday = yesterday;
        notifyPropertyChanged(BR.yesterday);
    }

    @Bindable
    public SportInfo getToday() {
        return today;
    }

    public void setToday(SportInfo today) {
        this.today = today;
        notifyPropertyChanged(BR.today);
    }
}
