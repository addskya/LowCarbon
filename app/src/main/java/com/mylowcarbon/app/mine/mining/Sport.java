package com.mylowcarbon.app.mine.mining;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 *
 * 每日的运动数据
 */

class Sport {

    /**
     * steps : 10980
     * clycle : 10.1
     * burn : 889
     * date : 2018-04-22
     */

    private int steps;
    private float clycle;
    private int burn;
    private String date;

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public float getClycle() {
        return clycle;
    }

    public void setClycle(float clycle) {
        this.clycle = clycle;
    }

    public int getBurn() {
        return burn;
    }

    public void setBurn(int burn) {
        this.burn = burn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
