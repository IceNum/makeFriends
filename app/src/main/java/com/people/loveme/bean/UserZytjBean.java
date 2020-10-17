package com.people.loveme.bean;

/**
 * Created by kxn on 2018/10/29 0029.
 */

public class UserZytjBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540777651
     * data : {"uid":99887,"age":18,"city":"河南省郑州市","height":"160-170","income":"1","edu":"本科","house":"0","car":"0"}
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
         * uid : 99887
         * age : 18
         * city : 河南省郑州市
         * height : 160-170
         * income : 1
         * edu : 本科
         * house : 0
         * car : 0
         */

        private int uid;
        private String age;
        private String city;
        private String height;
        private String income;
        private String edu;
        private String house;
        private String car;
        private String logintime;
        private String score;
        private String is_relaname;
        private String na_place;
        private String religion;
        private String nation;

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIs_relaname() {
            return is_relaname;
        }

        public void setIs_relaname(String is_relaname) {
            this.is_relaname = is_relaname;
        }

        public String getNa_place() {
            return na_place;
        }

        public void setNa_place(String na_place) {
            this.na_place = na_place;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getEdu() {
            return edu;
        }

        public void setEdu(String edu) {
            this.edu = edu;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }
    }
}
