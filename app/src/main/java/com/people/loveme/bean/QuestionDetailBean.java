package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class QuestionDetailBean {

    /**
     * familiar_issue_id : 1
     * title : 问题1
     * content : <p>问题12晚上我大所多</p>
     * add_time : 1533714838
     */

    private String familiar_issue_id;
    private String title;
    private String content;
    private String add_time;

    public String getFamiliar_issue_id() {
        return familiar_issue_id;
    }

    public void setFamiliar_issue_id(String familiar_issue_id) {
        this.familiar_issue_id = familiar_issue_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
