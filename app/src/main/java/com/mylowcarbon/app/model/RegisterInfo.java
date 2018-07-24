package com.mylowcarbon.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by orange on 18-3-25.
 * Email:addskya@163.com
 * The Register Response Bean
 * <p>
 * <p>
 * <p>
 * <p>
 * uid             用户id
 * mobile          用户手机号
 * access_token    访问凭证
 * <p>
 * <p>
 * <p>
 * 1001         参数不正确（可能是参数没传，或者参数格式错误）
 * 1002         服务器内部错误
 * 1003         授权失败
 * 2002         该手机号已被注册
 */

public class RegisterInfo implements Parcelable {

    private String uid;
    private String mobile;
    private String access_token;

    public RegisterInfo() {
    }

    public String getUid() {
        return uid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAccess_token() {
        return access_token;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "uid='" + uid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.mobile);
        dest.writeString(this.access_token);
    }

    protected RegisterInfo(Parcel in) {
        this.uid = in.readString();
        this.mobile = in.readString();
        this.access_token = in.readString();
    }

    public static final Parcelable.Creator<RegisterInfo> CREATOR = new Parcelable.Creator<RegisterInfo>() {
        @Override
        public RegisterInfo createFromParcel(Parcel source) {
            return new RegisterInfo(source);
        }

        @Override
        public RegisterInfo[] newArray(int size) {
            return new RegisterInfo[size];
        }
    };
}
