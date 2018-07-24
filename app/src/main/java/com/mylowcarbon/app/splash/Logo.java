package com.mylowcarbon.app.splash;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * app启动图
 */
@Entity
public class Logo {

    @Id
    private Long id;

    @SerializedName("app_start_img")
    private String logoImageUri;

    @Generated(hash = 1075091476)
    public Logo(Long id, String logoImageUri) {
        this.id = id;
        this.logoImageUri = logoImageUri;
    }

    @Generated(hash = 331804212)
    public Logo() {
    }

    public String getLogoImageUri() {
        return logoImageUri;
    }

    public void setLogoImageUri(String logoImageUri) {
        this.logoImageUri = logoImageUri;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
