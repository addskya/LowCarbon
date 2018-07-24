package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 * 我的推荐
 */
public class MyRecommend {

    public String invitation_code;//邀请码
    public String recommend_profit;//总收益
    public String recommend_award_desc;//推荐有奖说明
    public boolean list_more;//
    public int last_id;
    public List<MyRecommendItem> data;

    /**
     * 推荐item
     */
    public class MyRecommendItem {

        public int id;//
        public int level;//（1：一级用户，2:二级用户）
        public String avatar;//
        public String nickname;//
        public int status;//状态（-1：账号异常，0：未实名认证，1：实名认证审核中，2：已实名认证）


    }
}
