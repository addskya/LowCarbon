package com.mylowcarbon.app.bracelet.bind;

import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 *
 */
class BindBraceletModel implements BindBraceletContract.Model {

    private static final String TAG = "BindBraceletModel";

    // 把这个已经连接过的设备写入到数据库中,后续直接从数据库查询
    @Override
    public void saveConnectedDevice(BleDevices connectedDevice) {

        LogUtil.i(TAG, "writeDevice:" + connectedDevice);
        ModelDao.getInstance()
                .getDaoSession()
                .getBleDevicesDao()
                .insert(connectedDevice);

        List<BleDevices> list = ModelDao.getInstance()
                .getDaoSession()
                .getBleDevicesDao()
                .loadAll();
        LogUtil.i(TAG, "read:" + list);
    }
}
