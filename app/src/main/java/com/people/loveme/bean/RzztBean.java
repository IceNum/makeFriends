package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class RzztBean {

    /**
     * code : 1
     * msg : 查询成功
     * time : 1540860431
     * data : {"house":0,"work":0,"relaname":0,"edu":0,"car":0,"phone":1,"basic_info":1,"five_photo":0,"zanzhu_5":0,"zanzhu_10":0,"zanzhu_20":0}
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
         * house : 0
         * work : 0
         * relaname : 0
         * edu : 0
         * car : 0
         * phone : 1
         * basic_info : 1
         * five_photo : 0
         * zanzhu_5 : 0
         * zanzhu_10 : 0
         * zanzhu_20 : 0
         */

        private String house;
        private String work;
        private String relaname;
        private String edu;
        private String car;
        private String phone;
        private String basic_info;
        private String five_photo;
        private String zanzhu_5;
        private String zanzhu_10;
        private String zanzhu_20;

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getRelaname() {
            return relaname;
        }

        public void setRelaname(String relaname) {
            this.relaname = relaname;
        }

        public String getEdu() {
            return edu;
        }

        public void setEdu(String edu) {
            this.edu = edu;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBasic_info() {
            return basic_info;
        }

        public void setBasic_info(String basic_info) {
            this.basic_info = basic_info;
        }

        public String getFive_photo() {
            return five_photo;
        }

        public void setFive_photo(String five_photo) {
            this.five_photo = five_photo;
        }

        public String getZanzhu_5() {
            return zanzhu_5;
        }

        public void setZanzhu_5(String zanzhu_5) {
            this.zanzhu_5 = zanzhu_5;
        }

        public String getZanzhu_10() {
            return zanzhu_10;
        }

        public void setZanzhu_10(String zanzhu_10) {
            this.zanzhu_10 = zanzhu_10;
        }

        public String getZanzhu_20() {
            return zanzhu_20;
        }

        public void setZanzhu_20(String zanzhu_20) {
            this.zanzhu_20 = zanzhu_20;
        }
    }
}
