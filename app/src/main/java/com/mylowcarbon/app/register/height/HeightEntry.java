package com.mylowcarbon.app.register.height;

/**
 * Created by Orange on 18-4-15.
 * Email:addskya@163.com
 */
class HeightEntry {

    // 显示的身高
    private String mHeightName;

    // 实际数字,单位厘米
    private int mHeightInCm;


    HeightEntry(String mHeightName,
                int mHeightInCm) {
        this.mHeightName = mHeightName;
        this.mHeightInCm = mHeightInCm;
    }

    int getHeightInCm() {
        return mHeightInCm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeightEntry that = (HeightEntry) o;

        if (mHeightInCm != that.mHeightInCm) return false;
        return mHeightName.equals(that.mHeightName);
    }

    @Override
    public String toString() {
        return mHeightName;
    }
}
