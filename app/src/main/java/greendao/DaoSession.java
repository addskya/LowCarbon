package greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.model.SportInfo;
import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.splash.Logo;

import greendao.BleDevicesDao;
import greendao.UserInfoDao;
import greendao.SportInfoDao;
import greendao.StsInfoDao;
import greendao.LogoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bleDevicesDaoConfig;
    private final DaoConfig userInfoDaoConfig;
    private final DaoConfig sportInfoDaoConfig;
    private final DaoConfig stsInfoDaoConfig;
    private final DaoConfig logoDaoConfig;

    private final BleDevicesDao bleDevicesDao;
    private final UserInfoDao userInfoDao;
    private final SportInfoDao sportInfoDao;
    private final StsInfoDao stsInfoDao;
    private final LogoDao logoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bleDevicesDaoConfig = daoConfigMap.get(BleDevicesDao.class).clone();
        bleDevicesDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        sportInfoDaoConfig = daoConfigMap.get(SportInfoDao.class).clone();
        sportInfoDaoConfig.initIdentityScope(type);

        stsInfoDaoConfig = daoConfigMap.get(StsInfoDao.class).clone();
        stsInfoDaoConfig.initIdentityScope(type);

        logoDaoConfig = daoConfigMap.get(LogoDao.class).clone();
        logoDaoConfig.initIdentityScope(type);

        bleDevicesDao = new BleDevicesDao(bleDevicesDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);
        sportInfoDao = new SportInfoDao(sportInfoDaoConfig, this);
        stsInfoDao = new StsInfoDao(stsInfoDaoConfig, this);
        logoDao = new LogoDao(logoDaoConfig, this);

        registerDao(BleDevices.class, bleDevicesDao);
        registerDao(UserInfo.class, userInfoDao);
        registerDao(SportInfo.class, sportInfoDao);
        registerDao(StsInfo.class, stsInfoDao);
        registerDao(Logo.class, logoDao);
    }
    
    public void clear() {
        bleDevicesDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
        sportInfoDaoConfig.clearIdentityScope();
        stsInfoDaoConfig.clearIdentityScope();
        logoDaoConfig.clearIdentityScope();
    }

    public BleDevicesDao getBleDevicesDao() {
        return bleDevicesDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public SportInfoDao getSportInfoDao() {
        return sportInfoDao;
    }

    public StsInfoDao getStsInfoDao() {
        return stsInfoDao;
    }

    public LogoDao getLogoDao() {
        return logoDao;
    }

}
