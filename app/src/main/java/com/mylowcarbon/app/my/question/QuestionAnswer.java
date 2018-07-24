package com.mylowcarbon.app.my.question;

/**
 * Created by Orange on 18-4-29.
 * Email:addskya@163.com
 *
 * 总觉得这里有点怪, 应该还有一个字段, 问题 content
 */
class QuestionAnswer {
    /**
     * id : 4
     * uid : 77ad49e04b8f11e8a62a938239dde06d
     * problem_id : 1
     * answer : a
     * update_time : 1524996134
     * create_time : 1524995662
     */

    private int id;
    private String uid;
    private int problem_id;
    private String answer;
    private int update_time;
    private int create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
