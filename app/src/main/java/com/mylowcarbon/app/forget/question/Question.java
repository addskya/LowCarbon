package com.mylowcarbon.app.forget.question;

/**
 * Created by Orange on 18-3-22.
 * Email:addskya@163.com
 * <p>
 * 密保问题
 */

public class Question {
    /**
     * id : 1
     * content : 你的父亲的名字是？
     * create_time : 0
     */

    private int id;
    private String content;
    private int create_time;

    public int getId() {
        return id;
    }


    public String getContent() {
        return content;
    }


    public int getCreate_time() {
        return create_time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
