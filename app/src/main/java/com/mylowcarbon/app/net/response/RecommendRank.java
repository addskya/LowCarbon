package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 * 推荐item
 */
public class RecommendRank {

    public boolean list_more;//
    public int last_id;
    public RecommendRankItem current;
    public List<RecommendRankItem> data;

    /**
     * 推荐排名item
     */
    public static class RecommendRankItem {
        public int rank;//
        public int sum_level_1;//
        public int sum_level_2;//
        public int sum_level_3;//
        public int id;//
        public int status;//
        public String uid;//
        public String salt;//
        public String password;//
        public String nickname;//
        public String avatar;//
        public String mobile;//
        public String email;//
        public String invitation_code;//
        public String access_token;//
        public int errorlogin_time;//
        public int error_count;//
        public int auth_time;//
        public int update_time;//
        public int create_time;//

        public RecommendRankItem(int sum_level_1, int sum_level_2, int sum_level_3, String nickname) {
            this.sum_level_1 = sum_level_1;
            this.sum_level_2 = sum_level_2;
            this.sum_level_3 = sum_level_3;
            this.nickname = nickname;
        }
    }
}
