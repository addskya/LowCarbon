package com.mylowcarbon.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * 用户密保信息
 */

public class ProblemInfo implements Parcelable {


    private String id;                      //
    private String uid;                     //
    private String problem_id;              //
    private String answer;                  //
    private String update_time;             //
    private String create_time;             //
     public ProblemInfo(String id, String uid, String problem_id, String answer, String update_time, String create_time) {
        this.id = id;
        this.uid = uid;
        this.problem_id = problem_id;
        this.answer = answer;
        this.update_time = update_time;
        this.create_time = create_time;
    }
    protected ProblemInfo(Parcel in) {
        this.id = in.readString();
        this.uid = in.readString();
        this.problem_id = in.readString();
        this.answer = in.readString();
        this.update_time = in.readString();
        this.create_time = in.readString();
     }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uid);
        dest.writeString(this.problem_id);
        dest.writeString(this.answer);
        dest.writeString(this.update_time);
        dest.writeString(this.create_time);
    }

    public static final Creator<ProblemInfo> CREATOR = new Creator<ProblemInfo>() {
        @Override
        public ProblemInfo createFromParcel(Parcel source) {
            return new ProblemInfo(source);
        }

        @Override
        public ProblemInfo[] newArray(int size) {
            return new ProblemInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


}
