package com.mylowcarbon.app.register.weight;

/**
 * Created by Orange on 18-4-15.
 * Email:addskya@163.com
 */
class WeightEntry {

    private String mWeightName;
    private int mWeightInKg;

    WeightEntry(String weightName,
                       int weightInKg) {
        mWeightName = weightName;
        mWeightInKg = weightInKg;
    }

    int getWeightInKg() {
        return mWeightInKg;
    }

    @Override
    public String toString() {
        return mWeightName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightEntry that = (WeightEntry) o;

        if (mWeightInKg != that.mWeightInKg) return false;
        return mWeightName != null ? mWeightName.equals(that.mWeightName) : that.mWeightName == null;
    }

}
