package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 *
 */
public class Complain {


	public List<Reason> complain;
	public About.Contact contact;

	public static class Reason {
		public int id;
		public String reason;
		public String update_time;
		public String create_time;
	}
}
