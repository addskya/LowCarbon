package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 * 关于
 * <p>
 * 1001	参数不正确（可能是参数没传，或者参数格式错误）
 * 11002	服务器内部错误
 * 11003	认证失败
 */
public class About {
    public String logo;
    public List<String> partner;
    public Contact contact;


    public static class Contact {
        public String qq;
        public String wechat;
        public String weibo;
        public String email;

    }
}
