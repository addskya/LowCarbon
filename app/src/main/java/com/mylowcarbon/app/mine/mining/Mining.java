package com.mylowcarbon.app.mine.mining;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 *
 * 当日的运动数据换算
 */

class Mining {

    /**
     * total_steps : 5190
     * total_energy : 258
     * total_lcl : 0.6919827
     * clycle : 5.12
     */

    private int total_steps;
    private int total_energy;
    private float total_lcl;
    private float clycle;

    public int getTotal_steps() {
        return total_steps;
    }

    public void setTotal_steps(int total_steps) {
        this.total_steps = total_steps;
    }

    public int getTotal_energy() {
        return total_energy;
    }

    public void setTotal_energy(int total_energy) {
        this.total_energy = total_energy;
    }

    public float getTotal_lcl() {
        return total_lcl;
    }

    public void setTotal_lcl(float total_lcl) {
        this.total_lcl = total_lcl;
    }

    public float getClycle() {
        return clycle;
    }

    public void setClycle(float clycle) {
        this.clycle = clycle;
    }

    @Override
    public String toString() {
        return "Mining{" +
                "total_steps=" + total_steps +
                ", total_energy=" + total_energy +
                ", total_lcl=" + total_lcl +
                ", clycle=" + clycle +
                '}';
    }
}
