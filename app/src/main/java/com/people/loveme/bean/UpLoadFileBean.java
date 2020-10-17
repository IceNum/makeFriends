package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class UpLoadFileBean {

    /**
     * data : 文件路径
     * message :
     * code : 200
     */

    private String data;
    private String message;
    private int code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
