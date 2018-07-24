package com.mylowcarbon.app.bracelet.base;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * Created by Orange on 18-4-14.
 * Email:addskya@163.com
 */
public abstract class BaseBraceletActivity extends ActionBarActivity {

    private static final String TAG = "BaseBraceletActivity";
    private static final int REQUEST_ENABLE_BT = 0x50;

    // 请求启用蓝牙
    protected void requestEnableBt() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT: {
                if (resultCode == RESULT_OK) {
                    onEnableBtSuccess();
                } else {
                    onEnableBtFail();
                }
                break;
            }
        }
    }


    protected void onEnableBtSuccess() {
        LogUtil.i(TAG, "onBtEnabled");
    }

    protected void onEnableBtFail() {
        LogUtil.w(TAG, "onEnableBtFail");
    }

}
