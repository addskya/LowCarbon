package com.mylowcarbon.app.bracelet.own;

import java.util.List;

/**
 * 用户设备
 */
public class Device {


    /**
     * imei : 123456798
     * nickname : nickname
     * device_type : 2
     * mining : [{"steps":0,"clycle":"0","total_energy":0,"total_lcl":"0","date":"2018-04-25"},{"steps":0,"clycle":"0","total_energy":0,"total_lcl":"0","date":"2018-04-24"},{"steps":0,"clycle":"0","total_energy":0,"total_lcl":"0","date":"2018-04-23"}]
     */

    private String imei;
    private String nickname;
    private int device_type;
    private List<Mining> mining;

    public String getImei() {
        return imei;
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

    public List<Mining> getMining() {
        return mining;
    }

    public Mining getTheDayBeforeYesterday() {
        return mining.get(0);
    }

    public Mining getYesterday() {
        return mining.get(1);
    }

    public static class Mining {
        /**
         * steps : 0
         * clycle : 0
         * total_energy : 0
         * total_lcl : 0
         * date : 2018-04-25
         */

        private int steps;
        private int clycle;
        private int total_energy;
        private int total_lcl;
        private String date;

        public int getSteps() {
            return steps;
        }

        public int getClycle() {
            return clycle;
        }

        public int getTotal_energy() {
            return total_energy;
        }

        public int getTotal_lcl() {
            return total_lcl;
        }


        public String getDate() {
            return date;
        }

    }
}
