package com.people.loveme.bean;

/**
 * Created by kxn on 2018/9/18 0018.
 */

public class ZyRzBean {

    /**
     * code : 1
     * msg : 获取成功
     * time : 1540784011
     * data : {"id":2,"gongsiname":"阿里巴巴","image":"20181029/ec86c21b908efa67a62b31cee15d80cf.jpg","time":1540784004,"status":"0","uid":99887,"time_text":"2018-10-29 11:33:24","status_text":"Status 0"}
     */

    private int code;
    private String msg;
    private String time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * gongsiname : 阿里巴巴
         * image : 20181029/ec86c21b908efa67a62b31cee15d80cf.jpg
         * time : 1540784004
         * status : 0
         * uid : 99887
         * time_text : 2018-10-29 11:33:24
         * status_text : Status 0
         */

        private int id;
        private String gongsiname;
        private String image;
        private int time;
        private String status;
        private int uid;
        private String time_text;
        private String status_text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGongsiname() {
            return gongsiname;
        }

        public void setGongsiname(String gongsiname) {
            this.gongsiname = gongsiname;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTime_text() {
            return time_text;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }
    }
}
