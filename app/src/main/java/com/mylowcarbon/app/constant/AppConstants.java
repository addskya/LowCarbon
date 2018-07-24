package com.mylowcarbon.app.constant;

/**
 *
 * description 常量类
 */
public class AppConstants {
    /*------------------------------------------ URL ------------------------------------------*/

    public static final String BASE_URL = "https://lcl.mylowcarbon.com/";

    public static final String URL_TRADE_RULE_DESC = "https://lcl.mylowcarbon.com/help/query/trade_rule_desc.html";
    public static final String URL_AGREEMENT = "https://lcl.mylowcarbon.com/help/query/user_service_sla.html";
    public static final String URL_COMPLAINTS = "https://lcl.mylowcarbon.com/help/query/complain_desc.html";


    /*------------------------------------------ app文件存储模块  ------------------------------------------*/
    public static final String APP_IMAGE = "image";               //应用图片存储目录名
    public static final String APP_TMP = "temp";                  //应用临时文件目录名
    public static final String PICTURE_DIR = "sdcard/mylowcarbon/pictures/";
    public static final String FILE_DIR = "sdcard/mylowcarbon/recvFiles/";
    /*------------------------------------------ preference存储模块  -------------------------------------*/

    public static final String APP_SHARE = "app_share";                         // 默认preference名字
    public static final String APP_SHARE_USER = "app_user_share";               //用户sharePreference

    /*------------------------------------------  intent -------------------------------------*/
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

    public static final String CARD_USER_NAME = "card_user_name";
    public static final String CARD_NUMBER = "card_number";
    public static final String URL = "url";
    public static final String TRADE_NUMBER = "trade_number";
    public static final String TRADE_PRICE = "trade_price";
    public static final String COIN_ID = "coin_id";
    public static final String ORDER_DETAIL = "order_detail";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_IS_BUYER = "order_is_buyer";
    public static final String ORDER_IS_CREATE = "order_is_create";
    public static final String MYTRADE_TYPE = "mytrade_type";
    public static final String ORDER_NICK_NAME = "order_nick_name";

    /*------------------------------------------ swipeRefreshLayout-------------------------------------*/
    public static final int SWIPEREFRESH_REFRESH_FINISH = 0x001;
    public static final int SWIPEREFRESH_LOAD_FINISH = 0x002;
    public static final int SWIPEREFRESH_REFRESH_START_REFRESH = 0x003;



}
