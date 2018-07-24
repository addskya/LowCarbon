package com.mylowcarbon.app.login;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.mylowcarbon.app.BuildConfig;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 */

public class DeviceParameters {

    // 设备类型（1:手环，2:手机,3:矿机）
    private int device_type = 2;

    // 设备蓝牙地址或是网络mac地址
    private String address = "AA:AA:AA:AA:AA:BB";

    // 设备信号强度，数值
    private int rssi;

    // 设备唯一ID或IMEI号  06605126124857db f3a9c0777f91315a
    private String identifier = "";

    // 设备名称
    private String name = "Android";

    // 备注昵称
    private String nickname = "nickname";

    // 设备版本
    private String version = BuildConfig.VERSION_NAME;

    // 设备连接状态（0：未连接，1:连接中,2：在线）
    private int isconnected;

    // 设备是否支持扩展功能，QQ，mail,sms,wechat（0:不支持，1:支持）
    private int ishasextra;

    // 设备是否支持心率（0:不支持，1:支持）
    private int ishashrm;

    // 设备是否支持血压（0:不支持，1:支持）
    private int ishasblood;

    // 设备是否支持羽毛球功能（0:不支持，1:支持）
    private int ishastennis;

    // 设备是否支持步行 和跑步的区别（0:不支持，1:支持）
    private int ishaswalkrun;

    // 设备是否支持跳绳功能（0:不支持，1:支持）
    private int isskip;

    // 设备是否支持游泳功能（0:不支持，1:支持）
    private int ishasswim;

    // 设备是否支持骑车功能（0:不支持，1:支持）
    private int ishasbicycle;

    // 设备是否支持乒乓球功能（0:不支持，1:支持）
    private int ishastabletennis;

    // 设备是否支持羽毛球功能（0:不支持，1:支持）
    private int ishasbadminton;

    // 设备是否支持网球功能（0:不支持，1:支持）
    private int istennis;

    // 设备是否支持新的距离和卡路里的计算方式（0:不支持，1:支持）
    private int ishasnewcalculate;

    // 设备是否支持gps定位 （0:不支持，1:支持）
    private int ishasgps;

    // 设备是否支持紫外线功能 （0:不支持，1:支持）
    private int ishasuv;

    // 连接设备是否支持升级功能 （0:不支持，1:支持）
    private int ishasupdatefunction;

    /**
     * create the DeviceParameters object
     *
     * @param applicationContext the application context
     * @return DeviceParameters object
     */
    public static DeviceParameters from(@NonNull Context applicationContext) {
        DeviceParameters params = new DeviceParameters();
        params.address = Settings.Secure.getString(
                applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);

//        params.address = "ac38274e616f24f6";
        params.identifier = params.address;
        params.name = Build.PRODUCT;
        params.nickname = Build.DEVICE;


        return params;
    }
}
