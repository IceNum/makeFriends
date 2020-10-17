package com.people.loveme.bean;

import java.util.List;

/**
 * Created by kxn on 2018/9/19 0019.
 */

public class MyInviteBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1557822397
     * data : [{"id":100310,"name":"","phone":"18937138548","city":"北京市北京市","sex":"1","score":31.8,"viptime":1568106804,"isvip":"1","money":11,"status":"1","regtime":1555905530,"password":"79b1b1921c5a3398220fea336756535a","puid":100284,"headimage":"http://39.98.246.254/uploads/20190422/18ee011f2ce943d8abbef7f1e3600946.jpg","nickname":"吴小帅","lastlogintime":1557817163,"dubai":"","biaoqian":"","age":0,"love_status":"0","openid":"","type":"0","message_tiaojian":"0","message_all":"1","rong_token":"aje4iVqtnFOMuZGpVQfVd8/noTqvwdMEj9ra1vC4qJcmnAOlHq1YWYzhB/hrPjdgH9su1LCI9eP7N0FEalg+Tg==","user_longitude":"113.733868","user_latitude":"34.749528","birthday":"2019-04-27","jpush_id":"120c83f7606a19b3dd5","relaname_pay":"","rebot_id":3,"order":[{"id":"201905131713172089","uid":100310,"money":388,"time":1557738797,"pay_type":"2","detail":"季度套餐","type":"0","order_type":"1","be_dashang_uid":null,"vip_opentime":120,"status":"1","time_text":"2019-05-13 17:13:17","pay_type_text":"Pay_type 2","type_text":"Type 0"}],"sex_text":"Sex 1","isvip_text":"Isvip 1","status_text":"Status 1","img_text":"http://39.98.246.254http://39.98.246.254/uploads/20190422/18ee011f2ce943d8abbef7f1e3600946.jpg","regtime_text":"2019-04-22 11:58:50"}]
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
         * id : 100310
         * name :
         * phone : 18937138548
         * city : 北京市北京市
         * sex : 1
         * score : 31.8
         * viptime : 1568106804
         * isvip : 1
         * money : 11
         * status : 1
         * regtime : 1555905530
         * password : 79b1b1921c5a3398220fea336756535a
         * puid : 100284
         * headimage : http://39.98.246.254/uploads/20190422/18ee011f2ce943d8abbef7f1e3600946.jpg
         * nickname : 吴小帅
         * lastlogintime : 1557817163
         * dubai :
         * biaoqian :
         * age : 0
         * love_status : 0
         * openid :
         * type : 0
         * message_tiaojian : 0
         * message_all : 1
         * rong_token : aje4iVqtnFOMuZGpVQfVd8/noTqvwdMEj9ra1vC4qJcmnAOlHq1YWYzhB/hrPjdgH9su1LCI9eP7N0FEalg+Tg==
         * user_longitude : 113.733868
         * user_latitude : 34.749528
         * birthday : 2019-04-27
         * jpush_id : 120c83f7606a19b3dd5
         * relaname_pay :
         * rebot_id : 3
         * order : [{"id":"201905131713172089","uid":100310,"money":388,"time":1557738797,"pay_type":"2","detail":"季度套餐","type":"0","order_type":"1","be_dashang_uid":null,"vip_opentime":120,"status":"1","time_text":"2019-05-13 17:13:17","pay_type_text":"Pay_type 2","type_text":"Type 0"}]
         * sex_text : Sex 1
         * isvip_text : Isvip 1
         * status_text : Status 1
         * img_text : http://39.98.246.254http://39.98.246.254/uploads/20190422/18ee011f2ce943d8abbef7f1e3600946.jpg
         * regtime_text : 2019-04-22 11:58:50
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
        private String birthday;
        private String jpush_id;
        private String relaname_pay;
        private String rebot_id;
        private String sex_text;
        private String isvip_text;
        private String status_text;
        private String img_text;
        private String regtime_text;
        private List<OrderBean> order;

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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJpush_id() {
            return jpush_id;
        }

        public void setJpush_id(String jpush_id) {
            this.jpush_id = jpush_id;
        }

        public String getRelaname_pay() {
            return relaname_pay;
        }

        public void setRelaname_pay(String relaname_pay) {
            this.relaname_pay = relaname_pay;
        }

        public String getRebot_id() {
            return rebot_id;
        }

        public void setRebot_id(String rebot_id) {
            this.rebot_id = rebot_id;
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

        public String getImg_text() {
            return img_text;
        }

        public void setImg_text(String img_text) {
            this.img_text = img_text;
        }

        public String getRegtime_text() {
            return regtime_text;
        }

        public void setRegtime_text(String regtime_text) {
            this.regtime_text = regtime_text;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * id : 201905131713172089
             * uid : 100310
             * money : 388
             * time : 1557738797
             * pay_type : 2
             * detail : 季度套餐
             * type : 0
             * order_type : 1
             * be_dashang_uid : null
             * vip_opentime : 120
             * status : 1
             * time_text : 2019-05-13 17:13:17
             * pay_type_text : Pay_type 2
             * type_text : Type 0
             */

            private String id;
            private int uid;
            private String money;
            private int time;
            private String pay_type;
            private String detail;
            private String type;
            private String order_type;
            private String be_dashang_uid;
            private int vip_opentime;
            private String status;
            private String time_text;
            private String pay_type_text;
            private String type_text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getBe_dashang_uid() {
                return be_dashang_uid;
            }

            public void setBe_dashang_uid(String be_dashang_uid) {
                this.be_dashang_uid = be_dashang_uid;
            }

            public int getVip_opentime() {
                return vip_opentime;
            }

            public void setVip_opentime(int vip_opentime) {
                this.vip_opentime = vip_opentime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTime_text() {
                return time_text;
            }

            public void setTime_text(String time_text) {
                this.time_text = time_text;
            }

            public String getPay_type_text() {
                return pay_type_text;
            }

            public void setPay_type_text(String pay_type_text) {
                this.pay_type_text = pay_type_text;
            }

            public String getType_text() {
                return type_text;
            }

            public void setType_text(String type_text) {
                this.type_text = type_text;
            }
        }
    }
}
