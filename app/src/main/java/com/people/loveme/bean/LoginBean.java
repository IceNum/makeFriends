package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/5 0005.
 */

public class LoginBean {


    /**
     * code : 1
     * msg : 登录成功
     * data : {"id":99887,"name":null,"phone":"18317713519","city":null,"sex":"3","score":null,"isvip":"0","money":null,"status":"1","regtime":1540542175,"password":"e10adc3949ba59abbe56e057f20f883e","puid":0,"headimage":null,"nickname":null,"xueli":null,"shouru":null,"height":null,"lastlogintime":null,"sex_text":"Sex 3","isvip_text":"Isvip 0","status_text":"Status 1","regtime_text":"2018-10-26 16:22:55"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * id : 99887
         * name : null
         * phone : 18317713519
         * city : null
         * sex : 3
         * score : null
         * isvip : 0
         * money : null
         * status : 1
         * regtime : 1540542175
         * password : e10adc3949ba59abbe56e057f20f883e
         * puid : 0
         * headimage : null
         * nickname : null
         * xueli : null
         * shouru : null
         * height : null
         * lastlogintime : null
         * sex_text : Sex 3
         * isvip_text : Isvip 0
         * status_text : Status 1
         * regtime_text : 2018-10-26 16:22:55
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
        private int regtime;
        private String password;
        private int puid;
        private String headimage;
        private String nickname;
        private String xueli;
        private String shouru;
        private String height;
        private String lastlogintime;
        private String sex_text;
        private String isvip_text;
        private String status_text;
        private String regtime_text;
        private String rong_token;
        private String is_wanshan;

        public String getIs_wanshan() {
            return is_wanshan;
        }

        public void setIs_wanshan(String is_wanshan) {
            this.is_wanshan = is_wanshan;
        }

        public String getRong_token() {
            return rong_token;
        }

        public void setRong_token(String rong_token) {
            this.rong_token = rong_token;
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

        public String getXueli() {
            return xueli;
        }

        public void setXueli(String xueli) {
            this.xueli = xueli;
        }

        public String getShouru() {
            return shouru;
        }

        public void setShouru(String shouru) {
            this.shouru = shouru;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLastlogintime() {
            return lastlogintime;
        }

        public void setLastlogintime(String lastlogintime) {
            this.lastlogintime = lastlogintime;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", city='" + city + '\'' +
                    ", sex='" + sex + '\'' +
                    ", score='" + score + '\'' +
                    ", isvip='" + isvip + '\'' +
                    ", money='" + money + '\'' +
                    ", status='" + status + '\'' +
                    ", regtime=" + regtime +
                    ", password='" + password + '\'' +
                    ", puid=" + puid +
                    ", headimage='" + headimage + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", xueli='" + xueli + '\'' +
                    ", shouru='" + shouru + '\'' +
                    ", height='" + height + '\'' +
                    ", lastlogintime='" + lastlogintime + '\'' +
                    ", sex_text='" + sex_text + '\'' +
                    ", isvip_text='" + isvip_text + '\'' +
                    ", status_text='" + status_text + '\'' +
                    ", regtime_text='" + regtime_text + '\'' +
                    ", rong_token='" + rong_token + '\'' +
                    ", is_wanshan='" + is_wanshan + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
