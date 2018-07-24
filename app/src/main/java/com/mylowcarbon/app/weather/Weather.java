package com.mylowcarbon.app.weather;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 */

public class Weather {

    private String wendu;
    private String type;
    private String icon;

    String getWendu() {
        return wendu;
    }

    public String getType() {
        return type;
    }

    String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "wendu='" + wendu + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
