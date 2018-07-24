package com.mylowcarbon.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mylowcarbon.app.PermissionsActivity;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 服务类
 *
 */
public class BluetoothService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 服务创建的时候调用
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        System.out.println("=========BluetoothService onCreate======");
    }
    /**
     * 服务启动的时候调用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        System.out.println("=========BluetoothService onStartCommand======");
        return super.onStartCommand(intent, flags, startId);
    }
    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("=========BluetoothService onDestroy======");
        super.onDestroy();
    }
}
