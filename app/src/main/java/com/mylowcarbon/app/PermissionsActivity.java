package com.mylowcarbon.app;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mylowcarbon.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 18-2-26.
 * Email:addskya@163.com
 * 这个是Android系统权限的处理类,主要用于:
 * 比如: App需要扫描二维码
 * 一般的步骤是:
 * 1.检查App是否有访问相机的权限
 * 2.如果没有权限则申请提示
 * 3.获取权限成功后,执行扫描二维码动作
 * <p>
 * 比如: App需要读取SDCard文件
 * 一般的步骤是:
 * 1.检查App是否有访问SDCard权限
 * 2.如果没有权限则申请提示
 * 3.获取权限成功后,执行读取文件操作
 * ----------------
 * 基本以上情况流程是一致的,所以提取到基础类中实现,
 * 现在只需要把第三步(权限获取成功后的操作)包装成一个Runnable对象,然后发起此请求即可.
 */

public abstract class PermissionsActivity extends AppCompatActivity
        implements PermissionsTransfer {
    private static final String TAG = "PermissionsActivity";
    private static final int REQUEST_PERMISSIONS = 0x20;

    /**
     * The pending Runnable when request Permissions
     */
    private Runnable mPermissionPendingRunnable;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 避免内存泄漏
        mPermissionPendingRunnable = null;
    }

    /**
     * request system Permissions
     *
     * @param permissions     the permissions array
     * @param pendingRunnable the pending runnable if GRANTED
     */
    @Override
    public final void requestPermissions(@NonNull String[] permissions,
                                         @Nullable Runnable pendingRunnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(permissions)) {
                executePendingRunnable(pendingRunnable);
            } else {
                //Remind the pendingRunnable
                mPermissionPendingRunnable = pendingRunnable;
            }
        } else {
            executePendingRunnable(pendingRunnable);
        }
    }

    /**
     * Execute the pending action
     *
     * @param action the pending action
     */
    private void executePendingRunnable(@Nullable Runnable action) {
        if (action != null) {
            action.run();
            action = null;
        }
    }

    /**
     * check self Permissions
     *
     * @param permissions the permissions
     * @return true if all the permission is GRANTED, or otherwise
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermissions(@NonNull String[] permissions) {
        List<String> needRequestPermissions = new ArrayList<String>(1);
        for (String permission : permissions) {
            if (checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissions.add(permission);
            }
        }
        if (needRequestPermissions.size() > 0) {
            String[] requestPermissions = new String[needRequestPermissions.size()];
            needRequestPermissions.toArray(requestPermissions);
            requestPermissions(requestPermissions, REQUEST_PERMISSIONS);
            return false;
        }
        // 如果已经具有本次所需的权限,则返回True
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                handlePermissionsResult(permissions, grantResults);
                break;
            }
            default: {
                LogUtil.e(TAG, "Unknown Permission request Code.");
            }
        }
    }

    /**
     * handle the permission request result
     *
     * @param permissions  the request permissions
     * @param grantResults the request result array
     */
    private void handlePermissionsResult(@NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        final int requestPermissionLength = permissions.length;
        List<String> requestFailurePermissionsList = new ArrayList<String>(1);
        for (int index = 0; index < requestPermissionLength; index++) {
            String permission = permissions[index];
            boolean isPermissionGranted = grantResults[index] == PackageManager.PERMISSION_GRANTED;
            LogUtil.d(TAG, "RequestPermission:" + permission + " granted:" + isPermissionGranted);
            if (!isPermissionGranted) {
                requestFailurePermissionsList.add(permission);
            }
        }
        if (requestFailurePermissionsList.size() > 0) {
            String[] requestFailurePermissions = new String[requestFailurePermissionsList.size()];
            requestFailurePermissionsList.toArray(requestFailurePermissions);
            handleRequestPermissionsFailure(requestFailurePermissions);
        } else {
            handleRequestPermissionSuccess(permissions);
        }
    }

    /**
     * Handle permissions request success
     */
    private void handleRequestPermissionSuccess(String[] permissions) {
        onRequestPermissionsSuccess(permissions);
        executePendingRunnable(mPermissionPendingRunnable);
        mPermissionPendingRunnable = null;
    }

    /**
     * Call Back When the specified Permission Request Success
     */
    protected void onRequestPermissionsSuccess(String[] permissions) {

    }

    /**
     * handle permissions request failure
     *
     * @param requestFailurePermissions the request failure permissions
     */
    private void handleRequestPermissionsFailure(String[] requestFailurePermissions) {
        mPermissionPendingRunnable = null;
        onRequestPermissionsFailure(requestFailurePermissions);
    }

    /**
     * Call Back when the specified permission request failure
     *
     * @param requestFailurePermissions the request permissions
     */
    protected void onRequestPermissionsFailure(String[] requestFailurePermissions) {

    }
}
