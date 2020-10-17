package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/11/10 0010.
 */

public class AppStartBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1541831676
     * data : ["http://rrl.project.hbsshop.cn/uploads/20181110/8f7a2178bb2b678f65e601743f8e0c3d.png","http://rrl.project.hbsshop.cn/uploads/20181110/353c07191aafea159bffaf00275aa488.jpg"]
     */

    private int code;
    private String msg;
    private String time;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
