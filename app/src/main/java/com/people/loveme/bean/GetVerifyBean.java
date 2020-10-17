package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class GetVerifyBean {

    /**
     * data : 405430
     * message : 获取验证码成功
     * code : 200
     */

    private int data;
    private String message;
    private int code;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
