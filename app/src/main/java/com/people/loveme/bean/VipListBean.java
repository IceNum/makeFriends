package com.people.loveme.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class VipListBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540805899
     * data : [{"id":1,"viptype":"银牌会员","0_price":998,"n_price":100}]
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
         * viptype : 银牌会员
         * 0_price : 998
         * n_price : 100
         */

        private String id;
        private String viptype;
        @SerializedName("0_price")
        private String _$0_price;
        private String n_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getViptype() {
            return viptype;
        }

        public void setViptype(String viptype) {
            this.viptype = viptype;
        }

        public String get_$0_price() {
            return _$0_price;
        }

        public void set_$0_price(String _$0_price) {
            this._$0_price = _$0_price;
        }

        public String getN_price() {
            return n_price;
        }

        public void setN_price(String n_price) {
            this.n_price = n_price;
        }
    }
}
