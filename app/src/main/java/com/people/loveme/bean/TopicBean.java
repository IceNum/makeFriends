package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/17 0017.
 */

public class TopicBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540803713
     * data : [{"id":1,"name":"分类三"},{"id":2,"name":"分类二"},{"id":3,"name":"分类一"},{"id":4,"name":"123 "}]
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

    public static class DataBean {
        /**
         * id : 1
         * name : 分类三
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
