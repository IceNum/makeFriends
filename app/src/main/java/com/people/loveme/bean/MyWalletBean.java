package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/19 0019.
 */

public class MyWalletBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540868938
     * data : {"success_money":0,"ing_money":0}
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
         * success_money : 0
         * ing_money : 0
         */

        private int success_money;
        private int ing_money;

        public int getSuccess_money() {
            return success_money;
        }

        public void setSuccess_money(int success_money) {
            this.success_money = success_money;
        }

        public int getIng_money() {
            return ing_money;
        }

        public void setIng_money(int ing_money) {
            this.ing_money = ing_money;
        }
    }
}
