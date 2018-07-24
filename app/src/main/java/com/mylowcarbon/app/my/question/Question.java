package com.mylowcarbon.app.my.question;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */
public class Question {


    /**
     * id : 1
     * content : 你的父亲的名字是？
     * create_time : 0
     */

    private int id;
    private String content;
    private long create_time;

    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
