package com.mylowcarbon.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.sts.StsUtil;
import com.mylowcarbon.app.utils.LogUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 */

public class App extends Application {

    private static final String TAG = "App";

    //钱包rate
    private float mRate;



    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
        //初始化zxing
        ZXingLibrary.initDisplayOpinion(this);
        ModelDao.getInstance().setUpDao(this, null);
        Fresco.initialize(this);
        //初始化极光IM
        JMessageUtil.init(this);
        StsUtil.init();
     }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.w(TAG, "onTrimMemory,level:" + level);
    }


    public float getRate() {
        return mRate;
    }

    public void setRate(float mRate) {
        this.mRate = mRate;
    }


}
