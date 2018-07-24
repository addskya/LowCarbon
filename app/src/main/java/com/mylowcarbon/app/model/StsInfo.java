package com.mylowcarbon.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 *STS凭证
 */
@Entity
public class StsInfo implements Parcelable {

    @Id
    private String AccessKeyId;
    private String AccessKeySecret;
    private String Expiration;
    private String SecurityToken;

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String securityToken) {
        SecurityToken = securityToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AccessKeyId);
        dest.writeString(this.AccessKeySecret);
        dest.writeString(this.Expiration);
        dest.writeString(this.SecurityToken);

    }

    protected StsInfo(Parcel in) {
        this.AccessKeyId = in.readString();
        this.AccessKeySecret = in.readString();
        this.Expiration = in.readString();
        this.SecurityToken = in.readString();

    }

    @Generated(hash = 1853704356)
    public StsInfo(String AccessKeyId, String AccessKeySecret, String Expiration, String SecurityToken) {
        this.AccessKeyId = AccessKeyId;
        this.AccessKeySecret = AccessKeySecret;
        this.Expiration = Expiration;
        this.SecurityToken = SecurityToken;
    }

    @Generated(hash = 529164869)
    public StsInfo() {
    }

    public static final Creator<StsInfo> CREATOR = new Creator<StsInfo>() {
        @Override
        public StsInfo createFromParcel(Parcel source) {
            return new StsInfo(source);
        }

        @Override
        public StsInfo[] newArray(int size) {
            return new StsInfo[size];
        }
    };
}
