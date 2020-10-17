package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class MyLikedBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1541752237
     * data : [{"id":33,"like_uid":100008,"be_like_uid":100007,"time":1540988484,"jb":{"uid":100008,"height":"151","income":"","edu":"","academy":"清华","profession":"","marital_status":"","seek":""},"likeer":{"id":100008,"name":null,"phone":"17329429078","city":"天津市 天津市","sex":"2","score":"0","viptime":1541678219,"isvip":"1","money":120,"status":"1","regtime":1540955953,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181031/1fc51129eadee8401910acfade5187c1.png","nickname":"小花","lastlogintime":null,"dubai":"很好","biaoqian":"","age":93,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"25Xai9/eAHKDooGO5s3L5zmuAX9P8WqTEHr23Re19Io5JS3kvqLvsiDNO4/7bAq5Va0+Cqwb/Sryy+IcxEHN0g==","user_longitude":"113.734402","user_latitude":"34.749372","birthday":null,"sex_text":"Sex 2","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-10-31 11:19:13"}},{"id":53,"like_uid":100009,"be_like_uid":100007,"time":1541058605,"jb":{"uid":100009,"height":"180","income":"7","edu":"硕士","academy":"哈佛大学","profession":"计算机/互联网/通信","marital_status":"0","seek":"恋人"},"likeer":{"id":100009,"name":null,"phone":"18317713519","city":"河南省郑州市","sex":"2","score":"0.3","viptime":1554446069,"isvip":"1","money":0,"status":"1","regtime":1540969441,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://thirdqq.qlogo.cn/qqapp/1107702717/472568E14791848782447D6DF53EB7A4/100","nickname":"Mr、Ku","lastlogintime":1541655595,"dubai":"浪里个浪","biaoqian":"1,6","age":0,"love_status":"1","openid":"472568E14791848782447D6DF53EB7A4","type":"2","message_tiaojian":"0","message_all":"1","rong_token":null,"user_longitude":"113.734402","user_latitude":"34.749372","birthday":null,"sex_text":"Sex 2","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-10-31 15:04:01"}},{"id":89,"like_uid":100007,"be_like_uid":100007,"time":1541637833,"jb":{"uid":100007,"height":"180","income":"2","edu":"本科","academy":"清华大学","profession":"计算机/互联网/通信","marital_status":"0","seek":"恋人"},"likeer":{"id":100007,"name":null,"phone":"18317713519","city":"河南省郑州市","sex":"1","score":"38.1","viptime":1663310537,"isvip":"1","money":100,"status":"1","regtime":1540955639,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181101/8491f4000b0d97b751d48f2d781fa68b.jpg","nickname":"酷酷","lastlogintime":1541751923,"dubai":"寂寞的夜！","biaoqian":"1,2","age":26,"love_status":"2","openid":null,"type":"0","message_tiaojian":"1","message_all":"1","rong_token":"eMcTggKzgMrEVznjZzydCTmuAX9P8WqTEHr23Re19Io5JS3kvqLvsvyANkmbvAROqnjpI73X8zf5JEJpZP4/Qw==","user_longitude":"113.734402","user_latitude":"34.749372","birthday":"2018-11-01","sex_text":"Sex 1","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-10-31 11:13:59"}},{"id":94,"like_uid":100014,"be_like_uid":100007,"time":1541734937,"jb":{"uid":100014,"height":"","income":"1.5-2万元","edu":"","academy":"","profession":"","marital_status":"","seek":""},"likeer":{"id":100014,"name":null,"phone":"18736005602","city":"河南省 郑州市","sex":"2","score":null,"viptime":1594524397,"isvip":"1","money":null,"status":"1","regtime":1540983924,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181106/5551aa7c978c8e5d059710227475d526.png","nickname":"小花","lastlogintime":null,"dubai":"很好","biaoqian":"1,2","age":31,"love_status":"0","openid":"ohbvX1Y7I1wXb1OR_W06Ls9GV3pI","type":"1","message_tiaojian":"0","message_all":"1","rong_token":"LAg68XHoR293pY/qJiSSvqMLJoDJSPagsc2qd98DbuIxZvAGZSx4bZY/846HikYgZaugv+Rk7pxs/ySxYyWUYQ==","user_longitude":"113.734402","user_latitude":"34.749372","birthday":null,"sex_text":"Sex 2","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-10-31 19:05:24"}}]
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
         * id : 33
         * like_uid : 100008
         * be_like_uid : 100007
         * time : 1540988484
         * jb : {"uid":100008,"height":"151","income":"","edu":"","academy":"清华","profession":"","marital_status":"","seek":""}
         * likeer : {"id":100008,"name":null,"phone":"17329429078","city":"天津市 天津市","sex":"2","score":"0","viptime":1541678219,"isvip":"1","money":120,"status":"1","regtime":1540955953,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":"http://rrl.project.hbsshop.cn/uploads/20181031/1fc51129eadee8401910acfade5187c1.png","nickname":"小花","lastlogintime":null,"dubai":"很好","biaoqian":"","age":93,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"25Xai9/eAHKDooGO5s3L5zmuAX9P8WqTEHr23Re19Io5JS3kvqLvsiDNO4/7bAq5Va0+Cqwb/Sryy+IcxEHN0g==","user_longitude":"113.734402","user_latitude":"34.749372","birthday":null,"sex_text":"Sex 2","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-10-31 11:19:13"}
         */

        private int id;
        private String like_uid;
        private String be_like_uid;
        private String time;
        private JbBean jb;
        private LikeerBean likeer;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLike_uid() {
            return like_uid;
        }

        public void setLike_uid(String like_uid) {
            this.like_uid = like_uid;
        }

        public String getBe_like_uid() {
            return be_like_uid;
        }

        public void setBe_like_uid(String be_like_uid) {
            this.be_like_uid = be_like_uid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public JbBean getJb() {
            return jb;
        }

        public void setJb(JbBean jb) {
            this.jb = jb;
        }

        public LikeerBean getLikeer() {
            return likeer;
        }

        public void setLikeer(LikeerBean likeer) {
            this.likeer = likeer;
        }

        public static class JbBean {
            /**
             * uid : 100008
             * height : 151
             * income :
             * edu :
             * academy : 清华
             * profession :
             * marital_status :
             * seek :
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

        public static class LikeerBean {
            /**
             * id : 100008
             * name : null
             * phone : 17329429078
             * city : 天津市 天津市
             * sex : 2
             * score : 0
             * viptime : 1541678219
             * isvip : 1
             * money : 120
             * status : 1
             * regtime : 1540955953
             * password : e10adc3949ba59abbe56e057f20f883e
             * puid : 0
             * headimage : http://rrl.project.hbsshop.cn/uploads/20181031/1fc51129eadee8401910acfade5187c1.png
             * nickname : 小花
             * lastlogintime : null
             * dubai : 很好
             * biaoqian :
             * age : 93
             * love_status : null
             * openid : null
             * type : 0
             * message_tiaojian : 0
             * message_all : 1
             * rong_token : 25Xai9/eAHKDooGO5s3L5zmuAX9P8WqTEHr23Re19Io5JS3kvqLvsiDNO4/7bAq5Va0+Cqwb/Sryy+IcxEHN0g==
             * user_longitude : 113.734402
             * user_latitude : 34.749372
             * birthday : null
             * sex_text : Sex 2
             * isvip_text : Isvip 1
             * status_text : Status 1
             * regtime_text : 2018-10-31 11:19:13
             */

            private int id;
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
            private String birthday;
            private String sex_text;
            private String isvip_text;
            private String status_text;
            private String regtime_text;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
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
}
