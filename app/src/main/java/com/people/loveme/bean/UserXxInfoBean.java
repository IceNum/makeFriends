package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/26 0026.
 */

public class UserXxInfoBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540620929
     * data : {"uid":1,"car":"1","weight":98,"nation":"汉族","na_place":"河南","have_son":"1","religion":"基督教","constellation":"金牛","shengxiao":"狗"}
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
         * uid : 1
         * car : 1
         * weight : 98
         * nation : 汉族
         * na_place : 河南
         * have_son : 1
         * religion : 基督教
         * constellation : 金牛
         * shengxiao : 狗
         */

        private String uid;
        private String car;
        private String house;
        private String weight;
        private String nation;
        private String na_place;
        private String have_son;
        private String religion;
        private String constellation;
        private String shengxiao;

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getNa_place() {
            return na_place;
        }

        public void setNa_place(String na_place) {
            this.na_place = na_place;
        }

        public String getHave_son() {
            return have_son;
        }

        public void setHave_son(String have_son) {
            this.have_son = have_son;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getShengxiao() {
            return shengxiao;
        }

        public void setShengxiao(String shengxiao) {
            this.shengxiao = shengxiao;
        }
    }
}
