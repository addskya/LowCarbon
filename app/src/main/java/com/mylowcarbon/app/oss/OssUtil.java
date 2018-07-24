package com.mylowcarbon.app.oss;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.mylowcarbon.app.App;
import com.mylowcarbon.app.model.StsInfo;
import com.mylowcarbon.app.sts.StsUtil;

/**
 * Created by wangzheng on 2017/11/22.
 */

public class OssUtil {
    private static final String TAG = "OssUtil";

    private static OssUtil instance;
    private OssService ossService;
    private StsInfo mStsInfo;

    private OssUtil() {
        ossService = initOSS(Config.endpoint, Config.bucket);
    }

    public static OssUtil getInstance() {
        if (instance == null) {
            instance = new OssUtil();
        }
        return instance;
    }

    public OssService getOssService() {
        return ossService;
    }

    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    public OssService initOSS(String endpoint, String bucket) {

        App mApp;
        try {

            mApp = (App) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            mStsInfo = StsUtil.mStsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (mStsInfo == null) {
            return null;
        }


//        移动端是不安全环境，不建议直接使用阿里云主账号ak，sk的方式。建议使用STS方式。具体参
//        https://help.aliyun.com/document_detail/31920.html
//        注意：SDK 提供的 PlainTextAKSKCredentialProvider 只建议在测试环境或者用户可以保证阿里云主账号AK，SK安全的前提下使用。具体使用如下
//        主账户使用方式

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Config.accessKeyId, Config.accessKeySecret);

//        以下是使用STS Sever方式。
//        如果用STS鉴权模式，推荐使用OSSAuthCredentialProvider方式直接访问鉴权应用服务器，token过期后可以自动更新。
//        详见：https://help.aliyun.com/document_detail/31920.html
//        OSSClient的生命周期和应用程序的生命周期保持一致即可。在应用程序启动时创建一个ossClient，在应用程序结束时销毁即可。

//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(mStsInfo.getAccessKeyId(), mStsInfo.getAccessKeySecret(), mStsInfo.getSecurityToken());
        //使用自己的获取STSToken的类
//        MyOSSAuthCredentialsProvider  credentialProvider = new MyOSSAuthCredentialsProvider(Config.STSSERVER);
        String editBucketName = bucket;

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(mApp.getApplicationContext(), endpoint, credentialProvider, conf);
        OSSLog.enableLog();
        return new OssService(oss, editBucketName);

    }

}
