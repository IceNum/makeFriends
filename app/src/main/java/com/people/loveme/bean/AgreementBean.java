package com.people.loveme.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class AgreementBean implements Serializable{


    /**
     * code : 1
     * msg : 获取成功
     * time : 1542353933
     * data : [{"name":"法律声明","url":"http://rrl.project.hbsshop.cn/xieyi/1.html"},{"name":"注册协议","url":"http://rrl.project.hbsshop.cn/xieyi/2.html"}]
     */

    private int code;
    private String msg;
    private String time;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * name : 法律声明
         * url : http://rrl.project.hbsshop.cn/xieyi/1.html
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
