package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class UserInfoBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540887503
     * data : {"dongtai_num":0,"likeme_num":0,"viewme_num":4,"rate":90}
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
         * dongtai_num : 0
         * likeme_num : 0
         * viewme_num : 4
         * rate : 90
         */

        private int dongtai_num;
        private int likeme_num;
        private int viewme_num;
        private int rate;

        public int getDongtai_num() {
            return dongtai_num;
        }

        public void setDongtai_num(int dongtai_num) {
            this.dongtai_num = dongtai_num;
        }

        public int getLikeme_num() {
            return likeme_num;
        }

        public void setLikeme_num(int likeme_num) {
            this.likeme_num = likeme_num;
        }

        public int getViewme_num() {
            return viewme_num;
        }

        public void setViewme_num(int viewme_num) {
            this.viewme_num = viewme_num;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }
}
