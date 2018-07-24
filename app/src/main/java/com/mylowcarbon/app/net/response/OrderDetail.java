package com.mylowcarbon.app.net.response;

import java.io.Serializable;

/**
 *
 */
public class OrderDetail implements Serializable{
	public int id;
  	public String order_sn;// "2018041121084042989",
	public int coin_id;// 1,
	public String uid;// "77e96dd0-3225-11e8-ad1c-e18b0b746d74",
	public int number;//
	public int order_status;// 订单状态（订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价））
	public long create_time;// 1523452120,
	public int order_id;// 3
	public String nickname;// 3
	public String pay_voucher;// 3
	public int price;// 3
	public String pay_type_info;//
	public Comment comment_info;//
	public String mobile;//


	public static class Comment {
		public int id;//
		public int coin_order_id;//
		public int role_type;//
		public int comment_type;//评价类型（1：好评，2：中评，3：差评）
		public String remark;//
		public int create_time;//
	}

}
