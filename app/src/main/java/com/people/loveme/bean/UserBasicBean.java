package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class UserBasicBean {

    /**
     * data : {"uid":"26","stature":"180","income":null,"education":"博士","academy":"211","profession":"计算机/互联网/通信","marital_status":"0","seek":"恋人"}
     * message :
     * code : 200
     */

    private DataBean data;
    private String message;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * uid : 26
         * height : 180
         * income : null
         * education : 博士
         * academy : 211
         * profession : 计算机/互联网/通信
         * marital_status : 0
         * seek : 恋人
         */

        private String uid;
        private String height;
        private String income;
        private String edu;
        private String academy;
        private String profession;
        private String marital_status;
        private String seek;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String stature) {
            this.height = stature;
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

        public String getAcademy() {
            return academy;
        }

        public void setAcademy(String academy) {
            this.academy = academy;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getMarital_status() {
            return marital_status;
        }

        public void setMarital_status(String marital_status) {
            this.marital_status = marital_status;
        }

        public String getSeek() {
            return seek;
        }

        public void setSeek(String seek) {
            this.seek = seek;
        }
    }
}
