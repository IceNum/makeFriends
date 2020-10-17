package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/19 0019.
 */

public class HmdListBean {


    /**
     * code : 1
     * msg : 获取成功
     * time : 1540860965
     * data : [{"id":4,"uid":99887,"be_black_uid":99894,"time":1540860960,"be_black_user":{"id":99894,"name":null,"phone":"17329429078","city":null,"sex":"3","score":null,"viptime":null,"isvip":"0","money":null,"status":"1","regtime":1540860941,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":null,"nickname":null,"lastlogintime":null,"dubai":null,"biaoqian":null,"age":null,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","sex_text":"Sex 3","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-30 08:55:41"}}]
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
         * id : 4
         * uid : 99887
         * be_black_uid : 99894
         * time : 1540860960
         * be_black_user : {"id":99894,"name":null,"phone":"17329429078","city":null,"sex":"3","score":null,"viptime":null,"isvip":"0","money":null,"status":"1","regtime":1540860941,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":null,"nickname":null,"lastlogintime":null,"dubai":null,"biaoqian":null,"age":null,"love_status":null,"openid":null,"type":"0","message_tiaojian":"0","message_all":"1","sex_text":"Sex 3","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-30 08:55:41"}
         */

        private int id;
        private int uid;
        private String be_black_uid;
        private int time;
        private BeBlackUserBean be_black_user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getBe_black_uid() {
            return be_black_uid;
        }

        public void setBe_black_uid(String be_black_uid) {
            this.be_black_uid = be_black_uid;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public BeBlackUserBean getBe_black_user() {
            return be_black_user;
        }

        public void setBe_black_user(BeBlackUserBean be_black_user) {
            this.be_black_user = be_black_user;
        }

        public static class BeBlackUserBean {
            /**
             * id : 99894
             * name : null
             * phone : 17329429078
             * city : null
             * sex : 3
             * score : null
             * viptime : null
             * isvip : 0
             * money : null
             * status : 1
             * regtime : 1540860941
             * password : e10adc3949ba59abbe56e057f20f883e
             * puid : 0
             * headimage : null
             * nickname : null
             * lastlogintime : null
             * dubai : null
             * biaoqian : null
             * age : null
             * love_status : null
             * openid : null
             * type : 0
             * message_tiaojian : 0
             * message_all : 1
             * sex_text : Sex 3
             * isvip_text : Isvip 0
             * status_text : Status 1
             * regtime_text : 2018-10-30 08:55:41
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
            private int regtime;
            private String password;
            private int puid;
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

            public Object getName() {
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

            public int getRegtime() {
                return regtime;
            }

            public void setRegtime(int regtime) {
                this.regtime = regtime;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getPuid() {
                return puid;
            }

            public void setPuid(int puid) {
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

            public Object getBiaoqian() {
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
