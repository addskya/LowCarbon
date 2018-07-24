package com.mylowcarbon.app.exchange.fragment;

import java.util.List;

/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
public class ExchangeResp {

    /**
     * date : 2018-04-10
     * lcl : 0
     * uid : 77e96dd0-3225-11e8-ad1c-e18b0b746d74
     * wallet : xxxxxxxxxxxxxxxxxxxx
     * device_id : ["BDAF6B4D-5DC0-4AEF-BCF8-6C7EFC94DE97"]
     * exchangetId : 5190e130-3e1b-11e8-8f5e-4bba6c840528
     * status : 0
     */

    private String date;
    private String lcl;
    private String uid;
    private String wallet;
    private String exchangetId;
    private int status;  // 兑换状态(0:兑换中,1:兑换成功,2:兑换失败)
    private List<String> device_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLcl() {
        return lcl;
    }

    public void setLcl(String lcl) {
        this.lcl = lcl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getExchangetId() {
        return exchangetId;
    }

    public void setExchangetId(String exchangetId) {
        this.exchangetId = exchangetId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getDevice_id() {
        return device_id;
    }

    public void setDevice_id(List<String> device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "ExchangeResp{" +
                "date='" + date + '\'' +
                ", lcl='" + lcl + '\'' +
                ", uid='" + uid + '\'' +
                ", wallet='" + wallet + '\'' +
                ", exchangetId='" + exchangetId + '\'' +
                ", status=" + status +
                ", device_id=" + device_id +
                '}';
    }
}
