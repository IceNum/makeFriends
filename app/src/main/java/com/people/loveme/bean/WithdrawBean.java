package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/5/13 0013.
 */
public class WithdrawBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1557739808
     * data : [{"id":5,"uid":100324,"money":0,"account_name":"申盼盼","time":1557711988,"account":"13693003292","status":"2","time_text":"2019-05-13 09:46:28","status_text":"Status 2"}]
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
         * id : 5
         * uid : 100324
         * money : 0
         * account_name : 申盼盼
         * time : 1557711988
         * account : 13693003292
         * status : 2
         * time_text : 2019-05-13 09:46:28
         * status_text : Status 2
         */

        private String id;
        private String uid;
        private String money;
        private String account_name;
        private String time;
        private String account;
        private String status;
        private String time_text;
        private String status_text;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime_text() {
            return time_text;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }
    }
}
