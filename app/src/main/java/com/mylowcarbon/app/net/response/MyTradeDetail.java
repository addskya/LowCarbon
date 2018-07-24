package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 *
 */
public class MyTradeDetail {
    public int last_time;//
    public boolean list_more;//
    public List<MyTradeDetailItem> data;


    public static class MyTradeDetailItem {

        public int id;// 10000",
        public long start_time;// 10000",
        public String order_num;//9867",
        public float number;//"0",
        public int price;// 0

        public String order_sn;//订单号
        public int coin_id;//碳币表id
        public String uid;//用户id
        public int order_status;//订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价）
        public String pay_voucher;//支付凭证
        public int update_time;//
        public int create_time;//
        public int success_trade_num;//成功交易量
        public String nickname;//

        public int type;//（1：买入，2：卖出）






    }
     
}
