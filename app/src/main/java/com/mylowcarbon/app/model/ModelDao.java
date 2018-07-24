package com.mylowcarbon.app.model;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.greendao.database.Database;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.UserInfoDao;

/**
 * Created by Orange on 18-4-1.
 * Email:addskya@163.com
 */
public class ModelDao {

    private static final String TAG = "ModelDao";

    // ENCRYPTED the DB
    private static final boolean ENCRYPTED = false;

    private static ModelDao sInstance = new ModelDao();

    public static ModelDao getInstance() {
        return sInstance;
    }

    private DaoSession mDaoSession;
    // 当前的登录用户
    private UserInfo mUserInfo;
    // 应用的sts凭证
    private StsInfo mStsInfo;

    /**
     * setUp the GreenDao DB
     *
     * @param app the application
     */
    public void setUpDao(@NonNull Application app,
                         @Nullable Runnable action) {
        // 注意注意注意
        // 目前这里用的是 Dev类型的数据库,每次升级数据库表都会删除所有表,
        // 导致的问题是: 一旦升级数据库后,就需要重新登录之类的.所以需要自己写升级
        // 当你发现这个问题的时候,可以向  http://greenrobot.org/greendao/ 发起帮助,
        // 但是, 官方一旦很忙, 当然,你可以问问 addskya@163.com
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app,
                ENCRYPTED ? "lowcarbon-db-encrypted" : "lowcarbon-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") :
                helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserInfo = mDaoSession.getUserInfoDao().queryBuilder().unique();

        // UserInfo只能存在一条数据,
        mUserInfo = mDaoSession.getUserInfoDao()
                .queryBuilder()
                .unique();
        mStsInfo = mDaoSession.getStsInfoDao()
                .queryBuilder()
                .unique();

        if (action != null) {
            action.run();
        }
    }

    public DaoSession getDaoSession() {
        assert mDaoSession != null;
        return mDaoSession;
    }

    public boolean isLogin() {
        return mUserInfo != null;
    }

    /**
     * 登录成功,保存登录数据
     *
     * @param user UserInfo
     */
    public void login(@NonNull UserInfo user) {
        UserInfoDao dao = getDaoSession()
                .getUserInfoDao();
        dao.deleteAll();
        dao.save(user);
        mUserInfo = user;
    }

    /**
     * 注销登录,清除数据
     */
    public void logout() {
        mDaoSession.getUserInfoDao().deleteAll();
        mUserInfo = null;

        mDaoSession.getBleDevicesDao().deleteAll();
    }

    public StsInfo getmStsInfo() {
        if (mStsInfo == null) {
            mStsInfo = mDaoSession.getStsInfoDao().queryBuilder().unique();
        }
        return mStsInfo;
    }

    public void updateStsInfo(@NonNull StsInfo stsInfo) {
        mDaoSession.getStsInfoDao().deleteAll();
        mDaoSession.getStsInfoDao().insert(stsInfo);
    }

    private void updateUserInfo(@NonNull UserInfo userInfo) {
        mDaoSession.getUserInfoDao().deleteAll();
        mDaoSession.getUserInfoDao().insert(userInfo);
    }

    public void modifyNickname(CharSequence nickname) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setNickname(String.valueOf(nickname));
        updateUserInfo(mUserInfo);
    }

    public void modifyEmail(CharSequence email) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setEmail(String.valueOf(email));
        updateUserInfo(mUserInfo);
    }

    public void modifyAvatar(CharSequence url) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setAvatar(String.valueOf(url));
        updateUserInfo(mUserInfo);
    }

    public void modifyGender(int gender) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setGender(gender);
        updateUserInfo(mUserInfo);
    }

    public void modifyHeight(int heightInCm) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setHeight(heightInCm);
        updateUserInfo(mUserInfo);
    }

    public void modifyWeight(int weightInKg) {
        if (mUserInfo == null) {
            throw new IllegalStateException("The userInfo is null");
        }
        mUserInfo.setWeight(weightInKg);
        updateUserInfo(mUserInfo);
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

}
