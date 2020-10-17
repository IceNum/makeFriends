package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class LookedBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1541745775
     * data : [{"id":9,"view_uid":100019,"be_view_uid":100007,"time":1541745755,"jibeninfo":{"uid":100019,"height":"180","income":"7","edu":"博士","academy":"麻省理工","profession":"计算机/互联网/通信","marital_status":"0","seek":"结婚对象"},"viewer":{"id":100019,"name":"Circs","phone":"17611396550","city":"河南省郑州市","sex":"","score":"120","viptime":null,"isvip":"1","money":1000000,"status":"1","regtime":1541052208,"password":"e10adc3949ba59abbe56e057f20f883e","puid":null,"headimage":"","nickname":"","lastlogintime":1541398822,"dubai":"","biaoqian":"","age":0,"love_status":"0","openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"ReobEb7nF7F/M2sJ2QSifqMLJoDJSPagsc2qd98DbuIxZvAGZSx4baUPWS9ogpjlFyuWYBeLc+LZBa1U0V+03A==","user_longitude":null,"user_latitude":null,"birthday":null,"sex_text":"","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-11-01 14:03:28"}}]
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
         * id : 9
         * view_uid : 100019
         * be_view_uid : 100007
         * time : 1541745755
         * jibeninfo : {"uid":100019,"height":"180","income":"7","edu":"博士","academy":"麻省理工","profession":"计算机/互联网/通信","marital_status":"0","seek":"结婚对象"}
         * viewer : {"id":100019,"name":"Circs","phone":"17611396550","city":"河南省郑州市","sex":"","score":"120","viptime":null,"isvip":"1","money":1000000,"status":"1","regtime":1541052208,"password":"e10adc3949ba59abbe56e057f20f883e","puid":null,"headimage":"","nickname":"","lastlogintime":1541398822,"dubai":"","biaoqian":"","age":0,"love_status":"0","openid":null,"type":"0","message_tiaojian":"0","message_all":"1","rong_token":"ReobEb7nF7F/M2sJ2QSifqMLJoDJSPagsc2qd98DbuIxZvAGZSx4baUPWS9ogpjlFyuWYBeLc+LZBa1U0V+03A==","user_longitude":null,"user_latitude":null,"birthday":null,"sex_text":"","isvip_text":"Isvip 1","status_text":"Status 1","regtime_text":"2018-11-01 14:03:28"}
         */

        private String id;
        private String view_uid;
        private String be_view_uid;
        private String time;
        private JibeninfoBean jibeninfo;
        private ViewerBean viewer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getView_uid() {
            return view_uid;
        }

        public void setView_uid(String view_uid) {
            this.view_uid = view_uid;
        }

        public String getBe_view_uid() {
            return be_view_uid;
        }

        public void setBe_view_uid(String be_view_uid) {
            this.be_view_uid = be_view_uid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public JibeninfoBean getJibeninfo() {
            return jibeninfo;
        }

        public void setJibeninfo(JibeninfoBean jibeninfo) {
            this.jibeninfo = jibeninfo;
        }

        public ViewerBean getViewer() {
            return viewer;
        }

        public void setViewer(ViewerBean viewer) {
            this.viewer = viewer;
        }

        public static class JibeninfoBean {
            /**
             * uid : 100019
             * height : 180
             * income : 7
             * edu : 博士
             * academy : 麻省理工
             * profession : 计算机/互联网/通信
             * marital_status : 0
             * seek : 结婚对象
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

        public static class ViewerBean {
            /**
             * id : 100019
             * name : Circs
             * phone : 17611396550
             * city : 河南省郑州市
             * sex :
             * score : 120
             * viptime : null
             * isvip : 1
             * money : 1000000
             * status : 1
             * regtime : 1541052208
             * password : e10adc3949ba59abbe56e057f20f883e
             * puid : null
             * headimage :
             * nickname :
             * lastlogintime : 1541398822
             * dubai :
             * biaoqian :
             * age : 0
             * love_status : 0
             * openid : null
             * type : 0
             * message_tiaojian : 0
             * message_all : 1
             * rong_token : ReobEb7nF7F/M2sJ2QSifqMLJoDJSPagsc2qd98DbuIxZvAGZSx4baUPWS9ogpjlFyuWYBeLc+LZBa1U0V+03A==
             * user_longitude : null
             * user_latitude : null
             * birthday : null
             * sex_text :
             * isvip_text : Isvip 1
             * status_text : Status 1
             * regtime_text : 2018-11-01 14:03:28
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
