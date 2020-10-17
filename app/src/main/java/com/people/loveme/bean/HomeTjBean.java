package com.people.loveme.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kxn on 2018/12/24 0024.
 */

public class HomeTjBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1545612670
     * data : [{"id":6,"nickname":"居住地","name":"city","switch":0},{"id":7,"nickname":"身高","name":"height","switch":1},{"id":8,"nickname":"收入","name":"income","switch":1},{"id":9,"nickname":"学历","name":"edu","switch":1},{"id":10,"nickname":"名族","name":"nation","switch":1},{"id":11,"nickname":"是否购车","name":"car","switch":1},{"id":12,"nickname":"籍贯","name":"na_place","switch":1},{"id":13,"nickname":"上线时间","name":"logintime","switch":1},{"id":14,"nickname":"信用分","name":"score","switch":1},{"id":15,"nickname":"实名制","name":"is_relaname","switch":1},{"id":16,"nickname":"房屋认证","name":"is_buyhome","switch":1},{"id":17,"nickname":"年龄","name":"age","switch":1}]
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
         * id : 6
         * nickname : 居住地
         * name : city
         * switch : 0
         */

        private int id;
        private String nickname;
        private String name;
        @SerializedName("switch")
        private String switchX;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSwitchX() {
            return switchX;
        }

        public void setSwitchX(String switchX) {
            this.switchX = switchX;
        }
    }
}
