package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/10/13 0013.
 */

public class CreditRecordBean  {

    /**
     * data : [{"title":"赞助10元","moeny":"+5"},{"title":"赞助20元","moeny":"+10"}]
     * message : 登录成功
     * code : 200
     */

    private String message;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 赞助10元
         * moeny : +5
         */

        private String title;
        private String moeny;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMoeny() {
            return moeny;
        }

        public void setMoeny(String moeny) {
            this.moeny = moeny;
        }
    }
}
