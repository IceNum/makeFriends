package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class HomeRecommendBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540990337
     * data : [{"id":1,"name":"123","phone":"123","city":"","sex":"2","score":45,"viptime":null,"isvip":"0","money":300,"status":"1","regtime":1539834884,"password":"8d4646eb2d7067126eb08adb0672f7bb","puid":null,"headimage":"/1.jpg","nickname":"张三","lastlogintime":1540274843,"dubai":"哈哈哈啊啊好好","biaoqian":"1,2","age":15,"love_status":null,"openid":null,"type":"0","message_tiaojian":"1","message_all":"1","rong_token":null,"user_longitude":"1.01","user_latitude":"1.01","dongtai":[{"id":1,"uid":1,"cateid":1,"content":"","images":"/assets/img/qrcode.png,/uploads/20181018/815b239deaf6ef283549e6c2c62d0242.gif","time":1539836693,"latitude":null,"longitude":null,"zan":0,"address":null,"time_text":"2018-10-18 12:24:53"},{"id":2,"uid":1,"cateid":2,"content":"123","images":"123","time":1539841610,"latitude":null,"longitude":null,"zan":0,"address":null,"time_text":"2018-10-18 13:46:50"},{"id":4,"uid":1,"cateid":3,"content":"123","images":"123","time":1539846947,"latitude":"1231212","longitude":"123123","zan":0,"address":null,"time_text":"2018-10-18 15:15:47"},{"id":5,"uid":1,"cateid":4,"content":"123","images":"123","time":1539846947,"latitude":"1231212","longitude":"123123","zan":0,"address":null,"time_text":"2018-10-18 15:15:47"}],"jb":{"uid":1,"height":"123","income":"1","edu":"123","academy":"123","profession":"123","marital_status":"1","seek":"什么"},"sex_text":"Sex 2","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-18 11:54:44"},{"id":100008,"name":null,"phone":"17329429078","city":"","sex":"2","score":null,"viptime":null,"isvip":"0","money":null,"status":"1","regtime":1540955953,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181031/1fc51129eadee8401910acfade5187c1.png","nickname":"小花","lastlogintime":null,"dubai":"很好","biaoqian":"","age":93,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"25Xai9/eAHKDooGO5s3L5zmuAX9P8WqTEHr23Re19Io5JS3kvqLvsiDNO4/7bAq5Va0+Cqwb/Sryy+IcxEHN0g==","user_longitude":"113.734402","user_latitude":"34.749372","dongtai":[{"id":13,"uid":100008,"cateid":1,"content":"Hahaha","images":"","time":1540956893,"latitude":"","longitude":"","zan":0,"address":null,"time_text":"2018-10-31 11:34:53"},{"id":15,"uid":100008,"cateid":2,"content":"博士","images":"http://rrl.project.hbsshop.cn/uploads/20181031/eab946634ee293b383ebf6e48462d7bd.png","time":1540981685,"latitude":"","longitude":"","zan":0,"address":null,"time_text":"2018-10-31 18:28:05"}],"jb":{"uid":100008,"height":"151","income":"","edu":"","academy":"清华","profession":"","marital_status":"","seek":""},"sex_text":"Sex 2","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-31 11:19:13"}]
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
         * dongtai : [{"id":1,"uid":1,"cateid":1,"content":"","images":"/assets/img/qrcode.png,/uploads/20181018/815b239deaf6ef283549e6c2c62d0242.gif","time":1539836693,"latitude":null,"longitude":null,"zan":0,"address":null,"time_text":"2018-10-18 12:24:53"},{"id":2,"uid":1,"cateid":2,"content":"123","images":"123","time":1539841610,"latitude":null,"longitude":null,"zan":0,"address":null,"time_text":"2018-10-18 13:46:50"},{"id":4,"uid":1,"cateid":3,"content":"123","images":"123","time":1539846947,"latitude":"1231212","longitude":"123123","zan":0,"address":null,"time_text":"2018-10-18 15:15:47"},{"id":5,"uid":1,"cateid":4,"content":"123","images":"123","time":1539846947,"latitude":"1231212","longitude":"123123","zan":0,"address":null,"time_text":"2018-10-18 15:15:47"}]
         * jb : {"uid":1,"height":"123","income":"1","edu":"123","academy":"123","profession":"123","marital_status":"1","seek":"什么"}
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
        private String height;
        private String shouru;
        private String xueli;
        private JbBean jb;
        private String sex_text;
        private String isvip_text;
        private String status_text;
        private String regtime_text;
        private List<DongtaiBean> dongtai;

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getShouru() {
            return shouru;
        }

        public void setShouru(String shouru) {
            this.shouru = shouru;
        }

        public String getXueli() {
            return xueli;
        }

        public void setXueli(String xueli) {
            this.xueli = xueli;
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

        public Object getRong_token() {
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

        public JbBean getJb() {
            return jb;
        }

        public void setJb(JbBean jb) {
            this.jb = jb;
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

        public List<DongtaiBean> getDongtai() {
            return dongtai;
        }

        public void setDongtai(List<DongtaiBean> dongtai) {
            this.dongtai = dongtai;
        }

        public static class JbBean {
            /**
             * uid : 1
             * height : 123
             * income : 1
             * edu : 123
             * academy : 123
             * profession : 123
             * marital_status : 1
             * seek : 什么
             */

            private int uid;
            private String height;
            private String income;
            private String edu;
            private String academy;
            private String profession;
            private String marital_status;
            private String seek;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

        public static class DongtaiBean {
            /**
             * id : 1
             * uid : 1
             * cateid : 1
             * content :
             * images : /assets/img/qrcode.png,/uploads/20181018/815b239deaf6ef283549e6c2c62d0242.gif
             * time : 1539836693
             * latitude : null
             * longitude : null
             * zan : 0
             * address : null
             * time_text : 2018-10-18 12:24:53
             */

            private int id;
            private String uid;
            private String cateid;
            private String content;
            private String images;
            private String time;
            private String latitude;
            private String longitude;
            private int zan;
            private String iszan;
            private String address;
            private String time_text;

            public String getIszan() {
                return iszan;
            }

            public void setIszan(String iszan) {
                this.iszan = iszan;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTime_text() {
                return time_text;
            }

            public void setTime_text(String time_text) {
                this.time_text = time_text;
            }
        }
    }
}
