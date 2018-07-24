package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 * 我的钱包信息
 */
public class MyWallet {

    public float surplus_amount;//余额
    public String wallet_address;//钱包地址
    public float income;//收入
    public float transfer;//转出
    public int last_id;
    public boolean list_more;
    public List<TransferItem> data;

    /**
     * 转账信息
     */
    public static class TransferItem {
        public int id;
        public String transfer_uid;//转账人的uid
        public String receive_uid;//收款人的uid
        public float amount;//数量
        public int create_time;//创建时间
        public int type;//类型（1：转出，2：收入）

        public TransferItem(int id, String transfer_uid, String receive_uid, float amount, int create_time, int type) {
            this.id = id;
            this.transfer_uid = transfer_uid;
            this.receive_uid = receive_uid;
            this.amount = amount;
            this.create_time = create_time;
            this.type = type;
        }
    }
     
}
