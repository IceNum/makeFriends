package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/10/12 0012.
 */

public class UserMeetBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540984506
     * data : [{"id":1,"name":"123","phone":"123","city":"","sex":"2","score":45,"viptime":null,"isvip":"0","money":300,"status":"1","regtime":1539834884,"password":"8d4646eb2d7067126eb08adb0672f7bb","puid":null,"headimage":"/1.jpg","nickname":"张三","lastlogintime":1540274843,"dubai":"哈哈哈啊啊好好","biaoqian":"1,2","age":15,"love_status":null,"openid":null,"type":"0","message_tiaojian":"1","message_all":"1","rong_token":null,"user_longitude":"1.01","user_latitude":"1.01","sex_text":"Sex 2","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-18 11:54:44"},{"id":100008,"name":null,"phone":"17329429078","city":"","sex":"2","score":null,"viptime":null,"isvip":"0","money":null,"status":"1","regtime":1540955953,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181031/1fc51129eadee8401910acfade5187c1.png","nickname":"小花","lastlogintime":null,"dubai":"很好","biaoqian":"","age":93,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"25Xai9/eAHKDooGO5s3L5zmuAX9P8WqTEHr23Re19Io5JS3kvqLvsiDNO4/7bAq5Va0+Cqwb/Sryy+IcxEHN0g==","user_longitude":null,"user_latitude":null,"sex_text":"Sex 2","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-31 11:19:13"},{"id":100009,"name":null,"phone":"","city":null,"sex":"2","score":null,"viptime":null,"isvip":"0","money":null,"status":"1","regtime":1540969441,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://thirdqq.qlogo.cn/qqapp/1107702717/472568E14791848782447D6DF53EB7A4/100","nickname":"Mr、Ku","lastlogintime":null,"dubai":null,"biaoqian":null,"age":0,"love_status":null,"openid":"472568E14791848782447D6DF53EB7A4","type":"2","message_tiaojian":"0","message_all":"1","rong_token":null,"user_longitude":null,"user_latitude":null,"sex_text":"Sex 2","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-31 15:04:01"}]
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
         * name : 123
         * phone : 123
         * city :
         * sex : 2
         * score : 45
         * viptime : null
         * isvip : 0
         * money : 300
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
         * openid : null
         * type : 0
         * message_tiaojian : 1
         * message_all : 1
         * rong_token : null
         * user_longitude : 1.01
         * user_latitude : 1.01
         * sex_text : Sex 2
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
        private String viptime;
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
        private String openid;
        private String type;
        private String message_tiaojian;
        private String message_all;
        private String rong_token;
        private String user_longitude;
        private String user_latitude;
        private String sex_text;
        private String isvip_text;
        private String status_text;
        private String regtime_text;

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

        public String getViptime() {
            return viptime;
        }

        public void setViptime(String viptime) {
            this.viptime = viptime;
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

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getRong_token() {
            return rong_token;
        }

        public void setRong_token(String rong_token) {
            this.rong_token = rong_token;
        }

        public String getUser_longitude() {
            return user_longitude;
        }

        public void setUser_longitude(String user_longitude) {
            this.user_longitude = user_longitude;
        }

        public String getUser_latitude() {
            return user_latitude;
        }

        public void setUser_latitude(String user_latitude) {
            this.user_latitude = user_latitude;
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
