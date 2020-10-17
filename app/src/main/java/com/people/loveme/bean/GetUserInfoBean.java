package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/26 0026.
 */

public class GetUserInfoBean {

    /**
     * code : 1
     * msg : success
     * data : {"id":1,"name":"123","phone":"123","city":"河南","sex":"1","score":12,"isvip":"0","money":12313,"status":"1","regtime":1539834884,"password":"8d4646eb2d7067126eb08adb0672f7bb","puid":null,"headimage":"/1.jpg","nickname":"张三","lastlogintime":1540274843,"dubai":"哈哈哈啊啊好好","biaoqian":"1,2","age":15,"love_status":null,"sex_text":"Sex 1","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-18 11:54:44"}
     */

    private int code;
    private String msg;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 123
         * phone : 123
         * city : 河南
         * sex : 1
         * score : 12
         * isvip : 0
         * money : 12313
         * status : 1
         * regtime : 1539834884
         * password : 8d4646eb2d7067126eb08adb0672f7bb
         * puid : null
         * headimage : /1.jpg
         * nickname : 张三
         * lastlogintime : 1540274843
         * dubai : 哈哈哈啊啊好好
         * biaoqian : 1,2
         * age : 15
         * love_status : null
         * sex_text : Sex 1
         * isvip_text : Isvip 0
         * status_text : Status 1
         * regtime_text : 2018-10-18 11:54:44
         */

        private String id;
        private String name;
        private String phone;
        private String city;
        private String sex;
        private String score;
        private String isvip;
        private String money;
        private String status;
        private String regtime;
        private String password;
        private String puid;
        private String headimage;
        private String nickname;
        private String lastlogintime;
        private String dubai;
        private String biaoqian;
        private String age;
        private String love_status;
        private String sex_text;
        private String isvip_text;
        private String status_text;
        private String regtime_text;
        private String viptime;
        private String likeme_num;
        private String viewme_num;
        private String message_tiaojian;//接受符合条件消息息:0=否,1=是
        private String message_all;//接受所有人消息:0=否,1=是

        public String getLikeme_num() {
            return likeme_num;
        }

        public void setLikeme_num(String likeme_num) {
            this.likeme_num = likeme_num;
        }

        public String getViewme_num() {
            return viewme_num;
        }

        public void setViewme_num(String viewme_num) {
            this.viewme_num = viewme_num;
        }

        public String getMessage_tiaojian() {
            return message_tiaojian;
        }

        public void setMessage_tiaojian(String message_tiaojian) {
            this.message_tiaojian = message_tiaojian;
        }

        public String getMessage_all() {
            return message_all;
        }

        public void setMessage_all(String message_all) {
            this.message_all = message_all;
        }

        public String getViptime() {
            return viptime;
        }

        public void setViptime(String viptime) {
            this.viptime = viptime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIsvip() {
            return isvip;
        }

        public void setIsvip(String isvip) {
            this.isvip = isvip;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPuid() {
            return puid;
        }

        public void setPuid(String puid) {
            this.puid = puid;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLastlogintime() {
            return lastlogintime;
        }

        public void setLastlogintime(String lastlogintime) {
            this.lastlogintime = lastlogintime;
        }

        public String getDubai() {
            return dubai;
        }

        public void setDubai(String dubai) {
            this.dubai = dubai;
        }

        public String getBiaoqian() {
            return biaoqian;
        }

        public void setBiaoqian(String biaoqian) {
            this.biaoqian = biaoqian;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getLove_status() {
            return love_status;
        }

        public void setLove_status(String love_status) {
            this.love_status = love_status;
        }

        public String getSex_text() {
            return sex_text;
        }

        public void setSex_text(String sex_text) {
            this.sex_text = sex_text;
        }

        public String getIsvip_text() {
            return isvip_text;
        }

        public void setIsvip_text(String isvip_text) {
            this.isvip_text = isvip_text;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getRegtime_text() {
            return regtime_text;
        }

        public void setRegtime_text(String regtime_text) {
            this.regtime_text = regtime_text;
        }
    }
}
