package com.mylowcarbon.app.jiguang;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.utils.MD5Util;
import com.mylowcarbon.app.utils.ToastUtil;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.api.BasicCallback;

/**
 * JMessageUtil
 */
public class JMessageUtil {
    private static final String TAG = "JMessageUtil";
    private static String SAMPLE_CONFIGS = "lowcarbon_configs";
    public static boolean isImLogin = false;
    public static Context context;

    /**
     * 初始化
     */
    public static void init(final Context context) {
        JMessageUtil.context = context;
        JMessageClient.init(context);
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        JMessageClient.setDebugMode(true);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(context);
        UserInfo userInfo = ModelDao.getInstance().getUserInfo();
        if (userInfo == null) {
            return;
        }

        JMessageClient.getUserInfo(userInfo.getMobile(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {

            }
        });
        //注册极光
//        register(userInfo.getMobile());
        //登录注册
        login(userInfo.getMobile());

    }

    /**
     * 登录
     */
    public static void login(String userId) {
        String password = MD5Util.getMD5String(MD5Util.getMD5String(userId));
        Log.e(TAG, "login userId = " + userId + " password : " + password);
        JMessageClient.login(userId, password, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                Log.e(TAG, "JMessageUtil.login  status = " + status);

                if (status == 0) {

                    ToastUtil.showShort(context, "极光IM登录成功");

                    isImLogin = true;
                } else {
                    isImLogin = false;
                }
            }
        });
    }

    /**
     * 登录
     */
    public static void login(String userId, BasicCallback callback) {
        String password = MD5Util.getMD5String(MD5Util.getMD5String(userId));
        Log.e(TAG, "login userId = " + userId + " password : " + password);
        JMessageClient.login(userId, password, callback);
    }

    /**
     * 注册
     */
    public static void register(String userId) {
        String password = MD5Util.getMD5String(MD5Util.getMD5String(userId));
        Log.e(TAG, "register userId = " + userId + " password : " + password);
        JMessageClient.register(userId, password, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                Log.e(TAG, "JMessageUtil.register  status = " + status);
                if (status == 0) {

                }
            }
        });
    }

    /**
     * 注册
     */
    public static void register(String userId, BasicCallback callback) {
        String password = MD5Util.getMD5String(MD5Util.getMD5String(userId));
        Log.e(TAG, "register userId = " + userId + " password : " + password);
        JMessageClient.register(userId, password, callback);
    }

    /**
     * 登出
     */
    public static void logout() {
        JMessageClient.logout();
    }

    /**
     * 获取用户信息
     */

    public static void getUserInfo(String username, String appKey, GetUserInfoCallback callback) {
        JMessageClient.getUserInfo(username, appKey, callback);
    }

    /**
     * 修改昵称
     */
    public static void updateNickName(String nickname) {
        cn.jpush.im.android.api.model.UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
            return;
        }
        myInfo.setNickname(nickname);
        JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname, myInfo, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.e(TAG, "JMessageUtil.updateNickName  status = " + responseCode);

                if (responseCode == 0) {
                    Toast.makeText(context, "极光IM修改昵称成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "极光IM修改昵称失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 修改密码
     * (暂时弃用 因为修改密码涉及到旧密码 而忘记密码找回密码时没有旧密码 所以 极光密码就用两次MD5生成)
     */
    public static void updateUserPassword(String oldPassword, String newPassword) {

        JMessageClient.updateUserPassword(oldPassword, newPassword, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.e(TAG, "JMessageUtil.updateNickName  status = " + responseCode);
                if (responseCode == 0) {
                    Toast.makeText(context, "极光IM修改密码成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "极光IM修改密码失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 修改头像
     */
    public static void updateUserAvatar(String path) {

        JMessageClient.updateUserAvatar(new File(path), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.e(TAG, "JMessageUtil.updateUserAvatar  status = " + responseCode);
                if (responseCode == 0) {
                    Toast.makeText(context, "极光IM修改头像成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "极光IM修改头像失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
