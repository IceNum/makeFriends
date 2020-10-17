package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2019/5/13 0013.
 */
public class RechargeBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1557739083
     * data : [{"id":"201904221729383671","uid":100302,"money":2000,"time":1555925378,"pay_type":"3","detail":"充值虚拟币200个","type":"0","order_type":"6","be_dashang_uid":null,"vip_opentime":null,"status":"1","time_text":"2019-04-22 17:29:38","pay_type_text":"Pay_type 3","type_text":"Type 0"}]
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
         * id : 201904221729383671
         * uid : 100302
         * money : 2000
         * time : 1555925378
         * pay_type : 3
         * detail : 充值虚拟币200个
         * type : 0
         * order_type : 6
         * be_dashang_uid : null
         * vip_opentime : null
         * status : 1
         * time_text : 2019-04-22 17:29:38
         * pay_type_text : Pay_type 3
         * type_text : Type 0
         */

        private String id;
        private String uid;
        private String money;
        private String time;
        private String pay_type;
        private String detail;
        private String type;
        private String order_type;
        private String be_dashang_uid;
        private String vip_opentime;
        private String status;
        private String time_text;
        private String pay_type_text;
        private String type_text;

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
