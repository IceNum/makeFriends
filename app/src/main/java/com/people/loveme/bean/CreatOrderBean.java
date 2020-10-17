package com.people.loveme.bean;

/**
 * Created by kxn on 2018/11/2 0002.
 */

public class CreatOrderBean {

    /**
     * code : 1
     * msg : 添加成功
     * time : 1541137065
     * data : {"uid":"100007","money":"10","order_type":"1","be_dashang_uid":"","vip_opentime":"","type":"","pay_type":"","detail":"","id":"201811021337453203","time":1541137065,"time_text":"2018-11-02 13:37:45","pay_type_text":"","type_text":""}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 100007
         * money : 10
         * order_type : 1
         * be_dashang_uid :
         * vip_opentime :
         * type :
         * pay_type :
         * detail :
         * id : 201811021337453203
         * time : 1541137065
         * time_text : 2018-11-02 13:37:45
         * pay_type_text :
         * type_text :
         */

        private String uid;
        private String money;
        private String order_type;
        private String be_dashang_uid;
        private String vip_opentime;
        private String type;
        private String pay_type;
        private String detail;
        private String id;
        private int time;
        private String time_text;
        private String pay_type_text;
        private String type_text;

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

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getBe_dashang_uid() {
            return be_dashang_uid;
        }

        public void setBe_dashang_uid(String be_dashang_uid) {
            this.be_dashang_uid = be_dashang_uid;
        }

        public String getVip_opentime() {
            return vip_opentime;
        }

        public void setVip_opentime(String vip_opentime) {
            this.vip_opentime = vip_opentime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTime_text() {
            return time_text;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public String getPay_type_text() {
            return pay_type_text;
        }

        public void setPay_type_text(String pay_type_text) {
            this.pay_type_text = pay_type_text;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }
    }
}
