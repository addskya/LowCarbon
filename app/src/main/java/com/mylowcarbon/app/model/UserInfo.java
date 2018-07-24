package com.mylowcarbon.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Orange on 18-3-8.
 * Email:addskya@163.com
 */
@Entity
public class UserInfo implements Parcelable {

    @Id
    private Long mSeq;

    private String id;                      // 用户id
    private int status;                  // 状态（-1：账号异常，0：未实名认证，1：实名认证审核中，2：已实名认证）
    private String salt;                 // 自检码
    private String password;             // 加密后的密码
    private String nickname;             // 昵称
    private String avatar;               // 头像
    private String mobile;               // 电话号码
    private String email;                // 邮箱
    private String invitation_code;      // 邀请码
    private String access_token;         // 访问凭据
    private int errorlogin_time;         // 禁止登录开始时间
    private int error_count;             // 密码连续输入错误次数
    private int update_time;             // 更新时间
    private int create_time;             // 创建时间
    private String wallet_address;       // 钱包地址
    private int pay_type;                // 默认收款方式（1：支付宝，2：微信，3：银行卡）
    private int gender;                  // 性别（0：未知，1：男，2：女）
    private int height;                  //
    private float weight;                //
    private String keystore;             //
    private String pay_pwd;              //

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public int getErrorlogin_time() {
        return errorlogin_time;
    }

    public int getError_count() {
        return error_count;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public String getWallet_address() {
        return wallet_address;
    }

    public void setWallet_address(String wallet_address) {
        this.wallet_address = wallet_address;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public UserInfo(String id, int status, String salt, String password, String nickname,
                    String avatar, String mobile, String email, String invitation_code,
                    String access_token, int errorlogin_time, int error_count,
                    int update_time, int create_time, String wallet_address,
                    int pay_type,int height,float weight,String keystore,String pay_pwd) {
        this.id = id;
        this.status = status;
        this.salt = salt;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.mobile = mobile;
        this.email = email;
        this.invitation_code = invitation_code;
        this.access_token = access_token;
        this.errorlogin_time = errorlogin_time;
        this.error_count = error_count;
        this.update_time = update_time;
        this.create_time = create_time;
        this.wallet_address = wallet_address;
        this.pay_type = pay_type;
        this.height = height;
        this.weight = weight;
        this.keystore = keystore;
        this.pay_pwd = pay_pwd;
    }

    public UserInfo() {
    }

    public Long getMSeq() {
        return this.mSeq;
    }

    public void setMSeq(Long mSeq) {
        this.mSeq = mSeq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setErrorlogin_time(int errorlogin_time) {
        this.errorlogin_time = errorlogin_time;
    }

    public void setError_count(int error_count) {
        this.error_count = error_count;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }


    @Generated(hash = 1043115392)
    public UserInfo(Long mSeq, String id, int status, String salt, String password, String nickname,
            String avatar, String mobile, String email, String invitation_code, String access_token,
            int errorlogin_time, int error_count, int update_time, int create_time, String wallet_address,
            int pay_type, int gender, int height, float weight, String keystore, String pay_pwd) {
        this.mSeq = mSeq;
        this.id = id;
        this.status = status;
        this.salt = salt;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.mobile = mobile;
        this.email = email;
        this.invitation_code = invitation_code;
        this.access_token = access_token;
        this.errorlogin_time = errorlogin_time;
        this.error_count = error_count;
        this.update_time = update_time;
        this.create_time = create_time;
        this.wallet_address = wallet_address;
        this.pay_type = pay_type;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.keystore = keystore;
        this.pay_pwd = pay_pwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mSeq);
        dest.writeString(this.id);
        dest.writeInt(this.status);
        dest.writeString(this.salt);
        dest.writeString(this.password);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.mobile);
        dest.writeString(this.email);
        dest.writeString(this.invitation_code);
        dest.writeString(this.access_token);
        dest.writeInt(this.errorlogin_time);
        dest.writeInt(this.error_count);
        dest.writeInt(this.update_time);
        dest.writeInt(this.create_time);
        dest.writeString(this.wallet_address);
        dest.writeInt(this.pay_type);
        dest.writeInt(this.gender);
        dest.writeInt(this.height);
        dest.writeFloat(this.weight);
        dest.writeString(this.keystore);
        dest.writeString(this.pay_pwd);
    }

    protected UserInfo(Parcel in) {
        this.mSeq = (Long) in.readValue(Long.class.getClassLoader());
        this.id = in.readString();
        this.status = in.readInt();
        this.salt = in.readString();
        this.password = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.mobile = in.readString();
        this.email = in.readString();
        this.invitation_code = in.readString();
        this.access_token = in.readString();
        this.errorlogin_time = in.readInt();
        this.error_count = in.readInt();
        this.update_time = in.readInt();
        this.create_time = in.readInt();
        this.wallet_address = in.readString();
        this.pay_type = in.readInt();
        this.gender = in.readInt();
        this.height = in.readInt();
        this.weight = in.readFloat();
        this.keystore = in.readString();
        this.pay_pwd = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
