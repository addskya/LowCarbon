package com.mylowcarbon.app.oss;

/**
 * Created by wangzheng on 2017/11/22.
 */

public class Config {

    // To run the sample correctly, the following variables must have valid values.
    // The endpoint value below is just the example. Please use proper value according to your region

    // 访问的endpoint地址
    public static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    //callback 测试地址
    public static final String callbackAddress = "http://oss-demo.aliyuncs.com:23450";
    // STS 鉴权服务器地址，使用前请参照文档 https://help.aliyun.com/document_detail/31920.html 介绍配置STS 鉴权服务器地址。
    // 或者根据工程sts_local_server目录中本地鉴权服务脚本代码启动本地STS 鉴权服务器。详情参见sts_local_server 中的脚本内容。
    public static final String STSSERVER = "http://lcf.test.viecs.com/common/get-sts";//STS 地址

    public static final String uploadFilePath = "https://lcfaction.oss-cn-hangzhou.aliyuncs.com";
    public static final String bucket = "lcfaction";
    public static final String accessKeyId = "LTAIpNm41dFkvrMU";
    public static final String accessKeySecret = "bqFV8ssVTcSkn9fq1Pem1AkTiKyYA7";


    public static final int UPLOAD_START = 1;
    public static final int UPLOAD_END = 2;
    public static final int UPLOAD_SUC = 3;
    public static final int UPLOAD_LIST_SUC = 4;
    public static final int UPLOAD_LIST_END = 5;
    public static final int UPLOAD_FAIL = 6;

}
